package morteza.packag.com.onlinestorekotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_cart_products.*
import java.util.*

class CartProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_products)

        var cartProductsUrl = "http://192.168.1.124/OnlineStoreApp/fetch_temporary_order.php?email=${Person.email}"
        //WE got url and appended email to it with person.email
        var cartProductsList = ArrayList<String>() //we created an string array of product list
        var requestQ = Volley.newRequestQueue(this@CartProductsActivity)
        var jsonAR = JsonArrayRequest(Request.Method.GET, cartProductsUrl, null, Response.Listener{
            response ->


            for (joIndex in 0.until(response.length())) { // id, name, price, email, amount

                cartProductsList.add("${response.getJSONObject(joIndex).getInt("id")}" +
                        " \n ${response.getJSONObject(joIndex).getString("name")}" +
                        " \n ${response.getJSONObject(joIndex).getInt("price")}" +
                        " \n ${response.getJSONObject(joIndex).getString("email")}" +
                        " \n ${response.getJSONObject(joIndex).getInt("amount")}")

                //we added all details to vector of string
            }

            var cartProductsAdapter = ArrayAdapter(this@CartProductsActivity, android.R.layout.simple_list_item_1, cartProductsList)
            //we used default array adapter to put the data into the list
            //we specify the context then layout then data

            cartProductsListView.adapter = cartProductsAdapter

            //then listview.adapter=adapter

        }, Response.ErrorListener { error ->



        })


        requestQ.add(jsonAR)

        //then we do request q.add json array req
    }

    //to infalte the menu we need to override the OnCreate Options menu function

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.cart_menu, menu)
        //we are inflating the menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

//ITEM INSIDE THE ARG IS THE ITEM WE SELECT
        //ITEM.ITEMID GIVES THE ITEM ID
        //WE PUT ? COZ IT COULD BE NULL ALSO WHEN WE DONT SELECT ANY ITEM
        //SO WE WRITING MULTIPLE IF ELSE STATMENT TO COMPARE WHICH ONE IS SELECTED
       if (item?.itemId == R.id.continueShoppingItem) {

           //IF WE SELECT CONTINUE GO TO HOME SCREEN

           var intent = Intent(this, HomeScreen::class.java)
           startActivity(intent)

       } else if (item?.itemId == R.id.declineOrderItem) {

           var deleteUrl = "http://192.168.1.124//OnlineStoreApp/decline_order.php?email=${Person.email}"
           var requestQ = Volley.newRequestQueue(this@CartProductsActivity)
           var stringRequest = StringRequest(Request.Method.GET, deleteUrl, Response.Listener{
               response ->

//AFTER all the items are created go to home screen
               var intent = Intent(this, HomeScreen::class.java)
               startActivity(intent)

           }, Response.ErrorListener {
               error ->



           })

           requestQ.add(stringRequest)
       } else if (item?.itemId == R.id.verifyOrderItem) {

           //if the user clicks to verify order

           var verifyOrderUrl = "http://192.168.1.124/OnlineStoreApp/verify_order.php?email=${Person.email}" //this is the url we need to execute
           var requestQ = Volley.newRequestQueue(this@CartProductsActivity) //created a request queue
           var stringRequest = StringRequest(Request.Method.GET, verifyOrderUrl, Response.Listener {
               response ->

               //the response is the


               var intent = Intent(this, FinalizeShoppingActivity::class.java)
               Toast.makeText(this, response, Toast.LENGTH_LONG).show()
               intent.putExtra("LATEST_INVOICE_NUMBER", response)
               //we are passing the invoice number to next activity
               startActivity(intent)


           }, Response.ErrorListener { error ->  })



           requestQ.add(stringRequest)
       }

        return super.onOptionsItemSelected(item)
    }

}
