package com.example.thecookbook.Timer

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.thecookbook.R
import com.example.thecookbook.databinding.FragmentTimerBinding
import sendNotification

class TimerFragment : Fragment() {

    private lateinit var binding: FragmentTimerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_timer, container, false)

        // TODO: Step 1.7 call createChannel
        createChannel(
            getString(R.string.recipe_notification_channel_id),
            getString(R.string.recipe_notification_channel_name)
        )

        binding.btnStartTimer.setOnClickListener {
            val notificationManager = ContextCompat.getSystemService(
                requireActivity(),
                NotificationManager::class.java
            ) as NotificationManager



            notificationManager.sendNotification("meow", requireActivity())
        }
        return binding.root
    }




    private fun createChannel(channelId: String, channelName: String) {
        // TODO: Step 1.6 START create a channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                // TODO: Step 2.4 change importance
                NotificationManager.IMPORTANCE_LOW
            )
            // TODO: Step 2.6 disable badges for this channel

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Time for breakfast"
            val notificationManager = requireActivity().getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)


            // TODO: Step 1.6 END create channel
        }


    }
}