package com.xkcd.comics.viewer.app.ui.search

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.xkcd.comics.viewer.app.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.webView.settings.loadWithOverviewMode = true
        binding.webView.settings.useWideViewPort = true
        binding.webView.settings.builtInZoomControls = true
        title = "Search Comics"
        // WebViewClient allows you to handle
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                binding.progressCircular.visibility = View.VISIBLE
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                binding.progressCircular.visibility = View.GONE
            }
        }
        // this will load the url of the website
        binding.webView.loadUrl(SEARCH_URL)
    }

    // on Back button press
    override fun onBackPressed() {
        // if your webview can go back it will go back
        if (binding.webView.canGoBack())
            binding.webView.goBack()
        // it will exit the application
        else
            super.onBackPressed()
    }

    companion object {
        const val SEARCH_URL = "https://relevantxkcd.appspot.com/"
    }
}