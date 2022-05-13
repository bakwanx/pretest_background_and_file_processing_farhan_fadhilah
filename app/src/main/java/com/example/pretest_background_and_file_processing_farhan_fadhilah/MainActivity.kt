package com.example.pretest_background_and_file_processing_farhan_fadhilah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pretest_background_and_file_processing_farhan_fadhilah.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            ivBtnPdfHandle.setOnClickListener {
                val intent = Intent(this@MainActivity, PdfHandleActivity::class.java)
                startActivity(intent)
            }
            ivBtnImageHandle.setOnClickListener {
                val intent = Intent(this@MainActivity, ImageHandleActivity::class.java)
                startActivity(intent)
            }
            ivBtnVideoHandle.setOnClickListener {
                val intent = Intent(this@MainActivity, VideoHandleActivity::class.java)
                startActivity(intent)
            }
        }
    }
}