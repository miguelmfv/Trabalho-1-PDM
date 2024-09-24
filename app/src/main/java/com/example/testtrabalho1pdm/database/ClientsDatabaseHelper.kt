package com.example.testtrabalho1pdm.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.testtrabalho1pdm.model.Clients
import com.example.testtrabalho1pdm.model.Users

class ClientsDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "DBCLIENTSTrabalho1PDM.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_CLIENTS = "clients"
        private const val COLUMN_CLIENTS_ID = "id"
        private const val COLUMN_CLIENTS_NAME = "nome"
        private const val COLUMN_CLIENTS_EMAIL = "email"
        private const val COLUMN_CLIENTS_FONE = "telefone"
        private const val COLUMN_CLIENTS_ENDER = "endereco"
        private const val COLUMN_CLIENTS_DATANASC = "dataNasc"

    }

    override fun onCreate(db: SQLiteDatabase) {
        val createClientsTable = """
            CREATE TABLE $TABLE_CLIENTS (
                $COLUMN_CLIENTS_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_CLIENTS_NAME TEXT NOT NULL,
                $COLUMN_CLIENTS_EMAIL TEXT NOT NULL,
                $COLUMN_CLIENTS_FONE TEXT,
                $COLUMN_CLIENTS_ENDER TEXT,
                $COLUMN_CLIENTS_DATANASC TEXT
            )
        """.trimIndent()
        db.execSQL(createClientsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CLIENTS")
        onCreate(db)
    }

    fun addClient(name: String, email: String, telefone: String, endereco: String, dataNasc: String): Boolean {
        return try {
            val values = ContentValues().apply {
                put(COLUMN_CLIENTS_NAME, name)
                put(COLUMN_CLIENTS_EMAIL, email)
                put(COLUMN_CLIENTS_FONE, telefone)
                put(COLUMN_CLIENTS_ENDER, endereco)
                put(COLUMN_CLIENTS_DATANASC, dataNasc)
            }
            val db = this.writableDatabase
            val result = db.insert(TABLE_CLIENTS, null, values)
            db.close()
            if (result != -1L) {
                true
            } else {
                Log.e("DatabaseError", "Falha ao inserir cliente no banco de dados.")
                false
            }
        } catch (e: Exception) {
            Log.e("DatabaseError", "Erro ao adicionar cliente", e)
            e.printStackTrace()
            false
        }
    }

    fun getAllClients(): List<Clients> {
        val clientList = mutableListOf<Clients>()
        val db = this.readableDatabase
        val cursor = db.query(TABLE_CLIENTS, arrayOf(
            COLUMN_CLIENTS_ID,
            COLUMN_CLIENTS_NAME,
            COLUMN_CLIENTS_EMAIL,
            COLUMN_CLIENTS_FONE,
            COLUMN_CLIENTS_ENDER,
            COLUMN_CLIENTS_DATANASC
        ), null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val client = Clients(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CLIENTS_ID)),
                    nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENTS_NAME)),
                    email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENTS_EMAIL)),
                    telefone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENTS_FONE)),
                    endereco = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENTS_ENDER)),
                    dataNasc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENTS_DATANASC))
                )
                clientList.add(client)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return clientList
    }



    fun atualizaClient(clientId: Int, cNome: String, cEmail: String, cFone: String, cEnder: String, cNasc: String): Boolean {
        return try {
            val values = ContentValues().apply {
                put(COLUMN_CLIENTS_NAME, cNome)
                put(COLUMN_CLIENTS_EMAIL, cEmail)
                put(COLUMN_CLIENTS_FONE, cFone)
                put(COLUMN_CLIENTS_ENDER, cEnder)
                put(COLUMN_CLIENTS_DATANASC, cNasc)
            }
            val db = this.writableDatabase
            val result = db.update(TABLE_CLIENTS, values, "$COLUMN_CLIENTS_ID = ?", arrayOf(clientId.toString()))
            db.close()
            if (result != -1) {
                true
            } else {
                Log.e("DatabaseError", "Falha ao atualizar cliente no banco de dados.")
                false
            }
        } catch (e: Exception) {
            Log.e("DatabaseError", "Erro ao atualizar cliente", e)
            e.printStackTrace()
            false
        }
    }

    fun deleteId(clientId: Int): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_CLIENTS, "$COLUMN_CLIENTS_ID = ?",arrayOf(clientId.toString()))
        db.close()
        return result
    }

    fun getClientByID(id: Int): Clients? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_CLIENTS,
            null,
            "$COLUMN_CLIENTS_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        return if (cursor.moveToFirst()) {
            Clients(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CLIENTS_ID)),
                nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENTS_NAME)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENTS_EMAIL)),
                telefone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENTS_FONE)),
                endereco = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENTS_ENDER)),
                dataNasc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENTS_DATANASC))
            )
        } else {
            null
        }.also {
            cursor.close()
            db.close()
        }
    }

}