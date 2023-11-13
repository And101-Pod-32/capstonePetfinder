package com.example.capstonepetfinder

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.capstonepetfinder.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // creating a list for images
    private lateinit var imageList:MutableList<String>
    private lateinit var nameList:MutableList<String>
    private lateinit var sexList:MutableList<String>
    private lateinit var breedList:MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val genderSpinner : Spinner = binding.spinnerGender
        val typeSpinner : Spinner = binding.spinnerType
        val sizeSpinner : Spinner = binding.spinnerSize


        //initializing the lists
        imageList = mutableListOf()
        nameList = mutableListOf()
        sexList = mutableListOf()
        breedList = mutableListOf()

        spinnerAdapter(genderSpinner, R.array.gender_options)
        spinnerAdapter(sizeSpinner, R.array.size_options)
        spinnerAdapter(typeSpinner, R.array.type_options)
    }

    private fun spinnerAdapter(spinner: Spinner, spinnerOptions: Int) {
        ArrayAdapter.createFromResource(
            this,
            spinnerOptions,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }
}
