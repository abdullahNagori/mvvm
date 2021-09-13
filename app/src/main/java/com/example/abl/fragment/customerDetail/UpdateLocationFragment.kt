package com.example.abl.fragment.customerDetail

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.example.abl.R
import com.example.abl.base.BaseDockFragment
import com.example.abl.databinding.UpdateLocationFragmentBinding
import com.example.abl.fragment.portfolio.Customers
import com.example.abl.model.addLead.DynamicLeadsItem
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*


class UpdateLocationFragment : BaseDockFragment(), OnMapReadyCallback {

    companion object {
        fun newInstance() = CalculatorFragment()
    }

    lateinit var dynamicLeadsItem: DynamicLeadsItem
    private lateinit var binding: UpdateLocationFragmentBinding
    lateinit var customers: Customers
    private var enableListener = true
    private lateinit var mMap: GoogleMap
    private var locationPermissionGranted = true
    private var lat = 0.0
    private var lon = 0.0
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val DEFAULT_ZOOM = 15
    private val defaultLocation = LatLng(24.865817, 67.080942)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        initView()
        setUpMap()

        binding.btnUpdateLocation.setOnClickListener {
         //   updateLocation()
        }
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        return binding.root
    }





    private fun initView() {
        binding = UpdateLocationFragmentBinding.inflate(layoutInflater)
    }

    private fun setUpMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap?) {

        if (p0 != null) {
            mMap = p0
        }

        updateLocationUI()
        // Get the current location of the device and set the position of the map.
        getDeviceLocation()

        // Add a marker in Sydney and move the camera
        val client = LocationServices.getFusedLocationProviderClient(requireContext())

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        client.lastLocation.addOnSuccessListener {
            if (enableListener) {
                try {
                    lat = it.latitude
                    lon = it.longitude
                    val sydney = LatLng(lat, lon)
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat,lon), 15.0f))
                    mMap.addMarker(
                        MarkerOptions()
                        .position(sydney)
                        .title(Geocoder(requireContext(), Locale.getDefault()).getFromLocation(lat,lon,1)[0].getAddressLine(0)))
                } catch (e: Exception) {

                }
            }
            enableListener = false
        }

        mMap.setOnMapClickListener {
            try {
                mMap.clear()
                mMap.addMarker(
                    MarkerOptions().position(it).title(
                        Geocoder(
                            requireContext(),
                            Locale.getDefault()
                        ).getFromLocation(it.latitude, it.longitude, 1)[0].getAddressLine(0)
                    )
                )
                lat = it.latitude
                lon = it.longitude
            } catch (e: Exception) {
                println("Error: ${e.localizedMessage}")
            }
        }
    }


    private fun updateLocationUI() {
        try {
            if (locationPermissionGranted) {
                mMap.isMyLocationEnabled = true
                mMap.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                mMap.isMyLocationEnabled = false
                mMap.uiSettings?.isMyLocationButtonEnabled = false
            }
        } catch (e: SecurityException) {

        }
    }


    private fun getDeviceLocation() {
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            activity?.let {
                locationResult.addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        val lastKnownLocation = task.result
                        val latlng = lastKnownLocation
                            ?.latitude?.let { it1 ->
                                lastKnownLocation.longitude.let { it2 ->
                                    LatLng(it1, it2)
                                }
                            }
                        if (lastKnownLocation != null) {
                            mMap.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    latlng, DEFAULT_ZOOM.toFloat()
                                )
                            )
                        } else {
                            getDeviceLocation()
                        }
                    } else {
                        mMap.moveCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat())
                        )
                        mMap.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }


        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

}