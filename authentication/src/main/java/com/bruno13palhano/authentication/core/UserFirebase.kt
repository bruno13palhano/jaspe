package com.bruno13palhano.authentication.core

import android.net.Uri
import com.bruno13palhano.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserFirebase : UserAuthentication {
    private var auth: FirebaseAuth = Firebase.auth
    private var firebaseDB = Firebase.firestore

    override fun createUser(
        user: User,
        successfulCallback: () -> Unit,
        failedCallback: () -> Unit
    ) {
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    auth.currentUser?.uid?.let {
                        onSuccessfulCreateUser(user.username, it)
                        successfulCallback()
                    }
                } else {
                    failedCallback()
                }
            }
    }

    override fun login(
        email: String,
        password: String,
        successfulCallback: () -> Unit,
        failedCallback: () -> Unit
    ){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    successfulCallback()
                } else {
                    failedCallback()
                }
            }
    }

    override fun logout() {
        auth.signOut()
    }

    override fun updateUser(user: User) {
        val profileUpdates = userProfileChangeRequest {
            displayName = user.username
            photoUri = Uri.parse(user.urlPhoto)
        }
        auth.currentUser!!.updateProfile(profileUpdates)
    }

    override fun isUserAuthenticated(): Boolean {
        return auth.currentUser != null
    }

    override fun getCurrentUser(): User {
        return User(
            uid = auth.currentUser?.uid ?: "",
            username = auth.currentUser?.displayName ?: "",
            email = auth.currentUser?.email ?: "",
            password = "",
            urlPhoto = auth.currentUser?.photoUrl.toString()
        )
    }

    private fun onSuccessfulCreateUser(username: String, userUid: String) {
        addUser(username, userUid)
        updateProfile(username)
    }

    private fun updateProfile(username: String) {
        val profileUpdates = userProfileChangeRequest {
            displayName = username
        }
        val user = auth.currentUser
        user!!.updateProfile(profileUpdates)
    }

    private fun addUser(username: String, userUid: String) {
        val user = hashMapOf( "username" to username )
        firebaseDB.collection("users")
            .document(userUid)
            .set(user)
    }
}