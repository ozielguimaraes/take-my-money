package com.example.take_my_money.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.take_my_money.R
import com.example.take_my_money.databinding.FavoriteActivityBinding
import java.text.SimpleDateFormat
import java.util.*

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: FavoriteActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FavoriteActivityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupNavigationBottom()

        val date = Calendar.getInstance().time
        val dateTimeFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        binding.textViewDateNow.text = dateTimeFormat.format(date)
    }

    private fun setupNavigationBottom() {
        binding.btnNavigationFav.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.ic_coins -> {
                    startActivity(Intent(this, CoinListActivity::class.java))
                    finish()
                }
                R.id.ic_favorites -> {
                    startActivity((Intent(this, FavoriteActivity::class.java)))
                }
            }
        }
    }
}
