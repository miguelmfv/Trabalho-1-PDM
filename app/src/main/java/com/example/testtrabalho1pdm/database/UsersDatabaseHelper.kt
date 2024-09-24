package com.example.testtrabalho1pdm.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.testtrabalho1pdm.model.Users

class UsersDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "DBTrabalho1PDM.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_USERS = "users"
        private const val COLUMN_USERS_ID = "id"
        private const val COLUMN_USERS_NAME = "userName"
        private const val COLUMN_PASS = "password"

    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUsersTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_USERS_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USERS_NAME TEXT,
                $COLUMN_PASS TEXT
            )
        """.trimIndent()
        db.execSQL(createUsersTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun addUser(userName: String, password: String): Boolean {
        return try {
            val values = ContentValues().apply {
                put(COLUMN_USERS_NAME, userName)
                put(COLUMN_PASS, password)
            }
            val db = this.writableDatabase
            val result = db.insert(TABLE_USERS, null, values)
            db.close()
            if (result != -1L) {
                true
            } else {
                Log.e("DatabaseError", "Falha ao inserir usuário no banco de dados.")
                false
            }
        } catch (e: Exception) {
            Log.e("DatabaseError", "Erro ao adicionar usuário", e)
            e.printStackTrace()
            false
        }
    }

    fun getUserByUName(userName: String): Users? {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_USERS, arrayOf(COLUMN_USERS_ID, COLUMN_USERS_NAME, COLUMN_PASS), "$COLUMN_USERS_NAME = ?", arrayOf(userName), null, null, null)
        var user: Users? = null
        if(cursor.moveToFirst()){
            user = Users(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USERS_ID)),
                userName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERS_NAME)),
                password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASS))
            )
        }
        cursor.close()
        db.close()
        return user
    }
}