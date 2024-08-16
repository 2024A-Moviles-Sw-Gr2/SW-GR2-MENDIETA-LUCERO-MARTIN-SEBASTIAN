package com.example.proyectoiib_kronos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelper(
    context: Context?
): SQLiteOpenHelper(context, "KronosApp", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createCategoryTable = """
            CREATE TABLE CATEGORY(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name VARCHAR(100)
            );
        """.trimIndent()

        val createEventTable = """
            CREATE TABLE EVENT(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title VARCHAR(100),
                date VARCHAR(20),
                hour VARCHAR(20),
                category_id INTEGER,
                FOREIGN KEY (category_id) REFERENCES CATEGORY(id) ON DELETE CASCADE
            );
        """.trimIndent()

        db?.execSQL(createEventTable)
        db?.execSQL(createCategoryTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    fun getAllCategories():ArrayList<CategoryEntity> {
        val lectureDB =readableDatabase
        val queryScript ="""
            SELECT * FROM CATEGORY
        """.trimIndent()
        val queryResult = lectureDB.rawQuery(
            queryScript,
            emptyArray()
        )
        val response = arrayListOf<CategoryEntity>()

        if(queryResult.moveToFirst()) {
            do {
                response.add(
                    CategoryEntity(
                        queryResult.getInt(0),
                        queryResult.getString(1)
                    )
                )
            } while(queryResult.moveToNext())
        }
        queryResult.close()
        lectureDB.close()

        return response
    }

    fun getEventsByDate(date: String):ArrayList<EventEntity> {
        val lectureDB =readableDatabase
        val queryScript ="""
            SELECT * FROM EVENT WHERE date=?
        """.trimIndent()
        val queryResult = lectureDB.rawQuery(
            queryScript,
            arrayOf(date)
        )
        val response = arrayListOf<EventEntity>()

        if(queryResult.moveToFirst()) {
            do {
                response.add(
                    EventEntity(
                        queryResult.getInt(0),
                        queryResult.getString(1),
                        queryResult.getString(2),
                        queryResult.getString(3),
                        queryResult.getInt(4)
                    )
                )
            } while(queryResult.moveToNext())
        }
        queryResult.close()
        lectureDB.close()

        return response
    }

    fun createEvent(
        title: String,
        date: String,
        hour: String,
        category_id: Int
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToStore = ContentValues()
        valuesToStore.put("title", title)
        valuesToStore.put("date", date)
        valuesToStore.put("hour", hour)
        valuesToStore.put("category_id", category_id)

        val storeResult = writeDB.insert(
            "EVENT", // Table name
            null,
            valuesToStore // Values to insert
        )
        writeDB.close()

        return storeResult.toInt() != -1
    }

    fun createCategory(
        name: String,
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToStore = ContentValues()
        valuesToStore.put("name", name)

        val storeResult = writeDB.insert(
            "CATEGORY", // Table name
            null,
            valuesToStore // Values to insert
        )
        writeDB.close()

        return storeResult.toInt() != -1
    }

    fun findCategoryByName(categoryName: String): CategoryEntity? {
        val lectureDB =readableDatabase
        val queryScript ="""
            SELECT * FROM CATEGORY WHERE name=?
        """.trimIndent()
        val queryResult = lectureDB.rawQuery(
            queryScript,
            arrayOf(categoryName)
        )
        val response = arrayListOf<CategoryEntity>()

        if(queryResult.moveToFirst()) {
            do {
                response.add(
                    CategoryEntity(
                        queryResult.getInt(0),
                        queryResult.getString(1),
                    )
                )
            } while(queryResult.moveToNext())
        }
        queryResult.close()
        lectureDB.close()

        return if (response.size > 0)  response[0] else  null
    }

    fun findCategoryByID(categoryID: Int?): CategoryEntity? {
        val lectureDB =readableDatabase
        val queryScript ="""
            SELECT * FROM CATEGORY WHERE id=?
        """.trimIndent()
        val queryResult = lectureDB.rawQuery(
            queryScript,
            arrayOf(categoryID?.toString())
        )
        val response = arrayListOf<CategoryEntity>()

        if(queryResult.moveToFirst()) {
            do {
                response.add(
                    CategoryEntity(
                        queryResult.getInt(0),
                        queryResult.getString(1),
                    )
                )
            } while(queryResult.moveToNext())
        }
        queryResult.close()
        lectureDB.close()

        return if (response.size > 0)  response[0] else  null
    }

    fun updateEvent(
        id: Int,
        title: String,
        date: String,
        hour: String,
        category_id: Int
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToUpdate = ContentValues()
        valuesToUpdate.put("title", title)
        valuesToUpdate.put("date", date)
        valuesToUpdate.put("hour", hour)
        valuesToUpdate.put("category_id", category_id)

        val parametersUpdateQuery = arrayOf(id.toString())
        val updateResult = writeDB.update(
            "EVENT", // Table name
            valuesToUpdate,
            "id=?",
            parametersUpdateQuery
        )
        writeDB.close()

        return updateResult != -1
    }

    fun deleteEvent(id:Int): Boolean {
        val writeDB = writableDatabase
        // SQL query example: where .... ID=? AND NAME=? [?=1, ?=2]
        val parametersDeleteQuery = arrayOf(id.toString())
        val deleteResult = writeDB.delete(
            "EVENT",
            "id=?",
            parametersDeleteQuery
        )
        writeDB.close()

        return deleteResult != -1
    }
}