package com.example.appcamara

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.appcamara.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() , OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var  file: File
    private var rutafotoactual=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btncamara.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btncamara-> Tomarfoto()
            R.id.btncamara-> ConpartirFoto()


        }

    }

    private fun ConpartirFoto() {

    }

    private fun Tomarfoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).also{
            it.resolveActivity(packageManager).also{
                    componente->
                creararchivofoto()
                val fotoUri: Uri=
                    FileProvider.getUriForFile(
                        applicationContext,"com.example.appcamara.fileprovider",file
                    )
                it.putExtra(MediaStore.EXTRA_OUTPUT,fotoUri)
            }
        }
        abrirCamara.launch(intent)
    }

    private val abrirCamara =registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if(result.resultCode== RESULT_OK){
            /* val data = result.data!!
            val imagenBitmap = data.extras!!.get("data") as Bitmap */
            binding.ivimagen.setImageBitmap(Obtenerimagenbitmap())

        }
    }

    private fun Obtenerimagenbitmap(): Bitmap {
        return BitmapFactory.decodeFile(file.toString())

    }

    private fun creararchivofoto(){
        val directorioImg=getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        file = File.createTempFile("IMG_${System.currentTimeMillis()}_", ".jpg",directorioImg)
        rutafotoactual=file.absolutePath
    }

}