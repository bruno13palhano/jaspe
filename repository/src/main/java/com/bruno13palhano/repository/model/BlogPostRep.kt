package com.bruno13palhano.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bruno13palhano.model.BlogPost

@Entity(tableName = "blog_post_table")
internal data class BlogPostRep(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "post_id")
    val postId: Long,

    @ColumnInfo(name = "post_title")
    val postTitle: String,

    @ColumnInfo(name = "post_description")
    val postDescription: String,

    @ColumnInfo(name = "post_url_image")
    val postUrlImage: String,

    @ColumnInfo(name = "post_url_link")
    val postUrlLink: String
)

internal fun BlogPostRep.asBlogPost() = BlogPost(
    postId = postId,
    postTitle = postTitle,
    postDescription = postDescription,
    postUrlImage = postUrlImage,
    postUrlLink = postUrlLink
)

internal fun BlogPost.asBlogPostRep() = BlogPostRep(
    postId = postId,
    postTitle = postTitle,
    postDescription = postDescription,
    postUrlImage = postUrlImage,
    postUrlLink = postUrlLink
)