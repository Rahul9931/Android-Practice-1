package com.example.android_practice_1.graphql_practice

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.apollographql.apollo.api.ApolloResponse
import com.example.android_practice_1.R
import com.example.android_practice_1.databinding.ActivityGraphQlpracticeBinding
import com.example.android_practice_1.graphql_practice.Network.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import src.main.graphql.ContinentFetchingQuery

class GraphQLPracticeActivity : AppCompatActivity() {
    val graphQlpracticeBinding: ActivityGraphQlpracticeBinding by lazy {
        ActivityGraphQlpracticeBinding.inflate(layoutInflater)
    }
    lateinit var response: ApolloResponse<ContinentFetchingQuery.Data>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(graphQlpracticeBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        graphQlpracticeBinding.btnCallApi.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                calgraphQLAPI()
            }

        }
    }

    private suspend fun calgraphQLAPI() {
        var job = CoroutineScope(Dispatchers.IO).launch {
            response= ApiClient.ApolloClient().query(ContinentFetchingQuery()).execute()

        }
        job.join()
        Log.d("check_api_res","response  = ${response.data}")
    }
}