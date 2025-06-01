package com.example.android_practice_1.nested_menu.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_practice_1.R
import com.example.android_practice_1.databinding.ActivityNestedBinding
import com.example.android_practice_1.nested_menu.adapter.CategoryRvAdapter
import com.example.android_practice_1.nested_menu.helper.JsonParser
import com.example.android_practice_1.nested_menu.model.Category
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

class NestedActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityNestedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_nested)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_nested)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Load your static JSON
        val jsonString = """
            {
              "success": true,
              "message": "",
              "categoryList": {
                "parent": [
                  {
                    "name": "New",
                    "id": "6"
                  },
                  {
                    "name": "Men",
                    "id": "3",
                    "child": [
                      {
                        "name": "Ski",
                        "id": "8"
                      },
                      {
                        "name": "Athleisure",
                        "id": "92"
                      },
                      {
                        "name": "See All",
                        "id": "3"
                      }
                    ]
                  },
                  {
                    "name": "Women",
                    "id": "4",
                    "child": [
                      {
                        "name": "Athleisure",
                        "id": "17",
                        "child": [
                          {
                            "name": "Tops",
                            "id": "96"
                          },
                          {
                            "name": "Bottoms",
                            "id": "97"
                          },
                          {
                            "name": "Skirts & Dresses",
                            "id": "98"
                          },
                          {
                            "name": "Outerwear",
                            "id": "99"
                          },
                          {
                            "name": "Accessories",
                            "id": "100"
                          },
                          {
                            "name": "See All",
                            "id": "17"
                          }
                        ]
                      },
                      {
                        "name": "Golf",
                        "id": "19",
                        "child": [
                          {
                            "name": "New Arrivals",
                            "id": "20"
                          },
                          {
                            "name": "Polo Shirts",
                            "id": "21"
                          },
                          {
                            "name": "T-Shirt",
                            "id": "44"
                          },
                          {
                            "name": "Skirts & Dresses",
                            "id": "22"
                          },
                          {
                            "name": "Base & Midlayers",
                            "id": "23"
                          },
                          {
                            "name": "Knitwear",
                            "id": "24"
                          },
                          {
                            "name": "Sweater",
                            "id": "45"
                          },
                          {
                            "name": "Outerwear",
                            "id": "46"
                          },
                          {
                            "name": "Shorts",
                            "id": "25"
                          },
                          {
                            "name": "Trousers",
                            "id": "47"
                          },
                          {
                            "name": "Accessories",
                            "id": "26",
                            "child": [
                              {
                                "name": "Cap",
                                "id": "57"
                              },
                              {
                                "name": "Hat",
                                "id": "58"
                              },
                              {
                                "name": "Visor",
                                "id": "59"
                              },
                              {
                                "name": "Belt",
                                "id": "60"
                              },
                              {
                                "name": "Sleeve",
                                "id": "61"
                              },
                              {
                                "name": "Socks",
                                "id": "62"
                              },
                              {
                                "name": "See All",
                                "id": "26"
                              }
                            ]
                          },
                          {
                            "name": "Bag",
                            "id": "63"
                          },
                          {
                            "name": "See All",
                            "id": "19"
                          }
                        ]
                      },
                      {
                        "name": "Ski",
                        "id": "18",
                        "child": [
                          {
                            "name": "Bottoms",
                            "id": "95"
                          },
                          {
                            "name": "Outerwear",
                            "id": "94"
                          },
                          {
                            "name": "See All",
                            "id": "18"
                          }
                        ]
                      },
                      {
                        "name": "See All",
                        "id": "4"
                      }
                    ]
                  },
                  {
                    "name": "Sale",
                    "id": "84"
                  },
                  {
                    "name": "SH24: Men's Golf",
                    "id": "121"
                  },
                  {
                    "name": "SH24: Women's Golf",
                    "id": "122"
                  },
                  {
                    "name": "Suits & Sets",
                    "id": "123"
                  }
                ]
              },
              "eTag": "ca4274075de2a23226e64cbcfd51d20e8ea61a969cf7e6257a3bf20a9ca44cb7"
            }
        """.trimIndent()

        val categoryList = parseJsonResponse(jsonString)
        Log.d("check_nested","category list -> ${categoryList}")
        setupRecyclerView(categoryList)

    }

    data class ApiResponse(
        val success: Boolean,
        val message: String,
        val categoryList: CategoryList
    )

    data class CategoryList(
        val parent: ArrayList<Category>
    )

    // Then parse like this:
    private fun parseJsonResponse(json: String): ArrayList<Category> {
        val gson = Gson()
        val response = gson.fromJson(json, ApiResponse::class.java)
        return response.categoryList.parent
    }

    private fun setupRecyclerView(categories: ArrayList<Category>) {
        mBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@NestedActivity)
            Log.d("check_category","category list -> ${Gson().toJson(categories)}")
            adapter = CategoryRvAdapter(this@NestedActivity, categories)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

}