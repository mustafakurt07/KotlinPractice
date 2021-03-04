package com.example.kotlintest.repo

import androidx.lifecycle.LiveData
import com.example.kotlintest.model.ImageResponse
import com.example.kotlintest.room.Art
import com.example.kotlintest.util.Resource

interface ArtRepositoryInterface {
    suspend fun insertArt(art : Art)

    suspend fun deleteArt(art: Art)

    fun getArt() : LiveData<List<Art>>

    suspend fun searchImage(imageString : String) : Resource<ImageResponse>
}