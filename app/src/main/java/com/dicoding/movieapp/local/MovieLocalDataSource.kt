package com.dicoding.movieapp.local

import android.annotation.SuppressLint
import android.content.ContentValues
import com.dicoding.movieapp.responses.DetailResponse
import com.dicoding.movieapp.responses.Image
import com.dicoding.movieapp.responses.OverviewDetailResponses
import com.dicoding.movieapp.responses.Title

class MovieLocalDataSource(private val dbHelper: DatabaseHelper) {

    fun insertMovies(movies: List<DetailResponse>) {
        val db = dbHelper.writableDatabase

        for (movie in movies) {
            val values = ContentValues()
            values.put(DatabaseHelper.KEY_ID, movie.id)
            values.put(DatabaseHelper.KEY_TITLE, movie.title)
            values.put(DatabaseHelper.KEY_YEAR, movie.year)
            values.put(DatabaseHelper.KEY_IMAGE_URL, movie.image?.url)
            values.put(DatabaseHelper.KEY_LAST_UPDATED, System.currentTimeMillis())

            db.insert(DatabaseHelper.TABLE_MOVIES, null, values)
        }

        db.close()
    }

    @SuppressLint("Range")
    fun getMovies(): List<DetailResponse> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_MOVIES}", null)

        val movies = mutableListOf<DetailResponse>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_ID))
                val title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_TITLE))
                val year = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_YEAR))
                val imageUrl = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_IMAGE_URL))

                val movie = DetailResponse(
                    id = id,
                    title = title,
                    year = year,
                    image = Image(url = imageUrl)
                )
                movies.add(movie)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return movies
    }

    fun getLastUpdatedTime(): Long {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT MAX(${DatabaseHelper.KEY_LAST_UPDATED}) FROM ${DatabaseHelper.TABLE_MOVIES}", null)

        var lastUpdatedTime = 0L
        if (cursor.moveToFirst()) {
            lastUpdatedTime = cursor.getLong(0)
        }

        cursor.close()
        db.close()

        return lastUpdatedTime
    }



    @SuppressLint("Range")
    fun getFavoriteMovies(): List<OverviewDetailResponses> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.TABLE_FAVORITE_MOVIES,
            arrayOf(DatabaseHelper.KEY_ID, DatabaseHelper.KEY_TITLE, DatabaseHelper.KEY_RELEASE_DATE, DatabaseHelper.KEY_IMAGE_URL, DatabaseHelper.KEY_CERTIFICATES),
            null,
            null,
            null,
            null,
            null
        )

        val movies = mutableListOf<OverviewDetailResponses>()
        while (cursor.moveToNext()) {
            val id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_ID))
            val title = Title(title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_TITLE)), image = Image(url = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_IMAGE_URL))))
            val releaseDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_RELEASE_DATE))
            val movie = OverviewDetailResponses(id = id, title = title, releaseDate = releaseDate)
            movies.add(movie)
        }
        cursor.close()
        return movies
    }
}
