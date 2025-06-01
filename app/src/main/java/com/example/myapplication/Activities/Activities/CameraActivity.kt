package com.example.myapplication.Activities.Activities
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.myapplication.R
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CameraActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var btnTakePhoto: Button
    private lateinit var btnOpenCamera: Button

    private var currentPhotoPath: String? = null
    private lateinit var photoURI: Uri

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val REQUEST_CAMERA_PERMISSION = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        imageView = findViewById(R.id.imageView)
        btnTakePhoto = findViewById(R.id.btnTakePhoto)
        btnOpenCamera = findViewById(R.id.btnOpenCamera)

        btnTakePhoto.setOnClickListener {
            if (checkCameraPermission()) {
                dispatchTakePictureIntent()
            } else {
                requestCameraPermission()
            }
        }

        btnOpenCamera.setOnClickListener {

        }
    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_CAMERA_PERMISSION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_CAMERA_PERMISSION -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent()
                } else {
                    Toast.makeText(
                        this,
                        "Permiso de cámara denegado",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
            .format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            "JPEG_${timeStamp}_",  // prefijo
            ".jpg",                // sufijo
            storageDir             // directorio
        ).apply {
            // Guardar la ruta del archivo para usar después
            currentPhotoPath = absolutePath
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    Toast.makeText(
                        this,
                        "Error al crear archivo de imagen",
                        Toast.LENGTH_SHORT
                    ).show()
                    null
                }

                // Continuar solo si el archivo fue creado exitosamente
                photoFile?.also {
                    photoURI = FileProvider.getUriForFile(
                        this,
                        "${packageName}.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // La imagen fue capturada y guardada en currentPhotoPath
            displayImage()
            galleryAddPic()
        }
    }

    private fun displayImage() {
        currentPhotoPath?.let { path ->
            val file = File(path)
            if (file.exists()) {
                val uri = Uri.fromFile(file)
                imageView.setImageURI(uri)

                Toast.makeText(
                    this,
                    "Foto guardada en: $path",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun galleryAddPic() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            currentPhotoPath?.let {
                val f = File(it)
                mediaScanIntent.data = Uri.fromFile(f)
                sendBroadcast(mediaScanIntent)
            }
        }
    }

}