package com.example.a03_android_app

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelper(
    context: Context? /* this */
) : SQLiteOpenHelper(context, "AndroidApp", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createAuthorTable = """
            CREATE TABLE AUTHOR(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name VARCHAR(100),
                age INTEGER,
                literary_genre VARCHAR(50),
                latitude VARCHAR(50),
                longitude VARCHAR(50)
            );
        """.trimIndent()

        val createBookTable = """
            CREATE TABLE BOOK(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title VARCHAR(100),
                description VARCHAR(200),
                author_id INTEGER,
                FOREIGN KEY (author_id) REFERENCES AUTHOR(id) ON DELETE CASCADE
            );
        """.trimIndent()

        db?.execSQL(createAuthorTable)
        db?.execSQL(createBookTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun getAllAuthors():ArrayList<AuthorEntity> {
        val lectureDB =readableDatabase
        val queryScript ="""
            SELECT * FROM AUTHOR
        """.trimIndent()
        val queryResult = lectureDB.rawQuery(
            queryScript,
            emptyArray()
        )
        val response = arrayListOf<AuthorEntity>()

        if(queryResult.moveToFirst()) {
            do {
                response.add(
                    AuthorEntity(
                        queryResult.getInt(0),
                        queryResult.getString(1),
                        queryResult.getInt(2),
                        queryResult.getString(3),
                        queryResult.getString(4),
                        queryResult.getString(5)
                    )
                )
            } while(queryResult.moveToNext())
        }
        queryResult.close()
        lectureDB.close()

        return response
    }

    fun getBooksByAuthor(author_id: Int):ArrayList<BookEntity> {
        val lectureDB =readableDatabase
        val queryScript ="""
            SELECT * FROM BOOK WHERE author_id=?
        """.trimIndent()
        val queryResult = lectureDB.rawQuery(
            queryScript,
            arrayOf(author_id.toString())
        )
        val response = arrayListOf<BookEntity>()

        if(queryResult.moveToFirst()) {
            do {
                response.add(
                    BookEntity(
                        queryResult.getInt(0),
                        queryResult.getString(1),
                        queryResult.getString(2),
                        queryResult.getInt(3)
                    )
                )
            } while(queryResult.moveToNext())
        }
        queryResult.close()
        lectureDB.close()

        return response
    }

    fun createAuthor(
        name: String,
        age: Int,
        literary_genre: String,
        latitude: String,
        longitude: String
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToStore = ContentValues()
        valuesToStore.put("name", name)
        valuesToStore.put("age", age)
        valuesToStore.put("literary_genre", literary_genre)
        valuesToStore.put("latitude", latitude)
        valuesToStore.put("longitude", longitude)

        val storeResult = writeDB.insert(
            "AUTHOR", // Table name
            null,
            valuesToStore // Values to insert
        )
        writeDB.close()

        return storeResult.toInt() != -1
    }

    fun createBook(
        title: String,
        description: String,
        author_id: Int
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToStore = ContentValues()
        valuesToStore.put("title", title)
        valuesToStore.put("description", description)
        valuesToStore.put("author_id", author_id)

        val storeResult = writeDB.insert(
            "BOOK", // Table name
            null,
            valuesToStore // Values to insert
        )
        writeDB.close()

        return storeResult.toInt() != -1
    }

    fun updateAuthor(
        id: Int,
        name: String,
        age: Int,
        literary_genre: String,
        latitude: String,
        longitude: String
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToUpdate = ContentValues()
        valuesToUpdate.put("name", name)
        valuesToUpdate.put("age", age)
        valuesToUpdate.put("literary_genre", literary_genre)
        valuesToUpdate.put("latitude", latitude)
        valuesToUpdate.put("longitude", longitude)

        val parametersUpdateQuery = arrayOf(id.toString())
        val updateResult = writeDB.update(
            "AUTHOR", // Table name
            valuesToUpdate,
            "id=?",
            parametersUpdateQuery
        )
        writeDB.close()

        return updateResult != -1
    }

    fun updateBook(
        id: Int,
        title: String,
        description: String,
        author_id: Int
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToUpdate = ContentValues()
        valuesToUpdate.put("title", title)
        valuesToUpdate.put("description", description)
        valuesToUpdate.put("author_id", author_id)

        val parametersUpdateQuery = arrayOf(id.toString())
        val updateResult = writeDB.update(
            "BOOK", // Table name
            valuesToUpdate,
            "id=?",
            parametersUpdateQuery
        )
        writeDB.close()

        return updateResult != -1
    }

    fun deleteAuthor(id:Int): Boolean {
        val writeDB = writableDatabase
        // SQL query example: where .... ID=? AND NAME=? [?=1, ?=2]
        val parametersDeleteQuery = arrayOf(id.toString())
        val deleteResult = writeDB.delete(
            "AUTHOR",
            "id=?",
            parametersDeleteQuery
        )
        writeDB.close()

        return deleteResult != -1
    }

    fun deleteBook(id:Int): Boolean {
        val writeDB = writableDatabase
        // SQL query example: where .... ID=? AND NAME=? [?=1, ?=2]
        val parametersDeleteQuery = arrayOf(id.toString())
        val deleteResult = writeDB.delete(
            "BOOK",
            "id=?",
            parametersDeleteQuery
        )
        writeDB.close()

        return deleteResult != -1
    }

}