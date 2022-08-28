package com.example.thecookbook.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.thecookbook.R
import com.example.thecookbook.ui.authentication.AuthenticationViewModel
import com.example.thecookbook.ui.authentication.LoginActivity
import com.example.thecookbook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val viewModel by viewModels<AuthenticationViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        observeAuthenticationState()
    }

    private fun observeAuthenticationState() {

        viewModel.authenticationState.observe(this, Observer { authenticationState ->
            if (authenticationState == AuthenticationViewModel.AuthenticationState.UNAUTHENTICATED) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        })
    }

}