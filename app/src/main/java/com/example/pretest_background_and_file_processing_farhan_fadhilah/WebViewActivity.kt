package com.example.pretest_background_and_file_processing_farhan_fadhilah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import com.example.pretest_background_and_file_processing_farhan_fadhilah.databinding.ActivityWebViewBinding
import com.example.pretest_background_and_file_processing_farhan_fadhilah.utils.Utils

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            progressBarWebView.visibility = View.VISIBLE
            webView.webViewClient = WebViewClient()
            webView.settings.setSupportZoom(true)
            webView.settings.javaScriptEnabled = true
            val url = Utils.getPdfUrl()
            webView.loadUrl("https://docs.google.com/gview?embedded=true&url=$url")
            progressBarWebView.visibility = View.GONE
        }
    }
}