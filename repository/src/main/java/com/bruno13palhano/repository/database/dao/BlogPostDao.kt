package com.bruno13palhano.repository.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.bruno13palhano.repository.model.BlogPostRep
import kotlinx.coroutines.flow.Flow

@Dao
internal interface BlogPostDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(blogPostList: List<BlogPostRep>)

    @Query("SELECT * FROM blog_post_table")
    fun getAllBlogPost(): Flow<List<BlogPostRep>>
}