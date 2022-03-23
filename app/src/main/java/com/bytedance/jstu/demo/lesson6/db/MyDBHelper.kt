package com.bytedance.jstu.demo.lesson6.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDBHelper(val context: Context, name: String, version: Int): SQLiteOpenHelper(context, name, null, version) {

    private val createSessionList = "create table session(" +
            "id integer primary key autoincrement," +
            "avatar text," +
            "name text," +
            "content text)"

    private val createMessageList = "create table message(" +
            "id integer primary key autoincrement," +
            "avatar text," +
            "name text," +
            "content text)"
    private val createUserList = "create table message(" +
            "id integer primary key autoincrement," +
            "avatar text," +
            "name text," +
            "age text)"
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createSessionList)
//        db?.execSQL(createMessageList)
        Toast.makeText(context, "create session db success", Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        if (oldVersion <= 1) {
//            db?.execSQL(createMessageList)
//        }
//        if (oldVersion <= 2) {
//            db?.execSQL(createUserList)
//        }
    }
}