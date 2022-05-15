package com.example.pretest_background_and_file_processing_farhan_fadhilah

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.example.pretest_background_and_file_processing_farhan_fadhilah.PdfHandleActivity.Companion.KEY_INTENT
import com.example.pretest_background_and_file_processing_farhan_fadhilah.databinding.ActivityPdfViewBinding
import com.example.pretest_background_and_file_processing_farhan_fadhilah.utils.Utils
import java.io.File

class PdfViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPdfViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPdfAction(intent)
        PRDownloader.initialize(applicationContext)
    }

    private fun checkPdfAction(intent: Intent){
        when(intent.getStringExtra(KEY_INTENT)){
            "assets" -> {
                showPdfFromAssets(Utils.getPdfNameFrommAssets())
            }
            "storage" -> {
                selectPdfFromStorage()
            }
            "internet" -> {
                val fileName = "myFile.pdf"

                downloadPdfFromInternet(
                    Utils.getPdfUrl(),
                    Utils.getRoorDirPath(this),
                    fileName
                )
            }
        }
    }

    private fun showPdfFromAssets(pdfName: String){
        binding.pdfView.fromAsset(pdfName)
            .password(null)
            .defaultPage(0)
            .onPageError { page, _ ->
                Utils.showToast(
                    this,
                    "${getString(R.string.error_at_page)} $page"
                )
            }
            .load()
        binding.progressBar.visibility = View.GONE
    }

    private fun selectPdfFromStorage(){
        Utils.showToast(
            this,
            getString(R.string.choose_pdf)
        )
        val browseStorage = Intent(Intent.ACTION_GET_CONTENT)
        browseStorage.type = "application/pdf"
        browseStorage.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(
            Intent.createChooser(browseStorage, getString(R.string.choose_pdf)), PDF_SELECTION_CODE
        )
    }

    private fun showPdfFromUri(uri: Uri?){
        binding.pdfView.fromUri(uri)
            .defaultPage(0)
            .spacing(10)
            .load()
        binding.progressBar.visibility = View.GONE
    }

    private fun downloadPdfFromInternet(url: String, dirPath: String, fileName: String){
        PRDownloader.download(
            url,
            dirPath,
            fileName
        ).build()
            .start(object : OnDownloadListener{
                override fun onDownloadComplete() {
                    Utils.showToast(this@PdfViewActivity,getString(R.string.download_completed))
                    val downloadFile = File(dirPath, fileName)
                    binding.progressBar.visibility = View.GONE
                    showPdfFromFile(downloadFile)
                }

                override fun onError(error: Error?) {
                    Utils.showToast(this@PdfViewActivity,"${R.string.download_error} : $error")
                    binding.progressBar.visibility = View.GONE
                }

            })
    }

    private fun showPdfFromFile(file: File){
        binding.pdfView.fromFile(file)
            .password(null)
            .defaultPage(0)
            .enableSwipe(true)
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .onPageError{ page, _ ->
                Utils.showToast(this,"${getString(R.string.error_at_page)}: $page")
            }.load()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PDF_SELECTION_CODE && resultCode == Activity.RESULT_OK && data != null){
            val selectedPdfFromStorage = data.data
            showPdfFromUri(selectedPdfFromStorage)
        }else{
            finish()
        }
    }

    companion object{
        private const val PDF_SELECTION_CODE = 99
    }

}