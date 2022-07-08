package morteza.packag.com.onlinestorekotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import kotlinx.android.synthetic.main.activity_finalize_shopping.*
import java.math.BigDecimal

class FinalizeShoppingActivity : AppCompatActivity() {


    var ttPrice: Long = 0
    //total price is global to this class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finalize_shopping)

        var calculateTotalPriceUrl = "http://192.168.1.124//OnlineStoreApp/calculate_total_price.php?invoice_num=${intent.getStringExtra("LATEST_INVOICE_NUMBER")}"
        //we got invpice number from cart product activity through intent
        var requestQ = Volley.newRequestQueue(this@FinalizeShoppingActivity) //create request queue object
        var stringRequest = StringRequest(Request.Method.GET, calculateTotalPriceUrl, Response.Listener { response ->

            btnPaymentProcessing.text = "Pay $$response via Paypal Now!"
            ttPrice = response.toLong()

        }, Response.ErrorListener { error ->


        })


        requestQ.add(stringRequest)


        var paypalConfig: PayPalConfiguration = PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(MyPayPal.clientID)

        //we created a object of paypal configuration which means we are setting up paypal using paypalconfiguration object which is an object of paypal config class
        //environment of paypal is sandbox which is testing
        //and we setup client id using companion object
        var ppService = Intent(this@FinalizeShoppingActivity, PayPalService::class.java)
        //INTENT IS ALSO USED TO START A SERVICE
        ppService.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig)
        startService(ppService)
        //WE START SERVICE THROUGH MESSAGING OBJECT

        //we started paypal service till here its running now we need to decide what to do when button is clicked
        btnPaymentProcessing.setOnClickListener {

//for payapl payment class we need to mention the total price
            var ppProcessing = PayPalPayment(BigDecimal.valueOf(ttPrice),
                    "USD", "Online Store Kotlin!",
                    PayPalPayment.PAYMENT_INTENT_SALE)

            //tt price->specifies what payment need to be paid
            //format of payment is usd
            //msg to be displayed is "ONLINE STORE KOTLIN"

            var paypalPaymentIntent = Intent(this, PaymentActivity::class.java) //we gi to paymentactivity through this activity this activity is already given in paypal sdk
            paypalPaymentIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig)
            //we sent the paypal config like payment section and configuration
            paypalPaymentIntent.putExtra(PaymentActivity.EXTRA_PAYMENT, ppProcessing)
            startActivityForResult(paypalPaymentIntent, 1000)

            //we dont just use startAct we use startActforResylt coz we are expecting some result backkk



        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //after getting result then we need to do this

        if (requestCode == 1000) {
            //firstly we check if request code is same==1000
            if (resultCode == Activity.RESULT_OK) {
//resultcode is res we get
    //Activity.Result_OK means if result is sucessful

        //then we go to thankyou

                var intent = Intent(this, ThankYouActivity::class.java)
                startActivity(intent)

            } else {

                Toast.makeText(this, "Sorry! Something went wrong. Try Again", Toast.LENGTH_SHORT).show()

            }

        }
    }


    override fun onDestroy() {

        //on destroy method is called when activity is not in use or not in the acitivty stack where activity are rexecuted
        super.onDestroy()

        //then we stop service
        stopService(Intent(this, PayPalService::class.java))
    }

    }



