package com.example.thecookbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.thecookbook.authentication.AuthenticationViewModel
import com.example.thecookbook.authentication.LoginActivity

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<AuthenticationViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeAuthenticationState()
    }

    private fun observeAuthenticationState() {

        viewModel.authenticationState.observe(this, Observer { authenticationState ->
            when (authenticationState) {
                AuthenticationViewModel.AuthenticationState.UNAUTHENTICATED -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)

                }
            }
        })
    }
}