package com.example.take_my_money.ui.view.coinsfavorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.take_my_money.databinding.FavoriteActivityBinding
import java.text.SimpleDateFormat
import java.util.*

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: FavoriteActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FavoriteActivityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val date = Calendar.getInstance().time
        val dateTimeFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        binding.textViewDateNow.text = dateTimeFormat.format(date)
    }
}
