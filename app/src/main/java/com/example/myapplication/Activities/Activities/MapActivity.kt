package com.example.myapplication.Activities.Activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Locale

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var selectedLocation: LatLng? = null
    private var selectedAddress: String = ""

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        findViewById<Button>(R.id.button2).text = "Select Current Location"
        findViewById<Button>(R.id.button2).setOnClickListener {
            val intent = Intent()
            intent.putExtra("address", selectedAddress)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true

        checkLocationPermission()

        mMap.setOnCameraIdleListener {
            val target = mMap.cameraPosition.target
            selectedLocation = target

            val geocoder = Geocoder(this, Locale.getDefault())
            val addresses = geocoder.getFromLocation(target.latitude, target.longitude, 1)
            selectedAddress = if (!addresses.isNullOrEmpty()) {
                addresses[0].getAddressLine(0)
            } else {
                "Address not found"
            }
        }

        mMap.setOnMapClickListener { latLng ->
            selectedLocation = latLng
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(latLng).title("Address selected"))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))

            val geocoder = Geocoder(this, Locale.getDefault())
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            selectedAddress = if (!addresses.isNullOrEmpty()) {
                addresses[0].getAddressLine(0)
            } else {
                "Address not found"
            }
        }
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getUserLocation()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun getUserLocation() {
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    moveCameraAndSetMarker(currentLatLng)
                } else {
                    Toast.makeText(this, "Unable to obtain location", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun moveCameraAndSetMarker(location: LatLng) {
        mMap.clear()
        mMap.addMarker(MarkerOptions().position(location).title("Current location"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16f))
        selectedLocation = location
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getUserLocation()
            } else {
                Toast.makeText(this, "Location permit denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
