package morteza.packag.com.onlinestorekotlin


import android.app.DialogFragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class AmountFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        var fragmentView =  inflater.inflate(R.layout.fragment_amount, container, false)

        //this is a view which we got after inflating the frafment_amount.xml


        var edtEnterAmount = fragmentView.findViewById<EditText>(R.id.edtEnterAmount) //createdthe reference to edit text

        var btnAddToCart = fragmentView.findViewById<ImageButton>(R.id.btnAddToCart) //created reference to button

        btnAddToCart.setOnClickListener {
 //writinh code for what should happen after we click button

            var ptoUrl = "http://192.168.1.124/OnlineStoreApp/insert_temporary_order.php?email=${Person.email}&product_id=${Person.addToCartProductID}&amount=${edtEnterAmount.text.toString()}"

            //& oprtor is used to seperate the different components
            var requestQ = Volley.newRequestQueue(activity)

            //we need to give the context as current activity we are in but we cant give fragment activity as context so we give context of activity this fragment was actually dfeined in so we weitte activity
            var stringRequest = StringRequest(Request.Method.GET, ptoUrl, Response.Listener{ response ->

                var intent = Intent(activity, CartProductsActivity::class.java)

                startActivity(intent)


            }, Response.ErrorListener { error ->


            })

            requestQ.add(stringRequest)

        }

        return fragmentView //return the fragment view from line 24

    }


}
