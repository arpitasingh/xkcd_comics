package com.xkcd.comics.viewer.app.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xkcd.comics.viewer.app.R
import com.xkcd.comics.viewer.app.database.ComicNote
import com.xkcd.comics.viewer.app.databinding.FragmentFavoriteBinding

class MainAdapter : RecyclerView.Adapter<MainViewHolder>() {

    private var comics = ArrayList<ComicNote>()

    fun submitToComicsList(comics: ArrayList<ComicNote>) {
        this.comics = comics
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentFavoriteBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val comic = comics[position]
        val date = "Date : " + comic.comicDate
        holder.binding.comicDate.text = date
        holder.binding.titleText.text = comic.comicTitle
        holder.binding.comicId.text = comic.comicId.toString()
        Glide.with(holder.itemView.context).load(comic.comicImageUrl)
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(holder.binding.comicImg)
    }

    override fun getItemCount(): Int {
        return comics.size
    }
}

class MainViewHolder(val binding: FragmentFavoriteBinding) :
    RecyclerView.ViewHolder(binding.root) {}

