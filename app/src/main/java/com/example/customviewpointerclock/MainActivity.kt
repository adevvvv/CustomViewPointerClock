package com.example.customviewpointerclock

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.customviewpointerclock.databinding.ActivityMainBinding
import com.example.customviewpointerclocklib.CustomViewClock
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var clockView: CustomViewClock
    var random: Random = Random()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clockView = findViewById(R.id.clockView)
        clockView.setBackgroundColor(100)

        binding.buttonText.setOnClickListener {
            clockView.setTextColor(
                Color.rgb(
                    random.nextInt(256),
                    random.nextInt(256),
                    random.nextInt(256)
                )
            )
        }
        binding.buttonHour.setOnClickListener {
            clockView.setHourColor(
                Color.rgb(
                    random.nextInt(256),
                    random.nextInt(256),
                    random.nextInt(256)
                )
            )
        }
        binding.buttonMin.setOnClickListener {
            clockView.setMinColor(
                Color.rgb(
                    random.nextInt(256),
                    random.nextInt(256),
                    random.nextInt(256)
                )
            )
        }
        binding.buttonSec.setOnClickListener {
            clockView.setSecColor(
                Color.rgb(
                    random.nextInt(256),
                    random.nextInt(256),
                    random.nextInt(256)
                )
            )
        }

    }
}