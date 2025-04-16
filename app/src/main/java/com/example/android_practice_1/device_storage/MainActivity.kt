package com.example.android_practice_1.device_storage

import android.Manifest
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.android_practice_1.R

class MainActivity : AppCompatActivity() {

    private lateinit var downloadButton: Button
    private lateinit var progressBar: ProgressBar
    private var downloadId: Long = -1

    private val downloadUrl = ""
    private val fileName = "invoice_184.pdf"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        downloadButton = findViewById(R.id.downloadButton)
        progressBar = findViewById(R.id.progressBar)

        downloadButton.setOnClickListener {
            if (checkPermissions()) {
                startDownload()
            }
        }

        // Register broadcast receiver to know when download is complete
        registerReceiver(downloadCompleteReceiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

    }

    private fun checkPermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // For Android 10 and above, no permissions needed for Downloads directory
            true
        } else {
            // For older versions, need WRITE_EXTERNAL_STORAGE permission
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    STORAGE_PERMISSION_CODE
                )
                false
            } else {
                true
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startDownload()
            } else {
                Toast.makeText(
                    this,
                    "Permission denied. Cannot download file.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun startDownload() {
        progressBar.visibility = ProgressBar.VISIBLE
        downloadButton.isEnabled = false

        val request = DownloadManager.Request(Uri.parse(downloadUrl))
            .setTitle("Invoice Download")
            .setDescription("Downloading invoice file")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadId = downloadManager.enqueue(request)

        Toast.makeText(this, "Download started...", Toast.LENGTH_SHORT).show()
    }

    private val downloadCompleteReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (id == downloadId) {
                progressBar.visibility = ProgressBar.GONE
                downloadButton.isEnabled = true
                Toast.makeText(
                    this@MainActivity,
                    "File downloaded to Downloads folder",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(downloadCompleteReceiver)
    }

    companion object {
        private const val STORAGE_PERMISSION_CODE = 1001
    }

}