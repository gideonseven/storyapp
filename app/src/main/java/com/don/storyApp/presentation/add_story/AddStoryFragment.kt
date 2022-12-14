package com.don.storyApp.presentation.add_story

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.don.storyApp.R
import com.don.storyApp.databinding.FragmentAddStoryBinding
import com.don.storyApp.presentation.CameraActivity
import com.don.storyApp.util.rotateBitmap
import com.don.storyApp.util.showSnackBar
import com.don.storyApp.util.uriToFile
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.io.File


/**
 * Created by gideon on 29 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@AndroidEntryPoint
class AddStoryFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private var binding: FragmentAddStoryBinding? = null
    private val viewModel by viewModels<AddStoryViewModel>()
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                checkPermissionLocation()
            }
        }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )

            viewModel.isValidImage.value = myFile.exists()

            viewModel.myFile = myFile
            binding?.iv?.setImageBitmap(result)
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            viewModel.myFile = uriToFile(selectedImg, requireContext())
            viewModel.isValidImage.value = viewModel.myFile?.exists() == true
            binding?.iv?.setImageURI(selectedImg)
        }
    }
    private lateinit var nonNullContext: Context
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        const val CAMERA_X_RESULT = 200
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddStoryBinding.inflate(inflater, container, false)

        binding?.apply {
            lifecycleOwner = this@AddStoryFragment
            vm = viewModel
        }
        return binding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        nonNullContext = context
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            buttonAdd.setOnClickListener {
                viewModel.addStory()
            }
            tvTakeImage.setOnClickListener {
                cameraTask()
            }
            tvOpenGallery.setOnClickListener {
                startGallery()
            }
            tilDescription.editText?.doAfterTextChanged {
                viewModel.isValidText.value = tilDescription.isFormValid()
            }

            viewModel.errorMessage.observe(this@AddStoryFragment.viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    showSnackBar(this.root, it)
                    findNavController().navigateUp()
                }
            }

            viewModel.simpleNetworkModel.observe(this@AddStoryFragment.viewLifecycleOwner) {
                if (it.error == true) {
                    showSnackBar(this.root, it.message.orEmpty())
                }
            }
        }
        checkPermissionLocation()
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    @AfterPermissionGranted(REQUEST_CODE_PERMISSIONS)
    private fun cameraTask() {
        if (EasyPermissions.hasPermissions(requireContext(), Manifest.permission.CAMERA)) {
            // Have permission, do the thing!
            startCameraX()
        } else {
            // Request one permission
            EasyPermissions.requestPermissions(
                this@AddStoryFragment,
                getString(R.string.rationale_camera),
                REQUEST_CODE_PERMISSIONS,
                Manifest.permission.CAMERA
            )
        }
    }

    private fun startCameraX() {
        val intent = Intent(this@AddStoryFragment.context, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherGallery.launch(chooser)
    }

    private fun checkPermissionLocation() {
        if (ContextCompat.checkSelfPermission(
                nonNullContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
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
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(nonNullContext)
        binding?.apply {
            try {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnSuccessListener {
                    if (it != null) {
                        viewModel.lat = it.latitude
                        viewModel.lon = it.longitude
                    }
                }
            } catch (e: SecurityException) {
                showSnackBar(root, "Exception:  ${e.message}")
            }
        }
    }
}