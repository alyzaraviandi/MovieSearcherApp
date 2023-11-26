package com.dicoding.movieapp.activities

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.dicoding.movieapp.local.DatabaseHelper
import com.dicoding.movieapp.responses.OverviewDetailResponses
import com.dicoding.movieapp.responses.ResultsItem
import kotlinx.coroutines.launch

@Composable
fun MovieList(movies: List<ResultsItem>) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    LazyColumn {
        itemsIndexed(movies.chunked(2)) { _, pair ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                pair.forEach { movie ->
                    MovieItem(movie) {
                        coroutineScope.launch {
                            val intent = Intent(context, DetailActivity::class.java).apply {
                                putExtra("MOVIE_ID", movie.id)
                                putExtra("MOVIE_URL", movie.image?.url)
                            }
                            context.startActivity(intent)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieItem(movie: ResultsItem, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(180.dp)
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .height(300.dp)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            // Load image using Coil
            val painter = rememberAsyncImagePainter(model = movie.image?.url)

            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Show a CircularProgressIndicator when the image is loading
            if (painter.state is AsyncImagePainter.State.Loading) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }

        // Title and Year in a Column
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(8.dp)
        ) {
            // Title
            Text(
                text = movie.title.orEmpty(),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                modifier = Modifier.padding(bottom = 4.dp)
            )

            // Year
            Text(
                text = "${movie.year}",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(searchQuery: String, onSearchQueryChanged: (String) -> Unit, onSearchRequested: KeyboardActionScope.() -> Unit) {
    TextField(
        value = searchQuery,
        onValueChange = onSearchQueryChanged,
        label = { Text("Search") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = onSearchRequested
        )
    )
}

@Composable
fun DetailScreen(movie: OverviewDetailResponses, imageUrl: String) {
    val h6 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = Color.Black
    )
    val subtitle1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = Color.Gray
    )
    val body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        color = Color.Black
    )
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 350.dp)
                .padding(8.dp)
        ) {
            val painter = rememberAsyncImagePainter(model = imageUrl)
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop
            )

            if (painter.state is AsyncImagePainter.State.Loading) {
                CircularProgressIndicator(Modifier.align(Alignment.CenterVertically))
            }

            Column(modifier = Modifier.fillMaxHeight()) {
                Text(
                    text = movie.title?.title.orEmpty(),
                    style = h6,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = movie.releaseDate.orEmpty(),
                    style = subtitle1,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }

        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = movie.plotSummary?.text.orEmpty(),
                style = body1,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text(
                text = "Genres: ${movie.genres?.joinToString(", ").orEmpty()}",
                style = body1,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text(
                text = "Rating: ${movie.ratings?.rating.toString()}",
                style = body1,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text(
                text = "Certificates: ${movie.certificates?.uS?.joinToString(", ") { it?.certificate.orEmpty() }}",
                style = body1,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun Fab() {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FloatingActionButton(onClick = { navigateToFavoriteActivity(context) }) {
                Icon(Icons.Filled.Favorite, contentDescription = "Favorite")
            }

            FloatingActionButton(onClick = { navigateToProfileActivity(context) }) {
                Icon(Icons.Filled.AccountCircle, contentDescription = "about_page")
            }
        }
    }
}

fun navigateToFavoriteActivity(context: Context) {
    val intent = Intent(context, FavoriteActivity::class.java)
    context.startActivity(intent)
}

fun navigateToProfileActivity(context: Context) {
    val intent = Intent(context, ProfileActivity::class.java)
    context.startActivity(intent)
}

@Composable
fun FavoriteButton(movie: OverviewDetailResponses, imageUrl: String, dbHelper: DatabaseHelper) {
    var isFavorite by remember { mutableStateOf(checkIfMovieIsFavorite(movie.id, dbHelper)) }
    val icon = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        FloatingActionButton(
            onClick = {
                isFavorite = !isFavorite
                if (isFavorite) {
                    addMovieToFavorites(movie, imageUrl, dbHelper)
                } else {
                    removeMovieFromFavorites(movie.id, dbHelper)
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(icon, contentDescription = "Favorite")
        }
    }
}

fun addMovieToFavorites(movie: OverviewDetailResponses, imageUrl: String, dbHelper: DatabaseHelper) {
    val db = dbHelper.writableDatabase

    val values = ContentValues().apply {
        put(DatabaseHelper.KEY_ID, movie.id)
        put(DatabaseHelper.KEY_TITLE, movie.title?.title)
        put(DatabaseHelper.KEY_RELEASE_DATE, movie.releaseDate)
        put(DatabaseHelper.KEY_IMAGE_URL, imageUrl)
        put(DatabaseHelper.KEY_GENRES, movie.genres?.joinToString(", "))
        put(DatabaseHelper.KEY_PLOT_OUTLINE, movie.plotOutline?.text)
        put(DatabaseHelper.KEY_PLOT_SUMMARY, movie.plotSummary?.text)
        put(DatabaseHelper.KEY_CERTIFICATES, movie.certificates?.uS?.joinToString(", ") { it?.certificate.orEmpty() })
    }

    db.insert(DatabaseHelper.TABLE_FAVORITE_MOVIES, null, values)
}


fun removeMovieFromFavorites(movieId: String?, dbHelper: DatabaseHelper) {
    val db = dbHelper.writableDatabase
    db.delete(DatabaseHelper.TABLE_FAVORITE_MOVIES, "${DatabaseHelper.KEY_ID} = ?", arrayOf(movieId))
}

fun checkIfMovieIsFavorite(movieId: String?, dbHelper: DatabaseHelper): Boolean {
    val db = dbHelper.readableDatabase
    val cursor = db.query(
        DatabaseHelper.TABLE_FAVORITE_MOVIES,
        arrayOf(DatabaseHelper.KEY_ID),
        "${DatabaseHelper.KEY_ID} = ?",
        arrayOf(movieId),
        null,
        null,
        null
    )
    val isFavorite = cursor.moveToFirst()
    cursor.close()
    return isFavorite
}

@Composable
fun FavoriteMovieList(movies: List<OverviewDetailResponses>, onMovieClick: (String?, String?) -> Unit) {
    LazyColumn {
        itemsIndexed(movies.chunked(2)) { _, pair ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                pair.forEach { movie ->
                    FavoriteMovieItem(
                        movie = movie,
                        onClick = { onMovieClick(movie.id, movie.title?.image?.url) }
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteMovieItem(movie: OverviewDetailResponses, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(180.dp)
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .height(300.dp)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            // Load image using Coil
            val painter = rememberAsyncImagePainter(model = movie.title?.image?.url)

            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Show a CircularProgressIndicator when the image is loading
            if (painter.state is AsyncImagePainter.State.Loading) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }

        // Title and Release Date in a Column
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(8.dp)
        ) {
            // Title
            Text(
                text = movie.title?.title.orEmpty(),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                modifier = Modifier.padding(bottom = 4.dp)
            )

            // Release Date
            Text(
                text = movie.releaseDate.orEmpty(),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
            )
        }
    }
}
