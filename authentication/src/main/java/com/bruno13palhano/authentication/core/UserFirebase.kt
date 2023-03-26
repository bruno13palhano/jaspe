package com.bruno13palhano.authentication.core

import android.graphics.Bitmap
import com.bruno13palhano.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class UserFirebase @Inject constructor(
    private val auth: FirebaseAuth,
    private val firebaseDB: FirebaseFirestore,
    private val storage: FirebaseStorage
) : UserAuthentication {

    override fun createUser(
        user: User,
        successfulCallback: (userUid: String) -> Unit,
        failedCallback: () -> Unit
    ) {
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    auth.currentUser?.uid?.let {
                        onSuccessfulCreateUser(user.username, it)
                        successfulCallback(it)
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

    override fun updateUserUrlPhoto(
        photo: Bitmap,
        onSuccess: (newPhotoUrl: String, userUId: String) -> Unit,
        onFail: () -> Unit
    ) {
        val storageRef = storage.reference

        auth.currentUser?.let {
            val profilePhotoRef = storageRef.child("${it.email}/profile_image.jpg")

            val baos = ByteArrayOutputStream()
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            val uploadTask = profilePhotoRef.putBytes(data)
            uploadTask.addOnFailureListener {
                    onFail()
                }
                .addOnSuccessListener { taskSnapshot ->
                    profilePhotoRef.downloadUrl.addOnSuccessListener { uri ->
                        val profileUpdates = userProfileChangeRequest {
                            photoUri = uri
                        }
                        it.updateProfile(profileUpdates).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                onSuccess(uri.toString(), it.uid)
                            } else {
                                onFail()
                            }
                        }
                    }
                }
        }
    }

    override fun updateUsername(
        username: String,
        onSuccess: (newUsername: String, userUid: String) -> Unit,
        onFail: () -> Unit
    ) {
        val profileUpdates = userProfileChangeRequest {
            displayName = username
        }
        auth.currentUser?.let {
            it.updateProfile(profileUpdates).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    updateUsernameInFirebaseDb(
                        newUsername = username,
                        userUid = it.uid,
                        callback = object : FirebaseDBCallback {
                            override fun onSuccess() {
                                onSuccess(username, it.uid)
                            }

                            override fun onFail() {
                                onFail()
                            }
                        }
                    )
                } else {
                    onFail()
                }
            }
        }
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
        addUserInFirebaseDB(username, userUid)
        updateProfile(username)
    }

    private fun updateProfile(username: String) {
        val profileUpdates = userProfileChangeRequest {
            displayName = username
        }
        val user = auth.currentUser
        user!!.updateProfile(profileUpdates)
    }

    private fun addUserInFirebaseDB(username: String, userUid: String) {
        val user = hashMapOf( "username" to username )
        firebaseDB.collection("users")
            .document(userUid)
            .set(user)
    }

    private fun updateUsernameInFirebaseDb(newUsername: String, userUid: String, callback: FirebaseDBCallback) {
        val usernameRef = firebaseDB.collection("users").document(userUid)
        usernameRef.update("username", newUsername)
            .addOnSuccessListener {
                callback.onSuccess()
            }
            .addOnFailureListener {
                callback.onFail()
            }
    }

    interface FirebaseDBCallback {
        fun onSuccess()
        fun onFail()
    }
}