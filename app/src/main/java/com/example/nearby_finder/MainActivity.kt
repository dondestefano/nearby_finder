package com.example.nearby_finder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import android.util.Log
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.test_textview)
    }

    override fun onResume() {
        super.onResume()
        PlaceDataManager.getTestData(this)
        observePlaceDataManger()
    }

    private fun observePlaceDataManger() {
        PlaceDataManager.testValue.observe(this, Observer {
            it.let {
                when(it.id) {
                    1 -> {
                        textView.text = PlaceDataManager.testValue.value?.title.toString()
                    }

                    else -> {
                        textView.text = "No data"
                    }
                }
            }
        })
    }
}