package com.bytedance.jstu.demo.lesson6.cp

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.bytedance.jstu.demo.lesson6.db.MyDBHelper

class MyProvider : ContentProvider() {

    private val sessionDir = 0
    private val sessionItem = 1
    private val authority = "com.bytedance.jstu.demo.provider"
    private var dbHelper: MyDBHelper? = null
    private val uriMatcher by lazy {
        val matcher = UriMatcher(UriMatcher.NO_MATCH)
        matcher.addURI(authority, "session", sessionDir)
        matcher.addURI(authority, "session/*", sessionItem)
        matcher
    }

    override fun onCreate(): Boolean {
        return context?.let {
            dbHelper = MyDBHelper(it, "Chat.db", 1)
            true
        } ?: false
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        dbHelper?.let {
            val db = it.writableDatabase
            val cursor = when(uriMatcher.match(uri)) {
                sessionDir -> db.query("session", projection, selection, selectionArgs, null, null, sortOrder)
                sessionItem -> db.query("session", projection, "name = ?", arrayOf(uri.pathSegments[1]), null, null, sortOrder)
                else -> null
            }
            return cursor
        }
        return null
    }

    override fun getType(uri: Uri): String? {
        return when(uriMatcher.match(uri)) {
            sessionDir -> "vnd.android.cursor.dir/vnd.com.bytedance.jstu.demo.provider.session"
            sessionItem -> "vnd.android.cursor.item/vnd.com.bytedance.jstu.demo.provider.session"
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        dbHelper?.let {
            val db = it.writableDatabase
            val uri = when(uriMatcher.match(uri)) {
                sessionDir, sessionItem -> {
                    val newSessionId = db.insert("session", null, values)
                    Uri.parse("content://$authority/session/$newSessionId")
                }
                else -> null
            }
            return uri
        }
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        dbHelper?.let {
            val db = it.writableDatabase
            val deleteRows = when(uriMatcher.match(uri)) {
                sessionDir -> db.delete("session", selection, selectionArgs)
                sessionItem -> db.delete("session", "name = ?", arrayOf(uri.pathSegments[1]))
                else -> 0
            }
            return deleteRows
        }
        return 0
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        dbHelper?.let {
            val db = it.writableDatabase
            val updateRows = when(uriMatcher.match(uri)) {
                sessionDir -> db.update("session", values, selection, selectionArgs)
                sessionItem -> db.update("session", values, "name = ?", arrayOf(uri.pathSegments[1]))
                else -> 0
            }
            return updateRows
        }
        return 0
    }
}