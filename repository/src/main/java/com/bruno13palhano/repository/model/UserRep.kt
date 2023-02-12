package com.bruno13palhano.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bruno13palhano.model.User

@Entity(tableName = "user_table")
internal data class UserRep(

    @PrimaryKey
    @ColumnInfo(name = "user_id")
    val uid: String,

    @ColumnInfo(name = "user_name")
    val username: String,

    @ColumnInfo(name = "user_email")
    val email: String,

    @ColumnInfo(name = "user_url_photo")
    val urlPhoto: String
)

internal fun UserRep.asUser() = User(
    uid = uid,
    username = username,
    email = email,
    urlPhoto = urlPhoto
)

internal fun User.asUserRep() = UserRep(
    uid = uid,
    username = username,
    email = email,
    urlPhoto = urlPhoto
)
