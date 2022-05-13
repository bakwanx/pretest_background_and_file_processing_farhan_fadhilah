package com.example.pretest_background_and_file_processing_farhan_fadhilah.utils

import android.content.Context
import android.os.Environment
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.io.File

class Utils {

    companion object{
        fun getPdfUrl() : String = "https://kotlinlang.org/assets/kotlin-media-kit.pdf"

        fun getPdfNameFrommAssets() : String = "kotlin_media_kit.pdf"

        fun showToast(context: Context, message: String){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        fun getRoorDirPath(context: Context): String{
            return if(Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()){
                val file: File = ContextCompat.getExternalFilesDirs(
                    context.applicationContext,
                    null
                )[0]
                file.absolutePath
            }else{
                context.applicationContext.filesDir.absolutePath
            }
        }

        fun videoUrl() : String = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    }




}