package com.example.pretest_background_and_file_processing_farhan_fadhilah

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.example.pretest_background_and_file_processing_farhan_fadhilah.databinding.ActivityImageHandleBinding
import java.util.jar.Manifest

class ImageHandleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageHandleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageHandleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnChoose.setOnClickListener {
            checkingPermissions()
        }
    }

    private fun checkingPermissions(){
        if(isGranted(
                this, android.Manifest.permission.CAMERA,
                arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_CODE_PERMISSION
        )){
            chooseImageDialog()
        }else{
            chooseImageDialog()
        }
    }

    private fun isGranted(
        activity: Activity,
        permission: String,
        permissions: Array<String>,
        request: Int
    ): Boolean{
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)){
                showPermissionDeniedDialog()
            }else{
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        }else{
            true
        }
    }

    private fun showPermissionDeniedDialog(){
        AlertDialog.Builder(this)
            .setTitle(R.string.title_denied)
            .setMessage(R.string.message_denied)
            .setPositiveButton(
                R.string.pass_setting
            ){_ ,_->
               val intent = Intent()
               intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
               val uri = Uri.fromParts("package", packageName, null)
               intent.data = uri
               startActivity(intent)
            }.setNegativeButton(R.string.cancel){
                dialog,_ -> dialog.cancel()
            }.show()

    }

    private fun chooseImageDialog(){
        AlertDialog.Builder(this)
            .setMessage(R.string.choose_image)
            .setPositiveButton(R.string.gallery){_, _ -> openGallery()}
            .setNegativeButton(R.string.camera){_, _ -> openCamera()}
            .show()
    }

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()){ result ->
            binding.ivShowImage.setImageURI(result)
        }

    private fun openGallery(){
        intent.type = "image/*"
        galleryResult.launch("image/*")
    }

    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == Activity.RESULT_OK){
                handleCameraImage(result.data)
            }
        }

    private fun handleCameraImage(intent: Intent?){
        val bitmap = intent?.extras?.get("data") as Bitmap
        binding.ivShowImage.setImageBitmap(bitmap)
    }

    private fun openCamera(){
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraResult.launch(cameraIntent)
    }

    companion object{
        private val REQUEST_CODE_PERMISSION = 100
    }

}