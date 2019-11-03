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
import com.app.pataza.core.functional.view.SpinnerView
import com.app.pataza.core.platform.BaseFragment
import com.app.pataza.core.util.Constants
import com.app.pataza.core.util.Utils
import com.app.pataza.data.models.Color
import com.app.pataza.data.models.Gender
import com.app.pataza.data.models.Resource
import com.app.pataza.data.models.Vaccine
import kotlinx.android.synthetic.main.fragment_add_pet.*
import okhttp3.MultipartBody
import timber.log.Timber
import java.io.File
import java.io.IOException

class AddPetFragment : BaseFragment(), PhotoFragment.ActionPhoto {
    private var listBitmap = ArrayList<Bitmap>()
    private var currentPhotoPath: String = ""
    private var listMultipartBody = ArrayList<MultipartBody.Part>()
    lateinit var adapterPhotos: PhotoAdapter
    private lateinit var addPetViewModel: AddPetViewModel
    private var resources: Resource? = null

    private var race: Int = 0
    private var gender: Int = 0
    private var size: Int = 0
    private var allColors: ArrayList<Color>? = null
    private var colors: ArrayList<String>? = null
    private var genders: ArrayList<Gender>? = null
    private var vaccines: ArrayList<Int>? = null
    private var qualities: ArrayList<Int>? = null
    private var diseases: ArrayList<Int>? = null
    private var additionalRequirements: ArrayList<Int>? = null

    override fun layoutId() = R.layout.fragment_add_pet

    private fun initViewModel() {

        addPetViewModel = viewModel(viewModelFactory){
            observe(resources, ::showResources)
            observe(successId, ::successAddPet)
            observe(successPhotos, ::successSendPhotos)
            failure(failure, ::handleBaseFailure)
        }
    }

    private fun successAddPet(success: String?) {
        success?.let {
            addPetViewModel.sendPhotos(success, listMultipartBody)
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
        btAdd.setOnClickListener { addNewPet() }
        rvPhotos.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapterPhotos = PhotoAdapter()
        rvPhotos.adapter = adapterPhotos

        etColor.colorListener = (object : SpinnerView.ColorCallback{
            override fun onColorSelected(positions: ArrayList<Int>) {
                allColors?.let {
                    colors = ArrayList(it.filterIndexed { _, cl ->  positions.any { position -> position == cl.value }}.map { label -> label.label })
                }
            }
        })
        etRace.singleListener = (object : SpinnerView.SingleCallback{
            override fun onSingleSelected(position: Int) {
                resources?.races?.get(position)?.let { race = it.value }
            }
        })
        etGender.singleListener = (object : SpinnerView.SingleCallback{
            override fun onSingleSelected(position: Int) {
                genders?.get(position)?.let { gender = it.id }
            }
        })
        etSize.singleListener = (object : SpinnerView.SingleCallback{
            override fun onSingleSelected(position: Int) {
                resources?.sizes?.get(position)?.let { size = it.value }
            }
        })
        etVaccines.multipleListener = (object : SpinnerView.MultipleCallback{
            override fun onMultipleSelected(positions: ArrayList<Int>) {
                resources?.vaccines?.let {
                    vaccines = ArrayList(it.filterIndexed { index, _ ->  positions.any { position -> position == index }}.map { label -> label.value })
                }
            }
        })
        etQualities.multipleListener = (object : SpinnerView.MultipleCallback{
            override fun onMultipleSelected(positions: ArrayList<Int>) {
                resources?.qualities?.let {
                    qualities = ArrayList(it.filterIndexed { index, _ ->  positions.any { position -> position == index }}.map { label -> label.value })
                }
            }
        })
        etDiseases.multipleListener = (object : SpinnerView.MultipleCallback{
            override fun onMultipleSelected(positions: ArrayList<Int>) {
                resources?.diseases?.let {
                    diseases = ArrayList(it.filterIndexed { index, _ ->  positions.any { position -> position == index }}.map { label -> label.value })
                }
            }
        })

        addPetViewModel.allResources()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun addNewPet(){
        colors?.let {
            addPetViewModel.addPet(etName.getText(),
                    etMonths.getInt(),
                    etYears.getInt(),
                    race,
                    it.toList(),
                    gender,
                    size,
                    etWeight.getDouble(),
                    etHistory.getText(),
                    vaccines?.toList(),
                    qualities?.toList(),
                    diseases?.toList(),
                    additionalRequirements?.toList()
                    )
        }

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
        // Result code is RESULT_OK only if the userView selects an Image
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

    private fun showResources(resources: Resource?){
        this.resources = resources
        resources?.let {
            etGender.fragment = this
            genders = Utils.listGender()
            genders?.let { gnd -> etGender.list = ArrayList(gnd.map { gender ->  gender.label }) }

            etSize.fragment = this
            etSize.list = ArrayList(it.sizes.map { size ->  size.label })

            etRace.fragment = this
            etRace.list = ArrayList(it.races.map { race -> race.label })

            allColors = Utils.listColors()
            allColors?.let { colors -> etColor.setupData(this, ArrayList(colors.map { color ->  color.value })) }

            etVaccines.setupData(this, ArrayList(it.vaccines.map { vaccine -> vaccine.label }))
            etQualities.setupData(this, ArrayList(it.qualities.map { quality ->  quality.label }))
            etDiseases.setupData(this, ArrayList(it.diseases.map { disease -> disease.label }))

        }
    }
}