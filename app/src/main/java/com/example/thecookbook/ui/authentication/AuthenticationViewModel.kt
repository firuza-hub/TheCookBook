package com.example.thecookbook.ui.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.google.firebase.auth.FirebaseAuth

class AuthenticationViewModel: ViewModel() {
    val authenticationState = FirebaseUserLiveData().map { _ ->
        AuthenticationState.AUTHENTICATED
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }
}