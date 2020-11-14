package me.robbin.nilmusic.data

import androidx.compose.runtime.Immutable

@Immutable
data class Song(
    val name: String,
    val author: String,
    val album: String,
    val picture: String,
    val length: Int = 0
)