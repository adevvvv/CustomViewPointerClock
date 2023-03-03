package com.example.customviewpointerclock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.customviewpointerclock.databinding.ActivityMainBinding
import com.example.customviewpointerclocklib.CustomViewClock

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var clockView: CustomViewClock
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clockView = findViewById(R.id.clockView)

    }
}