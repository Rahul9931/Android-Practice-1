package com.example.android_practice_1.search_pagination.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import com.example.android_practice_1.R

class CustomToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {

    private lateinit var searchView: SearchView
    private lateinit var titleTextView: TextView
    private lateinit var backButton: ImageButton
    private lateinit var searchButton: ImageButton
    private lateinit var voiceSearchButton: ImageButton // Add this

    var onBackPressed: (() -> Unit)? = null
    var onSearchStateChanged: ((isSearching: Boolean) -> Unit)? = null
    var onQueryTextChanged: ((query: String) -> Unit)? = null
    var onVoiceSearchRequested: (() -> Unit)? = null

    private var isSearching = false

    init {
        // Set toolbar properties
        setContentInsetsAbsolute(0, 0)
        setContentInsetsRelative(0, 0)
        setBackgroundColor(context.getColor(R.color.colorPrimary))

        // Inflate layout
        LayoutInflater.from(context).inflate(R.layout.custom_toolbar, this, true)

        // Initialize views
        backButton = findViewById(R.id.backButton)
        titleTextView = findViewById(R.id.titleTextView)
        searchButton = findViewById(R.id.searchButton)
        searchView = findViewById(R.id.searchView)
        voiceSearchButton = findViewById(R.id.voiceSearchButton)

        // Configure search view
        searchView.maxWidth = Integer.MAX_VALUE // Important for proper expansion
        searchView.isIconified = false // Ensure it's not iconified by default

        // Set default title
        titleTextView.text = "Product List"

        // Setup click listeners
        backButton.setOnClickListener {
            if (isSearching) {
                toggleSearch()
            } else {
                onBackPressed?.invoke()
            }
        }

        searchButton.setOnClickListener {
            toggleSearch()
        }

        // Set click listener for voice button
        voiceSearchButton.setOnClickListener {
            onVoiceSearchRequested?.invoke()
        }

        // Configure search view
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                onQueryTextChanged?.invoke(newText ?: "")
                return true
            }
        })
    }

    fun setTitle(title: String) {
        titleTextView.text = title
    }

    fun toggleSearch() {
        isSearching = !isSearching
        if (isSearching) {
            titleTextView.visibility = View.GONE
            searchButton.visibility = View.GONE
            searchView.visibility = View.VISIBLE
            voiceSearchButton.visibility = View.VISIBLE
            searchView.isIconified = false
            post {
                searchView.requestFocus()
            }
        } else {
            titleTextView.visibility = View.VISIBLE
            searchButton.visibility = View.VISIBLE
            searchView.visibility = View.GONE
            voiceSearchButton.visibility = View.GONE
            searchView.setQuery("", false)
            searchView.clearFocus()
        }
        onSearchStateChanged?.invoke(isSearching)
    }

    fun setSearchQuery(query: String) {
        searchView.setQuery(query, false)
    }
}