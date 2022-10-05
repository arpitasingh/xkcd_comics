package com.xkcd.comics.viewer.app.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.xkcd.comics.viewer.app.R
import com.xkcd.comics.viewer.app.api.ApiServiceInterface
import com.xkcd.comics.viewer.app.api.ComicsRepository
import com.xkcd.comics.viewer.app.databinding.FragmentMainBinding
import com.xkcd.comics.viewer.app.models.CurrentComicDetails
import com.xkcd.comics.viewer.app.ui.details.DetailsActivity


/**
 * A fragment representing a list of Comics.
 */
class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private val retrofitService = ApiServiceInterface.create()
    private lateinit var binding: FragmentMainBinding
    private lateinit var comicDetails: CurrentComicDetails

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(
                this,
                MyViewModelFactory(ComicsRepository(retrofitService))
            )[MainViewModel::class.java]

        initViewModel(arguments?.getLong(KEY_COMICS_ID) ?: throw IllegalStateException())
    }

    private fun initViewModel(id: Long) {
        viewModel.getAllComics(id)

        viewModel.comicsList.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                comicDetails = it
                setDataToView()
            } else {
                Toast.makeText(activity, "Error in fetching data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setDataToView() {
        val date = comicDetails.day + "/" + comicDetails.month + "/" + comicDetails.year
        binding.comicDate.text = date
        binding.titleText.text = comicDetails.title
        binding.comicId.text = comicDetails
            .num.toString()
        Glide.with(this)
            .load(comicDetails.img)
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(binding.comicImg)
        binding.comicImg.setOnClickListener {
            val intent = Intent(this.activity, DetailsActivity::class.java)
            intent.putExtra("COMIC_DETAILS", comicDetails)
            startActivity(intent)
        }
        binding.shareButton.setOnClickListener { shareComic(comicDetails.img) }
    }

    private fun shareComic(url: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, url)
        startActivity(Intent.createChooser(shareIntent, "Share url using"))
    }


    override fun onDestroy() {
        //don't send events  once the activity is destroyed
        viewModel.disposable.dispose()
        super.onDestroy()
    }

    companion object {
        const val KEY_COMICS_ID = "key_comics_id"
        fun create(itemText: Long) =
            MainFragment().apply {
                arguments = Bundle(1).apply {
                    putLong(KEY_COMICS_ID, itemText)
                }
            }
    }
}