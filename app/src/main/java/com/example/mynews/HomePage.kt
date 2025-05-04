package com.example.mynews

import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.kwabenaberko.newsapilib.models.Article

@Composable
fun HomePage(newsViewModel: NewsViewModel, navController: NavHostController){
    val articles by newsViewModel.articles.observeAsState(emptyList())
    Column(modifier = Modifier.fillMaxSize()){
        CategoriesBar(newsViewModel)
        LazyColumn (modifier = Modifier.fillMaxSize()){
            items(articles){ article->
                ArticleItem(article , navController)
            }
        }
    }
}
@Composable
fun ArticleItem(article: Article, navController: NavHostController){
    Card (
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = {
            navController.navigate(NewsArticlePageScreen(article.url))
        }
    ){
        Row(modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = article.urlToImage?:"https://cdn.pixabay.com/photo/2015/02/15/09/33/news-636978_1280.jpg",
                contentDescription = "News Image",
                modifier = Modifier.size(110.dp).aspectRatio(1f).clip(RoundedCornerShape(10)),
                contentScale = ContentScale.Crop,

                )
            Column(modifier = Modifier.padding(start = 8.dp, top = 4.dp, bottom = 4.dp,end = 4.dp).fillMaxSize()) {
                Text(text = article.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp, maxLines = 2
                )
                Text(text = article.description?:"",
                    fontSize = 14.sp, maxLines = 2
                )

                Text(text = article.source.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp, maxLines = 1
                )
            }
        }
    }
}
@Composable
fun CategoriesBar(newsViewModel: NewsViewModel){
    var searchQuery by remember {
        mutableStateOf("")
    }
    var isSearchActive by remember {
        mutableStateOf(false)
    }
    val categories = listOf("General", "Business", "Entertainment", "Health", "Science", "Sports", "Technology")
    Row(modifier = Modifier.fillMaxWidth()
        .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically) {
        if(isSearchActive){
            OutlinedTextField(modifier = Modifier.padding(8.dp)
                .height(50.dp)
                .border(1.dp, Color.Gray, CircleShape)
                .clip(CircleShape)
                .align(Alignment.CenterVertically),
                value = searchQuery, onValueChange = { searchQuery = it },
                placeholder = { Text(text = "Search News..." , fontSize = 16.sp ) },
                textStyle = TextStyle.Default.copy(fontSize = 16.sp),
                leadingIcon = {
                    IconButton({ isSearchActive=false }, modifier = Modifier.size(16.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                },
                trailingIcon = {
                    IconButton({
                        if(searchQuery.isNotEmpty()){
                            newsViewModel.fetchEverythingWithQuery(searchQuery)
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        }else{
            IconButton({ isSearchActive=true }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        }
        categories.forEach { category->
            Button(onClick = {
                newsViewModel.fetchNewsTopHeadlines(category = category)
            },
                modifier = Modifier.padding(4.dp), elevation = ButtonDefaults.elevatedButtonElevation(4.dp)) {
                Text(text = category)
            }

        }
    }
}


