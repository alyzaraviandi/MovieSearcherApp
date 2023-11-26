package com.dicoding.movieapp.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dicoding.movieapp.R
import com.dicoding.movieapp.ui.theme.MovieAppTheme

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Add a background image
                    Image(
                        painter = painterResource(id = R.drawable.screenbackgrounddrawable),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // Add a column to align the profile details vertically
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Display the photo
                        Image(
                            painter = painterResource(id = R.drawable.photoprofile),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(128.dp)
                                .clip(CircleShape)
                        )

                        // Display the name
                        Text(
                            text = "Alyzar Aviandi",
                            style = TextStyle(
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Normal,
                                fontSize = 24.sp
                            ),
                            color = Color.White,
                            modifier = Modifier.padding(top = 16.dp)
                        )

                        // Display the email
                        Text(
                            text = "alyzaraviandi@gmail.com",
                            style = TextStyle(
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp
                            ),
                            color = Color.White,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}


