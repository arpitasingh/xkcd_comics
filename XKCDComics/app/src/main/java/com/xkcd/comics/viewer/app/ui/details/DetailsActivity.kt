package com.xkcd.comics.viewer.app.ui.details

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.xkcd.comics.viewer.app.R
import com.xkcd.comics.viewer.app.database.ComicNote
import com.xkcd.comics.viewer.app.databinding.ActivityDetailsBinding
import com.xkcd.comics.viewer.app.models.CurrentComicDetails
import com.xkcd.comics.viewer.app.ui.favorites.FavoriteComicsViewModel


class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private var comicDetails: CurrentComicDetails? = null
    private lateinit var viewModel: FavoriteComicsViewModel
    private var isFavoriteComic = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        comicDetails = intent.getParcelableExtra("COMIC_DETAILS") as CurrentComicDetails?
        title = "Comic Details"
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[FavoriteComicsViewModel::class.java]
        setDetailsView(comicDetails)
    }

    private fun setDetailsView(comicDetail: CurrentComicDetails?) {
        if (null != comicDetail) {
            val date = comicDetail.day + "/" + comicDetail.month + "/" + comicDetail.year
            val dateText = "Date : $date"
            binding.comicDate.text = dateText
            binding.titleText.text = comicDetail.title
            binding.comicId.text = comicDetail.num.toString()
            Glide.with(this)
                .load(comicDetail.img)
                .placeholder(R.drawable.ic_baseline_image_24)
                .into(binding.comicImg)
            binding.comicDetails.text = comicDetail.alt
            isFavorite(comicDetail)

            binding.favoriteButton.setOnClickListener {
                if (!isFavoriteComic) {
                    checkAndInsertComic(comicDetail, date)
                    binding.favoriteButton.setImageResource(R.drawable.ic_baseline_star_24_blue)
                    Toast.makeText(
                        this@DetailsActivity,
                        "Comics added to favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@DetailsActivity,
                        "Already added to favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun checkAndInsertComic(comicDetails: CurrentComicDetails, date: String) {
        var comicNote = ComicNote(
            comicDetails.title,
            comicDetails.alt,
            comicDetails.num,
            date,
            comicDetails.img
        )

        viewModel.insertComic(comicNote)

    }

    private fun isFavorite(comicDetails: CurrentComicDetails) {

        viewModel.isFavorite(comicDetails.num).observe(this, Observer {
            if (it > 0) {
                binding.favoriteButton.setImageResource(R.drawable.ic_baseline_star_24_blue)
                isFavoriteComic = true
            }
        })

    }
}