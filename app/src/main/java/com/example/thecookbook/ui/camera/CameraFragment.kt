package com.example.thecookbook.ui.camera

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.thecookbook.BuildConfig
import com.example.thecookbook.R
import com.example.thecookbook.databinding.FragmentCameraBinding
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class CameraFragment : Fragment() {
    private lateinit var _viewModel: CameraViewModel
    private lateinit var binding: FragmentCameraBinding
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var cameraActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var recipeId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cameraActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                setPic(true)
            }
        }
        requestPermissionLauncher = registerForActivityResult(
            RequestPermission() // Permission Type
        ) { isGranted ->
            if (isGranted) {
                Toast.makeText(
                    requireContext(),
                    "Permission granted now you can read the storage",
                    Toast.LENGTH_LONG
                ).show()
                openCamera()
            } else {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    "Please give access to camera usage to share your CHEF skills",
                    Snackbar.LENGTH_LONG
                )
                    .setAction(R.string.settings) {
                        startActivity(Intent().apply {
                            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        })
                    }.show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_camera, container, false)
        val args = CameraFragmentArgs.fromBundle(requireArguments())
        recipeId = args.recipeId
        _viewModel = ViewModelProvider(this)[CameraViewModel::class.java]
        createChannel(
            getString(R.string.recipe_notification_channel_id),
            getString(R.string.recipe_notification_channel_name)
        )



        binding.btnStartTimer.setOnClickListener {
//            val notificationManager = ContextCompat.getSystemService(
//                requireActivity(),
//                NotificationManager::class.java
//            ) as NotificationManager
//
//
//            notificationManager.sendNotification("meow", requireActivity())

            requestCameraPermissionAndOpenCamera()
        }

        return binding.root
    }

    private fun openCamera() {
        dispatchTakePictureIntent()
    }

    private fun setPic(save: Boolean) {
        // Get the dimensions of the View
        val targetW: Int = binding.ivMeal.width
        val targetH: Int = binding.ivMeal.height

        val bmOptions = BitmapFactory.Options().apply {
            // Get the dimensions of the bitmap
            inJustDecodeBounds = true

            BitmapFactory.decodeFile(currentPhotoPath, this)

            val photoW: Int = outWidth
            val photoH: Int = outHeight

            // Determine how much to scale down the image
            val scaleFactor: Int = Math.max(1, Math.min(photoW / targetW, photoH / targetH))

            // Decode the image file into a Bitmap sized to fill the View
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
            inPurgeable = true
        }
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions)?.also { bitmap ->
            binding.ivMeal.setImageBitmap(bitmap)

            _viewModel.savePic(bitmap, recipeId)
        }
    }

    lateinit var currentPhotoPath: String

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File =
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun dispatchTakePictureIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    Log.i("MYCAMERA", ex.message.toString())

                    null
                }
                // Continue only if the File was successfully created
                Log.i("MYCAMERA", "file created successfully")
                Log.i("MYCAMERA", ((photoFile?.name) ?: "nofile"))
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.example.thecookbook.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    Log.i("MYCAMERA", "PUT EXTRA PHOTO URI: $photoURI")

                }
            }
        }
        cameraActivityResultLauncher.launch(intent)

    }


    private fun requestCameraPermissionAndOpenCamera() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                openCamera()// You can use the API that requires the permission.
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {}
            else -> {
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }
        }


    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW
            )

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Time for breakfast"
            val notificationManager = requireActivity().getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }


    }

}