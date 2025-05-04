package com.example.mynews

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

// for showing contents of news on clicking news in homepage
@Composable
fun NewsArticlePage( url: String) {
// now to show content of news article we have to implement webView,  since webView is not in composable , so we goona use AndroidView
    AndroidView(factory = {context->
        WebView(context).apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    })
}