package com.example.take_my_money.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.take_my_money.R
import com.example.take_my_money.databinding.ActivityMainBinding
import com.example.take_my_money.ui.models.ModelListCoins
import com.example.take_my_money.ui.view.fragments.CoinsFragment
import com.example.take_my_money.ui.view.fragments.FavoritesFragment

class MainActivity : AppCompatActivity() {

    private val coinsFragment = CoinsFragment()
    private val favoritesFragment = FavoritesFragment()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        replaceFragment(coinsFragment)
        replaceFragment(favoritesFragment)

        binding.btnNavigation.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.ic_coins -> replaceFragment(favoritesFragment)
                R.id.ic_favorites -> replaceFragment(favoritesFragment)
            }
        }
    }

    private fun replaceFragment(fragment: CoinsFragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }

    private fun replaceFragment(fragments: FavoritesFragment) {
        if (fragments != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragments)
            transaction.commit()
        }
    }
}
