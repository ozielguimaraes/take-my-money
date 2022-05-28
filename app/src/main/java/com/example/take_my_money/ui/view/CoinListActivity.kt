package com.example.take_my_money.ui.view
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.take_my_money.R
import com.example.take_my_money.databinding.ActivityMainBinding
import com.example.take_my_money.ui.view.fragments.CoinsFragment
import com.example.take_my_money.ui.view.fragments.FavoritesFragment

class CoinListActivity : AppCompatActivity() {

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
                R.id.ic_coins -> replaceFragment(coinsFragment)
                R.id.ic_favorites -> replaceFragment(favoritesFragment)
            }
        }
    }

    private fun replaceFragment(fragment: CoinsFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.ic_coins, fragment)
        transaction.commit()
    }

    private fun replaceFragment(fragments: FavoritesFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.ic_favorites, fragments)
        transaction.commit()
    }
}
