package com.example.testtrabalho1pdm.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.testtrabalho1pdm.model.Orders

class OrdersDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "DBORDERSTrabalho1PDM.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_ORDERS = "orders"
        private const val COLUMN_ORDERS_ID = "id"
        private const val COLUMN_ORDERS_DATAPEDIDO = "dataPedido"
        private const val COLUMN_ORDERS_VALORTOT = "valorTot"
        private const val COLUMN_ORDERS_STATUS = "status"
        private const val COLUMN_ORDERS_OBS = "observacao"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createOrdersTable = """
            CREATE TABLE $TABLE_ORDERS (
                $COLUMN_ORDERS_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_ORDERS_DATAPEDIDO TEXT NOT NULL,
                $COLUMN_ORDERS_VALORTOT DOUBLE NOT NULL,
                $COLUMN_ORDERS_STATUS TEXT,
                $COLUMN_ORDERS_OBS TEXT
            )
        """.trimIndent()
        db.execSQL(createOrdersTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ORDERS")
        onCreate(db)
    }

    fun addOrder(data: String, valTot: String, status: String, obs: String): Boolean {
        return try {
            val values = ContentValues().apply {
                put(COLUMN_ORDERS_DATAPEDIDO, data)
                put(COLUMN_ORDERS_VALORTOT, valTot)
                put(COLUMN_ORDERS_STATUS, status)
                put(COLUMN_ORDERS_OBS, obs)
            }
            val db = this.writableDatabase
            val result = db.insert(TABLE_ORDERS, null, values)
            db.close()
            if (result != -1L) {
                true
            } else {
                Log.e("DatabaseError", "Falha ao inserir uma nova ordem de serviço no banco de dados.")
                false
            }
        } catch (e: Exception) {
            Log.e("DatabaseError", "Erro ao adicionar a nova ordem de serviço", e)
            e.printStackTrace()
            false
        }
    }

    fun getAllOrders(): List<Orders> {
        val orderList = mutableListOf<Orders>()
        val db = this.readableDatabase
        val cursor = db.query(TABLE_ORDERS, arrayOf(
            COLUMN_ORDERS_ID,
            COLUMN_ORDERS_DATAPEDIDO,
            COLUMN_ORDERS_VALORTOT,
            COLUMN_ORDERS_STATUS,
            COLUMN_ORDERS_OBS,
        ), null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val order = Orders(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDERS_ID)),
                    dataPedido = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDERS_DATAPEDIDO)),
                    valorTot = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_ORDERS_VALORTOT)),
                    status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDERS_STATUS)),
                    observacao = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDERS_OBS)),
                )
                orderList.add(order)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return orderList
    }



    fun atualizaOrders(orderId: Int,data: String, valTot: String, status: String, obs: String): Boolean {
        return try {
            val values = ContentValues().apply {
                put(COLUMN_ORDERS_DATAPEDIDO, data)
                put(COLUMN_ORDERS_VALORTOT, valTot)
                put(COLUMN_ORDERS_STATUS, status)
                put(COLUMN_ORDERS_OBS, obs)
            }
            val db = this.writableDatabase
            val result = db.update(TABLE_ORDERS, values, "$COLUMN_ORDERS_ID = ?", arrayOf(orderId.toString()))
            db.close()
            if (result != -1) {
                true
            } else {
                Log.e("DatabaseError", "Falha ao atualizar a OS no banco de dados.")
                false
            }
        } catch (e: Exception) {
            Log.e("DatabaseError", "Erro ao atualizar a OS", e)
            e.printStackTrace()
            false
        }
    }

    fun deleteId(orderId: Int): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_ORDERS, "$COLUMN_ORDERS_ID = ?",arrayOf(orderId.toString()))
        db.close()
        return result
    }

    fun getOrderByID(orderId: Int): Orders? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_ORDERS,
            null,
            "$COLUMN_ORDERS_ID = ?",
            arrayOf(orderId.toString()),
            null,
            null,
            null
        )
        return if (cursor.moveToFirst()) {
            Orders(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDERS_ID)),
                dataPedido = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDERS_DATAPEDIDO)),
                valorTot = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_ORDERS_VALORTOT)),
                status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDERS_STATUS)),
                observacao = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDERS_OBS)),
            )
        } else {
            null
        }.also {
            cursor.close()
            db.close()
        }
    }

}
