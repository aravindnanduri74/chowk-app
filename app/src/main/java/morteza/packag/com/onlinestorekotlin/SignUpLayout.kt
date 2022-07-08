package morteza.packag.com.onlinestorekotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.sign_up_layout.*

class SignUpLayout : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_layout)

        sign_up_layout_btnSignUp.setOnClickListener {

            if (sign_up_layout_edtPassword.text.toString().equals(
                            sign_up_layout_edtConfirmPassword.text.toString())) {

                // Registration Process

                val signUpURL = "http://192.168.1.124/OnlineStoreApp/join_new_user.php?email=" +
                        sign_up_layout_edtEmail.text.toString() +
                        "&username=" +
                        sign_up_layout_edtUsername.text.toString() +
                        "&pass=" + sign_up_layout_edtPassword.text.toString()

                //So when the entire url is called with username entered password entered then only in our sql table the data is added
                //with the help of volley library we can communicate with the server we can give request to the server and get the response from the server

                val requestQ = Volley.newRequestQueue(this@SignUpLayout)
                //a new request queue is created so that we can give multiple request to the request queue and FIFO method they are executed

                val stringRequest = StringRequest(Request.Method.GET, signUpURL, Response.Listener

                //if the response is not an error then we call Response.Listener same like button.listner resposne.Listner is call back method when some action is done what to do they specify
                //here action is getting Response so Response.Listner{ it takes response as argument which is the actual response
                //We need to sepecify the request type if it is GET or etc


                //we create a create a request we need to have api for any request here the api is url and we will get the response in our android studip
                { response ->

                    if (response.equals("A user with this Email Address already exists")) {

                        val dialogBuilder= AlertDialog.Builder(this)
                        dialogBuilder.setTitle("Message")
                        dialogBuilder.setMessage(response)
                        dialogBuilder.create().show()


                    } else {

//                        val dialogBuilder= AlertDialog.Builder(this)
//                        dialogBuilder.setTitle("Message")
//                        dialogBuilder.setMessage(response)
//                        dialogBuilder.create().show()

                        Person.email = sign_up_layout_edtEmail.text.toString()

                        Toast.makeText(this@SignUpLayout, response, Toast.LENGTH_SHORT).show()

                        val homeIntent = Intent(this@SignUpLayout, HomeScreen::class.java)
                        startActivity(homeIntent)

                    }

                }, Response.ErrorListener { error ->

                    val dialogBuilder= AlertDialog.Builder(this)
                    dialogBuilder.setTitle("Message")
                    dialogBuilder.setMessage(error.message)
                    dialogBuilder.create().show()

                })

                requestQ.add(stringRequest)

                //we add the request to the reqQ and these request in the request Q would be executed one by one

            } else {

                val dialogBuilder= AlertDialog.Builder(this) //alert dialog.builder class object is created
                dialogBuilder.setTitle("Message") //.setTitle of this class sets title
                dialogBuilder.setMessage("Password Mismatch")
                dialogBuilder.create().show()

            }


        }

        sign_up_layout_btnLogin.setOnClickListener {

            finish()

//            //when user finshes the work in this activity we need to go to default activity which activity so we can simply call
//            finish method which finishes current activity

        }

    }
}
