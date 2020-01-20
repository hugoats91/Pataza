package com.app.pataza.features.profile.edit

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.app.pataza.R
import com.app.pataza.core.extension.failure
import com.app.pataza.core.extension.observe
import com.app.pataza.core.extension.viewModel
import com.app.pataza.core.functional.dialog.PickerDialogFragment
import com.app.pataza.core.functional.dialog.DatePickerFragment
import com.app.pataza.core.functional.dialog.ErrorDialog
import com.app.pataza.core.functional.view.EditTextView
import com.app.pataza.core.platform.BaseFragment
import com.app.pataza.core.util.Constants.LOCATION_UPDATE_MIN_DISTANCE
import com.app.pataza.core.util.Constants.LOCATION_UPDATE_MIN_TIME
import com.app.pataza.core.util.Utils
import com.app.pataza.data.models.Country
import com.app.pataza.features.profile.UserViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class EditProfileFragment : BaseFragment(), DatePickerFragment.Callback, PickerDialogFragment.Callback, OnMapReadyCallback {
    lateinit var userViewModel: UserViewModel
    var countries: List<Country>? = null
    var codeCountry: String = ""
    private var mLocationManager: LocationManager? = null
    private var map: GoogleMap? = null
    private var point: LatLng? = null

    override fun layoutId() = R.layout.fragment_edit_profile

    private fun initViewModel() {
        userViewModel = viewModel(viewModelFactory) {
            observe(userView, ::showProfile)
            observe(edit, ::successEditProfile)
            failure(failure, ::handleBaseFailure)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        initViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btClose.setOnClickListener { activity?.finish() }
        btEdit.setOnClickListener { editProfile() }
        userViewModel.getProfile()
        etBirthdate.listener = (object: EditTextView.Callback{
            override fun onClick() {
                pickDate()
            }
        })
        etAddress.listener = (object : EditTextView.Callback{
            override fun onClick() {
                permissionLocation()
            }
        })
        btConfirmAddress.setOnClickListener { pickAddress() }

        val mapFragment = childFragmentManager.findFragmentById(R.id.frgMap) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap?) {
        map = p0
        map?.setOnMapClickListener {
            pickMarker(it.latitude, it.longitude)
        }

    }

    private fun pickAddress(){
        groupMap.visibility = View.GONE
        groupForm.visibility = View.VISIBLE
        etAddress.setText(getString(R.string.edit_profile_address_pick, Utils.formatGPS(point?.latitude), Utils.formatGPS(point?.longitude)))
    }

    private fun showProfile(userView: UserEditView?) {
        userView?.let { userParam ->
            point = LatLng(userParam.user.latitude, userParam.user.longitude)
            codeCountry = userParam.user.country?:""
            etName.setText(userParam.user.name)
            etPhone.setText(userParam.user.phone)
            etBirthdate.setText(userParam.user.birthdate)
            etAddress.setText(getString(R.string.edit_profile_address_pick, Utils.formatGPS(point?.latitude), Utils.formatGPS(point?.longitude)))

            etCountry.setText(userParam.country ?: "")
            etCountry.fragment = this
            etCountry.list = ArrayList(userParam.countries.map { it.label })
        }
    }

    private fun successEditProfile(success: Boolean?) {
        success?.let {

        }
    }

    private fun editProfile() {
        if (validate()) {
            point?.let {
                userViewModel.editProfile(etName.getText(), etPhone.getText(), etBirthdate.getText(), etAddress.getText(), codeCountry, it.latitude, it.longitude)
            }
        }
    }

    private fun validate(): Boolean {
        return true
    }

    private fun pickDate() {
        val fragment = DatePickerFragment.newInstance(Calendar.getInstance().time)
        fragment.setListener(this)
        fragment.show(fragmentManager, "datePicker")
    }

    override fun onDate(result: String) {
        etBirthdate.setText(result)
    }

    override fun onItem(position: Int) {
        countries?.let {
            etCountry.setText(it[position].label)
            codeCountry = it[position].value
        }
    }

    private val mLocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            if (location != null) {
            }
        }

        override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {

        }

        override fun onProviderEnabled(s: String) {

        }

        override fun onProviderDisabled(s: String) {

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            50 -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    ErrorDialog.create(context, {

                    }, getString(R.string.permissions_store), getString(R.string.permissions_button))
                } else {
                    getCurrentLocation()
                }
            }
        }
    }

    private fun permissionLocation() {
        if (ContextCompat.checkSelfPermission(context!!,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context!!,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (shouldShowRequestPermissionRationale(
                            Manifest.permission.ACCESS_COARSE_LOCATION)) {
                Timber.i("rechazo los permisos")
            }
            requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                    50)
        } else {
            getCurrentLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        groupForm.visibility = View.GONE
        groupMap.visibility = View.VISIBLE
        mLocationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGPSEnabled = mLocationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)
                ?: false
        val isNetworkEnabled = mLocationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                ?: false

        var location: Location? = null
        if (!(isGPSEnabled || isNetworkEnabled))
            Timber.i("no se pudo")
        else {
            if (isNetworkEnabled) {
                mLocationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener)
                location = mLocationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            }

            if (isGPSEnabled) {
                mLocationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener)
                location = mLocationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            }
        }
        location?.let {
            pickMarker(it.latitude, it.longitude)
            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 15f))
        }
    }

    private fun pickMarker(latitude: Double, longitude: Double){
        point = LatLng(latitude, longitude)
        map?.clear()
        map?.addMarker(MarkerOptions().position(LatLng(latitude, longitude)))
    }
}