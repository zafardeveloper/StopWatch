package com.example.stopwatch

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var buttonStart: Button
    private lateinit var buttonReset: Button
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)
        buttonStart = findViewById(R.id.button)
        buttonReset = findViewById(R.id.buttonReset)
        buttonReset.visibility = View.GONE
        buttonStart.setBackgroundColor(Color.GREEN)
        var isChecked = false


        buttonStart.setOnClickListener {

            isChecked = !isChecked

            when (isChecked) {
                true -> {
                    buttonStart.text = "Stop"
                    buttonStart.setBackgroundColor(Color.RED)
                    buttonReset.visibility = View.GONE
                    startCounter()
                }

                false -> {
                    buttonStart.text = "Resume"
                    buttonStart.setBackgroundColor(Color.BLUE)
                    buttonReset.visibility = View.VISIBLE
                    job?.cancel()
                }
            }
        }

        buttonReset.setOnClickListener {
            buttonStart.text = "Start"
            buttonStart.setBackgroundColor(Color.GREEN)
            buttonReset.visibility = View.GONE
            textView.text = "0"
            job?.cancel()
            job = null
        }
    }

    private fun startCounter() {
        job?.cancel()
        job = CoroutineScope(Dispatchers.Main).launch {
            var counter = textView.text.toString().toInt()
            while (isActive) {
                textView.text = counter.toString()
                delay(1000)
                counter++
            }
        }
    }

    override fun onDestroy() {
        job?.cancel()
        job = null
        super.onDestroy()
    }
}