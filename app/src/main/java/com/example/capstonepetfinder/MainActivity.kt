package com.example.capstonepetfinder

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler.JSON
import com.example.capstonepetfinder.databinding.ActivityMainBinding
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import com.codepath.asynchttpclient.RequestHeaders

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // lists for items shown in recycler view
    private var imageList: MutableList<String> = mutableListOf()
    private var nameList: MutableList<String> = mutableListOf()
    private var sexList: MutableList<String> = mutableListOf()
    private var breedList: MutableList<String> = mutableListOf()

    // initializing adapter instance
    private lateinit var petFinderAdapter: PetFinderAdapter

    // variables for OAuth access
    private val apiKey = "w7kXIyDrj2k6Y3uUs7Nl1PrkIeMNYA74rNAgarObh9bLonrx6K"
    private val apiSecret =
        "ht7ZIR8WwW6AuKFiXm8eOKSNYMr34DwtL5CgA2Tn"
    private val petfinderBaseUrl = "https://api.petfinder.com/v2/"
    private var accessToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spinners()
        recyclerView()

        // adding empty lists into the adapter
        petFinderAdapter = PetFinderAdapter(imageList, nameList, sexList, breedList)
        binding.recyclerView.adapter = petFinderAdapter

        // fetch access token before fetching pet data
        fetchAccessToken()
    }

    // fetching token for API access
    private fun fetchAccessToken() {
        val client = OkHttpClient()

        val requestBody = FormBody.Builder()
            .add("grant_type", "client_credentials")
            .add("client_id", apiKey)
            .add("client_secret", apiSecret)
            .build()

        val request = Request.Builder()
            .url("${petfinderBaseUrl}oauth2/token")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("AccessTokenError", "Failed to obtain access token: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                try {
                    val json = JSONObject(responseData)
                    accessToken = json.getString("access_token")
                    // now that an access token is created, pet data can be fetched
                    fetchPetData()
                } catch (e: Exception) {
                    Log.e("AccessTokenError", "Error parsing access token response: ${e.message}")
                }
            }
        })
    }

    private fun spinners() {
        // map of our spinners -- include all spinners here
        val spinnerMap = mapOf(
            binding.spinnerType to R.array.type_options,
            binding.spinnerSize to R.array.size_options,
            binding.spinnerGender to R.array.gender_options
        )

        // run spinnerAdapter on all spinners in spinMap
        for ((spinner, options) in spinnerMap) {
            spinnerAdapter(spinner, options)
            spinner.onItemSelectedListener = createSpinnerListener()
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

    private fun createSpinnerListener(): AdapterView.OnItemSelectedListener {
        return object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                fetchPetData()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // when no values are selected in spinners
            }
        }
    }

    private fun recyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    // updated for petfinder API
    private fun fetchPetData() {
        val client = AsyncHttpClient()
        val params = RequestParams()
        val apiUrl = "https://api.petfinder.com/v2/animals"



        // parsing info user selects from spinners
        val selectedType = binding.spinnerType.selectedItem.toString()
        val selectedSize = binding.spinnerSize.selectedItem.toString()
        val selectedGender = binding.spinnerGender.selectedItem.toString()

        params["limit"] = "5"
        params["page"] = "1"
        params["key"] = apiKey
        params["type"] = selectedType.lowercase()
        params["size"] = selectedSize.lowercase()
        params["gender"] = selectedGender.lowercase()

        //creating request header for api
        val headers = RequestHeaders()
        headers.put("Authorization", "Bearer $accessToken")


        client[apiUrl, headers, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                loadPetData(json)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String,
                throwable: Throwable?
            ) {
                Log.e("ERROR", response)
            }
        }]
    }

    // will parse JSON into mutable lists, then load into recycler view
    private fun loadPetData(json: JSON) {
//        // clear existing data whenever new data needs to be loaded
//        imageList.clear()
//        nameList.clear()
//        sexList.clear()
//        breedList.clear()

        // log the raw JSON retrieved
        Log.d("JSON", json.toString())

        // parse the JSON and update the lists
        val jsonArray = json.jsonArray
        for (i in 0 until jsonArray.length()) {
            val petObject = jsonArray.getJSONObject(i)
            val imageUrl = petObject.getJSONArray("photos").getJSONObject(0).getString("medium")
            val name = petObject.getString("name")
            val sex = petObject.getString("gender")
            val breed = petObject.getJSONArray("breeds").getJSONObject(0).getString("name")

            // log data parsed for each pet
            Log.d("Pet Data", "Name: $name, Sex: $sex, Breed: $breed, Image URL: $imageUrl")

            // Add parsed data to lists
            imageList.add(imageUrl)
            nameList.add(name)
            sexList.add(sex)
            breedList.add(breed)
        }

        // update existing adapter's data and notify the change
        petFinderAdapter.notifyDataSetChanged()
    }
}
