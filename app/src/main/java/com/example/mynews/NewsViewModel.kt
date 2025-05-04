package com.example.mynews

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.Article
import com.kwabenaberko.newsapilib.models.request.EverythingRequest
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse

class NewsViewModel: ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    private val _articles = MutableLiveData<List<Article>>()
    val articles: MutableLiveData<List<Article>> = _articles
    init {
        fetchNewsTopHeadlines()
    }
    fun fetchNewsTopHeadlines(category: String = "General"){
        isLoading.postValue(true)
        val newsApiClient = NewsApiClient(Constant.apiKey)
        val request = TopHeadlinesRequest.Builder().language("en").category(category).build()
        newsApiClient.getTopHeadlines(request, object : NewsApiClient.ArticlesResponseCallback{
            override fun onSuccess(response: ArticleResponse?) {
                response?.articles?.let {
                    _articles.postValue(it)
                }
                isLoading.postValue(false)
            }

            override fun onFailure(throwable: Throwable?) {
                Log.i("NewsApi Response Failed", throwable.toString())
                isLoading.postValue(false)
            }

        })

    }

    //api  for search
    fun fetchEverythingWithQuery(query: String){
        isLoading.postValue(true)
        val newsApiClient = NewsApiClient(Constant.apiKey)
        val request = EverythingRequest.Builder().language("en").q(query).build()
        newsApiClient.getEverything(request, object : NewsApiClient.ArticlesResponseCallback{
            override fun onSuccess(response: ArticleResponse?) {
                response?.articles?.let {
                    _articles.postValue(it)
                }
                isLoading.postValue(false)
            }

            override fun onFailure(throwable: Throwable?) {
                Log.i("NewsApi Response Failed", throwable.toString())
                isLoading.postValue(false)
            }

        })

    }
}