package morteza.packag.com.onlinestorekotlin

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_fetch_eproducts.*
import java.util.*

class FetchEProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch_eproducts)

        val selectedBrand:String = intent.getStringExtra("BRAND")
        txtBrandName.text = "Products of $selectedBrand"

        var productsList = ArrayList<EProduct>()

        //product list is an array to hold data of type e product class

        val productsUrl = "http://192.168.1.124/OnlineStoreApp/fetch_eproducts.php?brand=$selectedBrand"
        val requestQ = Volley.newRequestQueue(this@FetchEProductsActivity)
        val jsonAR = JsonArrayRequest(Request.Method.GET, productsUrl, null, Response.Listener {
            response ->


            for (productJOIndex in 0.until(response.length())) {


                productsList.add(EProduct(response.getJSONObject(productJOIndex).getInt("id"), response.getJSONObject(productJOIndex).getString("name"), response.getJSONObject(productJOIndex).getInt("price"), response.getJSONObject(productJOIndex).getString("picture")))

                //we need to do productlist.add(object of Eproduct)
                //so to create object we use constructor right
                //so we did samething prodlist.add(Eproduct(json.getID,json.getBrand,json.getName) etc so indeed we are creating a object
            }

            val pAdapter = EProductAdapter(this@FetchEProductsActivity, productsList)
            productsRV.layoutManager  = LinearLayoutManager(this@FetchEProductsActivity) //layout manager is used to describe the layout of the recycler view
            productsRV.adapter = pAdapter //recyclerView.adpaer=padpaer

        }, Response.ErrorListener { error ->

            val dialogBuilder= AlertDialog.Builder(this)
            dialogBuilder.setTitle("Message")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()

        })

        requestQ.add(jsonAR)

    }
}
