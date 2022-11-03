package com.don.storyApp.presentation.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.don.storyApp.R
import com.don.storyApp.databinding.FragmentMapBinding
import com.don.storyApp.util.showSnackBar
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.*


/**
 * Created by gideon on 03 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {

    private var binding: FragmentMapBinding? = null
    private lateinit var nonNullContext: Context
    private lateinit var mMap: GoogleMap

    // The entry point to the Fused Location Provider.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                checkPermissionLocation()
            }
        }
    private val boundsBuilder = LatLngBounds.Builder()
    private val viewModel by viewModels<MapViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        nonNullContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)

        binding?.apply {
            lifecycleOwner = this@MapFragment
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(nonNullContext)

        val mapFragment = childFragmentManager.findFragmentById(R.id.fcv_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_change_language -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        mMap.setOnMapLongClickListener { latLng ->
            mMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title("New Marker")
                    .snippet("Lat: ${latLng.latitude} Long: ${latLng.longitude}")
                    .icon(
                        vectorToBitmap(
                            R.drawable.ic_location,
                            Color.parseColor("#3DDC84")
                        )
                    )
            )
        }

        mMap.setOnPoiClickListener { pointOfInterest ->
            val poiMarker = mMap.addMarker(
                MarkerOptions()
                    .position(pointOfInterest.latLng)
                    .title(pointOfInterest.name)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
            )
            poiMarker?.showInfoWindow()
        }

        checkPermissionLocation()
        setMapStyle()
        getStoriesLocation()
    }

    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                        nonNullContext,
                        R.raw.map_style
                    )
                )
            if (!success) {
                binding?.apply {
                    showSnackBar(this.root, "Style parsing failed.")
                }
            }
        } catch (exception: Resources.NotFoundException) {
            binding?.apply {
                showSnackBar(this.root, "Can't find style. Error: $exception")
            }
        }
    }

    private fun vectorToBitmap(@DrawableRes id: Int, @ColorInt color: Int): BitmapDescriptor {
        val vectorDrawable = ResourcesCompat.getDrawable(resources, id, null)
            ?: return BitmapDescriptorFactory.defaultMarker()
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        DrawableCompat.setTint(vectorDrawable, color)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun checkPermissionLocation() {
        if (ContextCompat.checkSelfPermission(
                nonNullContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
            getMyCurrentLocation()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getMyCurrentLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mMap.isMyLocationEnabled) {
                val locationResult = fusedLocationProviderClient.lastLocation
                this.activity?.let {
                    locationResult.addOnCompleteListener(it) { task ->
                        if (task.isSuccessful) {
                            // Set the map's camera position to the current location of the device.
                            val lastKnownLocation = task.result
                            if (lastKnownLocation != null) {
                                mMap.moveCamera(
                                    CameraUpdateFactory.newLatLngZoom(
                                        LatLng(
                                            lastKnownLocation.latitude,
                                            lastKnownLocation.longitude
                                        ), 15f
                                    )
                                )
                            }
                        }
                    }
                }
            }
        } catch (e: SecurityException) {
            binding?.apply {
                showSnackBar(this.root, "Exception:  ${e.message}")
            }
        }
    }

    private fun getAddressName(lat: Double, lon: Double): String? {
        var addressName: String? = null

        /**
         *  @param lat
         *   validate should be in between these range
         *   -90 to +90
         */
        if (lat >= -90 && lat <= 90.0) {
            val geocoder = Geocoder(nonNullContext, Locale.getDefault())
            try {
                val list = geocoder.getFromLocation(lat, lon, 1)
                if (list != null && list.size != 0) {
                    addressName = list[0].getAddressLine(0)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return addressName
    }

    private fun getStoriesLocation() {
        viewModel.getListLocation(onSuccess = {
            it.forEach { story ->
                val latLng = LatLng(story.lat, story.lon)
                val addressName = getAddressName(story.lat, story.lon)
                mMap.addMarker(
                    MarkerOptions().position(latLng).title(story.name).snippet(addressName)
                )
                boundsBuilder.include(latLng)
            }
        })
    }
}