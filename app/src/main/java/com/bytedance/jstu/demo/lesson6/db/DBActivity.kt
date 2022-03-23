package com.bytedance.jstu.demo.lesson6.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.jstu.demo.R
import java.lang.NullPointerException
import java.lang.StringBuilder

class DBActivity: AppCompatActivity() {

    private val createDB: Button by lazy {
        findViewById(R.id.create_db)
    }

    private val add: Button by lazy {
        findViewById(R.id.add)
    }

    private val query: Button by lazy {
        findViewById(R.id.query)
    }

    private val update: Button by lazy {
        findViewById(R.id.update)
    }

    private val delete: Button by lazy {
        findViewById(R.id.delete)
    }

    private val queryResults: TextView by lazy {
        findViewById(R.id.query_results)
    }

    private val useTransaction: Button by lazy {
        findViewById(R.id.use_transaction)
    }

    private val dbHelper = MyDBHelper(this, "Chat.db", 1)
    private var db: SQLiteDatabase? = null

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_db_layout)
        createDB.setOnClickListener {
            db = dbHelper.writableDatabase
        }

        add.setOnClickListener {
            val values = ContentValues().apply {
                put("avatar", "https://com.xxx.xxx/avatar.png")
                put("name", "Tom")
                put("content", "hello")
            }
            db?.insert("session", null, values)

            val values1 = ContentValues().apply {
                put("avatar", "https://com.xxx.xxx/avatar1.png")
                put("name", "Peter")
                put("content", "how are you")
            }
            db?.insert("session", null, values1)
        }

        update.setOnClickListener {
            val values = ContentValues().apply {
                put("content", "hello world")
            }
            db?.update("session", values, "name = ?", arrayOf("Tom"))
        }

        delete.setOnClickListener {
            db?.delete("session", "name = ?", arrayOf("Peter"))
        }

        query.setOnClickListener {
            val cursor = (db?: dbHelper.writableDatabase).query("session", null, null, null, null, null, null, null)
            val sessionList = mutableListOf<Session>()
            if (cursor.moveToFirst()) {
                do {
                    val avatar = cursor.getString(cursor.getColumnIndex("avatar"))
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val content = cursor.getString(cursor.getColumnIndex("content"))
                    sessionList.add(Session(avatar, name, content))
                } while (cursor.moveToNext())
            }
            cursor.close()
            if (sessionList.isNotEmpty()) {
                val queryResult = StringBuilder().append("query results:" + "\n")
                for (session in sessionList) {
                    queryResult.append(session.avatar + ", " +session.name + ", " + session.content + "\n")
                }
                queryResults.text = queryResult.toString()
            }
        }
        useTransaction.setOnClickListener {
            db?.let {
                it.beginTransaction()
                try {
                    it.delete("session", "name = ?", arrayOf("Tom"))
                    if (true) {
                        throw NullPointerException()
                    }
                    val values = ContentValues().apply {
                        put("avatar", "https://com.xxx.xxx/avatar2.png")
                        put("name", "Bruce")
                        put("content", "haha")
                    }
                    it.insert("session", null, values)
                    it.setTransactionSuccessful()
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    it.endTransaction()
                }
            }
        }
    }
}