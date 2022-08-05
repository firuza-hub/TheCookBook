package com.example.thecookbook.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.google.firebase.auth.FirebaseAuth

class AuthenticationViewModel: ViewModel() {
    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }
}