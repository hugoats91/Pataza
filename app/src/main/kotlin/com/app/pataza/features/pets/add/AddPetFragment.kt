package com.app.pataza.features.pets.add

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.pataza.R
import com.app.pataza.core.extension.failure
import com.app.pataza.core.extension.observe
import com.app.pataza.core.extension.viewModel
import com.app.pataza.core.functional.dialog.ErrorDialog
import com.app.pataza.core.platform.BaseFragment
import com.app.pataza.core.util.Constants
import com.app.pataza.core.util.Utils
import com.app.pataza.features.pets.PetViewModel
import kotlinx.android.synthetic.main.fragment_add_pet.*
import kotlinx.android.synthetic.main.fragment_profile.*
import okhttp3.MultipartBody
import timber.log.Timber
import java.io.File
import java.io.IOException

class AddPetFragment : BaseFragment(), PhotoFragment.ActionPhoto {
    private var listBitmap = ArrayList<Bitmap>()
    private var currentPhotoPath: String = ""
    private var listMultipartBody = ArrayList<MultipartBody.Part>()
    lateinit var adapterPhotos: PhotoAdapter
    private lateinit var petViewModel: PetViewModel

    override fun layoutId() = R.layout.fragment_add_pet

    private fun initViewModel() {

        petViewModel = viewModel(viewModelFactory) {
            observe(successPet, ::successAddPet)
            observe(successPhotos, ::successSendPhotos)
            failure(failure, ::handleBaseFailure)
        }
    }

    private fun successAddPet(success: Boolean?) {
        success?.let {

        }
    }

    private fun successSendPhotos(success: Boolean?) {
        success?.let {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        initViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btBack = imgBack
        btnAddPhoto.setOnClickListener {
            val fragment = PhotoFragment()
            fragment.listener = this
            fragment.show(fragmentManager, "photo_fragment")
        }
        btnAdd.setOnClickListener { addNewPet() }
        rvPhotos.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapterPhotos = PhotoAdapter()
        rvPhotos.adapter = adapterPhotos
        super.onViewCreated(view, savedInstanceState)
    }

    private fun addNewPet(){
        petViewModel.addPet(etName.text.toString(), "08/07/2015", etColor.text.toString(), etHeight.text.toString().toDouble(), 20.0, etHistory.text.toString())
    }

    override fun onCamera() {
        activity?.let { dispatchTakePictureIntent(it) }
    }

    override fun onGallery() {
        showPickPhotos()
    }

    private fun showPickPhotos() {
        if (checkSelfPermission(context!!,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (shouldShowRequestPermissionRationale(
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Timber.i("rechazo los permisos")
            }
            requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    40)
        } else {
            pickFromGallery()
        }
    }

    private fun pickFromGallery() {
        //Create an Intent with action as ACTION_PICK
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.type = "image/*"
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        // Launching the Intent
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), Constants.GALLERY_REQUEST_CODE)
    }

    private fun dispatchTakePictureIntent(act: Activity) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(act.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    Utils.createImageFile(act).apply {
                        currentPhotoPath = this.absolutePath
                    }
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                            act,
                            "com.app.pataza.fileprovider",
                            it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, Constants.CAMERA_REQUEST_CODE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            when (requestCode) {
                Constants.GALLERY_REQUEST_CODE -> {
                    data?.data?.let { uri ->
                        val file = Utils.makeFile(context, uri)
                        file?.let { fileParam ->
                            listBitmap.add(BitmapFactory.decodeFile(fileParam.path))
                            val filePart = Utils.importPhoto(context, uri, fileParam)
                            filePart?.let {
                                listMultipartBody.add(it)
                            }
                        }
                    } ?: run {
                        data?.clipData?.let { clip ->
                            for (i in 0 until clip.itemCount) {
                                val file = Utils.makeFile(context, clip.getItemAt(i).uri)
                                file?.let { fileParam ->
                                    listBitmap.add(BitmapFactory.decodeFile(fileParam.path))
                                    val filePart = Utils.importPhoto(context, clip.getItemAt(i).uri, fileParam)
                                    filePart?.let {
                                        listMultipartBody.add(it)
                                    }
                                }

                            }
                        }
                    }
                    if (listMultipartBody.isNotEmpty()) {
                        adapterPhotos.collection = listBitmap
                        //profileViewModel.uploadPhotos(list)
                    }

                }

                Constants.CAMERA_REQUEST_CODE -> {
                    listBitmap.add(BitmapFactory.decodeFile(currentPhotoPath))
                    adapterPhotos.collection = listBitmap
                }
            }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            40 -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    ErrorDialog.create(context, {

                    }, getString(R.string.permissions_store), getString(R.string.permissions_button))
                } else {
                    pickFromGallery()
                }
            }
        }
    }
}