@file:Suppress("LocalVariableName")

package com.dicoding.movieapp.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "MovieDatabase"
        const val TABLE_MOVIES = "movies"
        const val TABLE_FAVORITE_MOVIES = "favoriteMovies"
        const val KEY_ID = "id"
        const val KEY_TITLE = "title"
        const val KEY_YEAR = "year"
        const val KEY_IMAGE_URL = "image_url"
        const val KEY_LAST_UPDATED = "last_updated"
        const val KEY_RELEASE_DATE = "releaseDate"
        const val KEY_CERTIFICATES = "certificates"
        const val KEY_GENRES = "genres"
        const val KEY_PLOT_OUTLINE = "plotOutline"
        const val KEY_PLOT_SUMMARY = "plotSummary"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_MOVIES_TABLE = ("CREATE TABLE " + TABLE_MOVIES + "("
                + KEY_ID + " TEXT PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_YEAR + " INTEGER," + KEY_IMAGE_URL + " TEXT,"
                + KEY_LAST_UPDATED + " INTEGER" + ")")
        db.execSQL(CREATE_MOVIES_TABLE)

        val CREATE_FAVORITE_MOVIES_TABLE = ("CREATE TABLE " + TABLE_FAVORITE_MOVIES + "("
                + KEY_ID + " TEXT PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_RELEASE_DATE + " TEXT,"
                + KEY_IMAGE_URL + " TEXT,"
                + KEY_CERTIFICATES + " TEXT,"
                + KEY_GENRES + " TEXT,"
                + KEY_PLOT_OUTLINE + " TEXT,"
                + KEY_PLOT_SUMMARY + " TEXT" +")")
        db.execSQL(CREATE_FAVORITE_MOVIES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MOVIES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FAVORITE_MOVIES")
        onCreate(db)
    }
}




