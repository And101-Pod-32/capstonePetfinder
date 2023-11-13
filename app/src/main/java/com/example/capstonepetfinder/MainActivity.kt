package com.example.capstonepetfinder

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler.JSON
import com.example.capstonepetfinder.databinding.ActivityMainBinding
import okhttp3.Headers

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // lists for items shown in recycler view
    private var imageList:MutableList<String> = mutableListOf("Test", "Test2")
    private var nameList:MutableList<String> = mutableListOf("Test", "Test2")
    private var sexList:MutableList<String> = mutableListOf("Test", "Test2")
    private var breedList:MutableList<String> = mutableListOf("Test", "Test2")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spinners()
        recyclerView()
        fetchPetData()
    }

    private fun spinners() {
        // map of our spinners -- include all spinners here
        val spinnerMap = mapOf(
            binding.spinnerType to R.array.type_options,
            binding.spinnerSize to R.array.size_options,
            binding.spinnerGender to R.array.gender_options)

        // run spinnerAdapter on all spinners in spinMap
        for ((spinner, options) in spinnerMap) {
            spinnerAdapter(spinner, options)
        }
    }

    private fun spinnerAdapter(spinner: Spinner, options: Int) {
        ArrayAdapter.createFromResource(
            this,
            options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun recyclerView() {
        val recyclerView = binding.recyclerView
        val petFinderAdapter = PetFinderAdapter(imageList, nameList, sexList, breedList)
        recyclerView.adapter = petFinderAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    // api call, using "The Dog API" for now
    private fun fetchPetData() {
        val client = AsyncHttpClient()
        val params = RequestParams()
        val apiUrl = "https://api.thedogapi.com/v1/images/search"
        val apiKey = "live_BNZSRUJLsMvPscAphfAbQrHuc9REhnFR3ongMHooJ8EgpbXEuzeojRUKNQf6A9y6"
        params["limit"] = "5"
        params["page"] = "0"
        params["key"] = apiKey

        client[apiUrl, params, object :
            JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                loadPetData(json)
            }

            override fun onFailure(statusCode: Int, headers: Headers?, response: String, throwable: Throwable?) {
                Log.e("ERROR", response)
            }
        }]
    }

    // will parse json into mutable lists, then load into recycler view
    private fun loadPetData(json: JSON) {
        println(json)
    }
}