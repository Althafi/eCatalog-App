package com.example.mycatalog.ui.form

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.example.mycatalog.R
import com.example.mycatalog.data.local.room.ProductDao
import com.example.mycatalog.data.network.ApiConfig
import com.example.mycatalog.data.network.ApiService
import com.example.mycatalog.data.preferences.UserPreferences
import com.example.mycatalog.data.repository.ProductRepository
import com.example.mycatalog.databinding.ActivityEditProfileBinding
import com.example.mycatalog.ui.camera.CameraActivity
import com.example.mycatalog.ui.camera.CameraActivity.Companion.RESULT_CODE
import com.example.mycatalog.ui.camera.CameraActivity.Companion.RESULT_KEY
import com.example.mycatalog.ui.list.dataStore
import com.example.mycatalog.ui.profile.ProfileActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.concurrent.ExecutorService


class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding

    private lateinit var viewModel: EditProfileViewModel
    private lateinit var pickImage: FloatingActionButton

    private val requestLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result->
        if(result.resultCode == RESULT_CODE && result.data != null){
            val data = result.data?.getStringExtra(RESULT_KEY)
            val imgUri = Uri.parse(data)

            setImage(imgUri)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)





        pickImage = binding.pickImage
        pickImage.setOnClickListener {


            val listPicker = listOf<String>("Galery", "Camera")
            // untuk menampilkan nilai list dalam bentuk dialog
            MaterialDialog(this)
                .title(R.string.pilih_gambar)
                .listItems(items = listPicker) { _, index, _ ->
                    when (index){
                        0 -> {
                            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                            changeImage.launch(pickImg)
                        }
                        1 -> {
                            val intent = Intent(this, CameraActivity::class.java)
                            requestLauncher.launch(intent)
                        }

                    }
                }
                .show()
        }

        ViewModelProvider(
            this,
            EditProfileViewModelFactory(
                ProductRepository(ApiConfig.createService(ApiService::class.java),(ApiConfig.createService(ProductDao::class.java))), UserPreferences(dataStore, this)
            )
        )[EditProfileViewModel::class.java].also { viewModel = it }
        viewModel.userPreferencesFlow.observe(this){

        }



        binding.btnSave.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java).apply {

            startActivity(this)
            }


            userProfile()

        }


    }
    //function yang dipanggil untuk mengambil data dari viewmodel untuk ditampilkan di view
    private fun userProfile(){
        viewModel.userProfile(fName = binding.etFullName.text.toString(), gender = binding.etGender.text.toString(),
                                phone = binding.etPhone.text.toString())
    }
    //function yang dipanggil untuk mengambil data dari viewmodel untuk ditampilkan di view
    private fun setImage(img: Uri?){
        viewModel.setImage(img)
    }

    private val changeImage =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                val imgUri = data?.data
                setImage(imgUri)
            }
        }


    companion object{

        const val TAG = "CameraXApp"
        const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        const val REQUEST_CODE_PERMISSIONS = 10

        val REQUIRED_PERMISSIONS =
            mutableListOf (
                Manifest.permission.CAMERA
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}
