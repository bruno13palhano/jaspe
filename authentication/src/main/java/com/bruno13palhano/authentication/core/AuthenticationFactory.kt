package com.bruno13palhano.authentication.core

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class AuthenticationFactory {

    fun createUserFirebase(): UserAuthentication {
        val auth = Firebase.auth
        val firebaseDB = Firebase.firestore
        val storage = Firebase.storage

        return UserFirebase(auth, firebaseDB, storage)
    }
}