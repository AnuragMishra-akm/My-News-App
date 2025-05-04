package com.example.mynews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.mynews.ui.theme.MyNewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val newsViewModel = NewsViewModel()
        setContent {
            val navController = rememberNavController()
            MyNewsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding).fillMaxSize()){
                        Text(text = "My News",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            color = Color.Blue,
                            fontSize = 25.sp,
                            fontFamily = FontFamily.Monospace
                            )
                        NavHost(navController = navController , startDestination = HomePageScreen){
                            composable<HomePageScreen> { HomePage(newsViewModel,navController) }
                            composable<NewsArticlePageScreen> {
                                val args = it.toRoute<NewsArticlePageScreen>()  // it will get all arguments which is going to passed (written in routes) NewsArticlePageScreen
                                NewsArticlePage(args.url) }
                        }
                    }
                }
            }
        }
    }
}

