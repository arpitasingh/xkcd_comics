package com.xkcd.comics.viewer.app.ui.favorites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xkcd.comics.viewer.app.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Favorite Comics"
        if (savedInstanceState == null) {
            val fragment = FavoriteFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(binding.fragmentContainer.id, fragment)
                .commit()
        }
    }
}