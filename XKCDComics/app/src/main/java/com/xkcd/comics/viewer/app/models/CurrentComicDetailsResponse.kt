package com.xkcd.comics.viewer.app.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CurrentComicDetails(
    val month: String,
    val num: Int,
    val link: String,
    val year: String,
    val news: String,
    val safe_title: String,
    val transcript: String,
    val alt: String,
    val img: String,
    val title: String,
    val day: String
) : Parcelable

//data class ComicsList(val mList: List<CurrentComicDetails>)