package com.example.pretest_background_and_file_processing_farhan_fadhilah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pretest_background_and_file_processing_farhan_fadhilah.databinding.ActivityMainBinding
import com.example.pretest_background_and_file_processing_farhan_fadhilah.databinding.ActivityPdfHandleBinding

class PdfHandleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPdfHandleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfHandleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            ivBtnWebView.setOnClickListener {
                val intent = Intent(this@PdfHandleActivity, WebViewActivity::class.java)
                startActivity(intent)
            }

            ivBtnAssets.setOnClickListener {
                val intent = Intent(this@PdfHandleActivity, PdfViewActivity::class.java)
                intent.putExtra(KEY_INTENT, "assets")
                startActivity(intent)
            }

            ivBtnStorage.setOnClickListener {
                val intent = Intent(this@PdfHandleActivity, PdfViewActivity::class.java)
                intent.putExtra(KEY_INTENT, "storage")
                startActivity(intent)
            }

            ivBtnInternet.setOnClickListener {
                val intent = Intent(this@PdfHandleActivity, PdfViewActivity::class.java)
                intent.putExtra(KEY_INTENT, "internet")
                startActivity(intent)
            }
        }
    }

    companion object{
        val KEY_INTENT = "ViewType"
    }
}