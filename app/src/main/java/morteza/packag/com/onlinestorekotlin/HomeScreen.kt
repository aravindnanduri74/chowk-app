package morteza.packag.com.onlinestorekotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_home_screen.*

class HomeScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        var brandsUrl = "http://192.168.1.124/OnlineStoreApp/fetch_brands.php"
//as always create an ur;
        var brandsList = ArrayList<String>()
//create an array for displaying name
        var requestQ = Volley.newRequestQueue(this@HomeScreen)



        var jsonAR = JsonArrayRequest(Request.Method.GET, brandsUrl, null, Response.Listener { response ->
        //before we created string request now we created a json array request

            //we create a for loop to iterate through various json object in our response
            //here response is an array
            for (jsonObject in 0.until(response.length())) {
                //response.getJsonObject(index) gives me an individual json obj
                    //json object.getString gives the value inside the json object
 

                brandsList.add(response.getJSONObject(jsonObject).getString("brand"))

            }


            var brandsListAdapter = ArrayAdapter(this@HomeScreen, R.layout.brand_item_text_view, brandsList)
            brandsListView.adapter = brandsListAdapter

        }, Response.ErrorListener { error ->

            val dialogBuilder= AlertDialog.Builder(this)
            dialogBuilder.setTitle("Message")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()
        })


        requestQ.add(jsonAR)
        //whatever req is there add to req queue



        brandsListView.setOnItemClickListener { adapterView, view, i, l ->


            val tappedBrand = brandsList.get(i)
            val intent = Intent(this@HomeScreen, FetchEProductsActivity::class.java)

            intent.putExtra("BRAND", tappedBrand)
            startActivity(intent)


        }


    }
}
