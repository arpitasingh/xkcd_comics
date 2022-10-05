package com.xkcd.comics.viewer.app

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.xkcd.comics.viewer.app.api.ApiServiceInterface
import com.xkcd.comics.viewer.app.api.ComicsRepository
import com.xkcd.comics.viewer.app.databinding.ActivityMainBinding
import com.xkcd.comics.viewer.app.ui.favorites.FavoriteActivity
import com.xkcd.comics.viewer.app.ui.main.ComicsViewModel
import com.xkcd.comics.viewer.app.ui.main.ComicsViewModelFactory
import com.xkcd.comics.viewer.app.ui.main.MainFragment
import com.xkcd.comics.viewer.app.ui.main.ViewPagerAdapter
import com.xkcd.comics.viewer.app.ui.search.SearchActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myViewPager2: ViewPager2
    private lateinit var myAdapter: ViewPagerAdapter
    private lateinit var viewModelMain: ComicsViewModel
    private val retrofitService = ApiServiceInterface.create()
    private var offsetRandom: Long = 1
    private var visibleOffset: Long = -1
    private var offsetRange: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModelMain =
            ViewModelProvider(
                this,
                ComicsViewModelFactory(ComicsRepository(retrofitService))
            )[ComicsViewModel::class.java]
        myViewPager2 = binding.viewPager2
        initViewModel()

        binding.prevButton.setOnClickListener {
            if (visibleOffset > 0) {
                visibleOffset -= 1
                if (myViewPager2.currentItem > 0) {
                    myAdapter.add(myViewPager2.currentItem - 1, MainFragment.create(visibleOffset))
                    myViewPager2.setCurrentItem(myViewPager2.currentItem - 1, true)
                } else {
                    myAdapter.add(0, MainFragment.create(visibleOffset))
                    myViewPager2.setCurrentItem(0, true)
                }
            }
        }
        binding.nextButton.setOnClickListener {
            if (visibleOffset <= offsetRange) {
                visibleOffset += 1
                myAdapter.add(myViewPager2.currentItem + 1, MainFragment.create(visibleOffset))
                myViewPager2.setCurrentItem(myViewPager2.currentItem + 1, true)
            }
        }
    }

    private fun initViewModel() {
        viewModelMain.getAllComics()

        viewModelMain.comicsList.observe(this, Observer {
            if (it != null) {
                offsetRange = it.num.toLong()
                setOffsets()
                setAdapter()
            } else {
                Toast.makeText(this, "Error in fetching data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setAdapter() {
        val fragments =
            mutableListOf(
                MainFragment.create(offsetRandom)
            )

        myViewPager2.isUserInputEnabled = false
        myAdapter = ViewPagerAdapter(supportFragmentManager, fragments, lifecycle)
        myViewPager2.adapter = myAdapter
    }

    private fun setOffsets() {
        val random = (1..offsetRange).random()
        offsetRandom = random
        visibleOffset = offsetRandom
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> startActivity(Intent(this, SearchActivity::class.java))
            R.id.favorites -> startActivity(Intent(this, FavoriteActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}