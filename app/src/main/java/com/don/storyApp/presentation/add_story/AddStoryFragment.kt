package com.don.storyApp.presentation.add_story

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            buttonAdd.setOnClickListener {
                viewModel.addStory(
                    errorMessage = {
                        showSnackBar(this.root, it)
                    },
                    onSuccess = {
                        showSnackBar(this.root, it.message.orEmpty())
                        findNavController().popBackStack()
                    })
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
        }
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
}