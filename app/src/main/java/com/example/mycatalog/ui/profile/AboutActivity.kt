package com.example.mycatalog.ui.profile

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.mycatalog.R
import com.example.mycatalog.databinding.ActivityAboutBinding

class AboutActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.user_profile)

        val name = "Althafi Hilal Anshar"
        val email = "altp.hlnshr14@gmail.com"

        binding.imgProfile.setImageResource(R.drawable.altap)
        binding.tvFullName.text = name
        binding.tvEmail.text = email

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}