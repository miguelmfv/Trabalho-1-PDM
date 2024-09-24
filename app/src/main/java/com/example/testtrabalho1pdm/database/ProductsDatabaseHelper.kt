package com.example.testtrabalho1pdm.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.testtrabalho1pdm.model.Products

class ProductsDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "DBPRODUCTSTrabalho1PDM.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_PRODUCTS = "products"
        private const val COLUMN_PRODUCTS_ID = "id"
        private const val COLUMN_PRODUCTS_NAME = "nome"
        private const val COLUMN_PRODUCTS_DESCRICAO = "descricao"
        private const val COLUMN_PRODUCTS_PRECO = "preco"
        private const val COLUMN_PRODUCTS_ESTOQ = "estoque"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createProcuctsTable = """
            CREATE TABLE $TABLE_PRODUCTS (
                $COLUMN_PRODUCTS_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_PRODUCTS_NAME TEXT,
                $COLUMN_PRODUCTS_DESCRICAO TEXT,
                $COLUMN_PRODUCTS_PRECO REAL,
                $COLUMN_PRODUCTS_ESTOQ INTEGER
            )
        """.trimIndent()
        db.execSQL(createProcuctsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCTS")
        onCreate(db)
    }

    fun addProduct(nome: String, descricao: String, preco: String, estoque: String): Boolean {
        return try {
            val values = ContentValues().apply {
                put(COLUMN_PRODUCTS_NAME, nome)
                put(COLUMN_PRODUCTS_DESCRICAO, descricao)
                put(COLUMN_PRODUCTS_PRECO, preco)
                put(COLUMN_PRODUCTS_ESTOQ, estoque)
            }
            val db = this.writableDatabase
            val result = db.insert(TABLE_PRODUCTS, null, values)
            db.close()
            if (result != -1L) {
                true
            } else {
                Log.e("DatabaseError", "Falha ao inserir produto no banco de dados.")
                false
            }
        } catch (e: Exception) {
            Log.e("DatabaseError", "Erro ao adicionar produto", e)
            e.printStackTrace()
            false
        }
    }

    fun getAllProducts(): List<Products> {
        val productsList = mutableListOf<Products>()
        val db = this.readableDatabase
        val cursor = db.query(TABLE_PRODUCTS, arrayOf(
            COLUMN_PRODUCTS_ID,
            COLUMN_PRODUCTS_NAME,
            COLUMN_PRODUCTS_DESCRICAO,
            COLUMN_PRODUCTS_PRECO,
            COLUMN_PRODUCTS_ESTOQ,
        ), null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val product = Products(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUCTS_ID)),
                    nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCTS_NAME)),
                    descricao = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCTS_DESCRICAO)),
                    preco = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRODUCTS_PRECO)),
                    estoque = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUCTS_ESTOQ))
                )
                productsList.add(product)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return productsList
    }



    fun atualizaProduto(productId: Int, nome: String, desc: String, preco: String, estoque: String): Boolean {
        return try {
            val values = ContentValues().apply {
                put(COLUMN_PRODUCTS_NAME, nome)
                put(COLUMN_PRODUCTS_DESCRICAO, desc)
                put(COLUMN_PRODUCTS_PRECO, preco)
                put(COLUMN_PRODUCTS_ESTOQ, estoque)
            }
            val db = this.writableDatabase
            val result = db.update(TABLE_PRODUCTS, values, "$COLUMN_PRODUCTS_ID = ?", arrayOf(productId.toString()))
            db.close()
            if (result != -1) {
                true
            } else {
                Log.e("DatabaseError", "Falha ao atualizar o produto no banco de dados.")
                false
            }
        } catch (e: Exception) {
            Log.e("DatabaseError", "Erro ao atualizar o produto", e)
            e.printStackTrace()
            false
        }
    }

    fun deleteId(productId: Int): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_PRODUCTS, "$COLUMN_PRODUCTS_ID = ?",arrayOf(productId.toString()))
        db.close()
        return result
    }

    fun getProductByID(productId: Int): Products? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_PRODUCTS,
            null,
            "$COLUMN_PRODUCTS_ID = ?",
            arrayOf(productId.toString()),
            null,
            null,
            null
        )
        return if (cursor.moveToFirst()) {
            Products(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUCTS_ID)),
                nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCTS_NAME)),
                descricao = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCTS_DESCRICAO)),
                preco = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRODUCTS_PRECO)),
                estoque = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUCTS_ESTOQ))
            )
        } else {
            null
        }.also {
            cursor.close()
            db.close()
        }
    }
}