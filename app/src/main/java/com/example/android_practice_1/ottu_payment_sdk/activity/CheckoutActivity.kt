package com.example.android_practice_1.ottu_payment_sdk.activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.android_practice_1.R
import com.example.android_practice_1.databinding.ActivityPracticeActyvityBinding
import com.ottu.checkout.Checkout
import com.ottu.checkout.network.model.payment.ApiTransactionDetails

class CheckoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_checkout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        val apikey = ""
//        val sessionid = ""
//        val merchantid = ""

        val apikey = "9c0ef01238c8c1efc156fa0e65b342704086e929"
        val sessionid = "79ee5c0b5a96a5cec3717d6d1407f4e9878a160c"
        val merchantid = "sandbox.ottu.net"

        val btn = findViewById<Button>(R.id.btn_pay)
        btn.setOnClickListener {
            startPayment(merchantid, sessionid, apikey,"500")
        }
    }

    private fun startPayment(merchantId: String, sessionId: String, apiKey: String, amount: String) {
        // Optional: Define forms of payment
        //val formsOfPayment = arrayOf("ottuPG", "tokenPay", "redirect", "stcPay")

        // Optional: Setup preload details
//        val setupPreload = ApiTransactionDetails(
//            orderNo = "ORDER12345",
//            description = "Payment for Order 12345",
//            currency = "USD",
//            totalAmount = amount
//        )

        // Create theme
//        val theme = getCheckoutTheme()

        // Initialize the builder
        val builder = Checkout.Builder(merchantId, sessionId, apiKey, amount.toDouble())
//            .theme(theme)
            .logger(Checkout.Logger.INFO)
            .build()

        // Start checkout
        val checkoutFragment = Checkout.init(
            context = this@CheckoutActivity,
            builder = builder,
            successCallback = { response ->
                Log.d("Checkout", "Payment Successful: $response")
                showResultDialog("Payment Successful: $response")
            },
            cancelCallback = { response ->
                Log.d("Checkout", "Payment Cancelled: $response")
                showResultDialog("Payment Cancelled: $response")
            },
            errorCallback = { errorData, throwable ->
                Log.d("Checkout", "Payment Error: $errorData", throwable)
                showResultDialog("Payment Error: $errorData", throwable)
            }
        )

        // Add the checkout fragment to your activity layout
        supportFragmentManager.beginTransaction()
            .replace(R.id.checkoutContainer, checkoutFragment)
            .commit()
    }

    private fun showResultDialog(message: String, throwable: Throwable? = null) {
        // Display the result in a dialog
        val dialog = AlertDialog.Builder(this)
            .setTitle("Payment Result")
            .setMessage(message + (throwable?.localizedMessage ?: ""))
            .setPositiveButton("OK") { _, _ -> }
            .create()
        dialog.show()
    }

//    private fun getCheckoutTheme(): Checkout.Theme {
//        // Define and return your theme settings here
//        return Checkout.Theme.Builder()
//            .setBackgroundColor("#FFFFFF") // Example: White background
//            .setTextColor("#000000")      // Example: Black text color
//            .build()
//    }

}