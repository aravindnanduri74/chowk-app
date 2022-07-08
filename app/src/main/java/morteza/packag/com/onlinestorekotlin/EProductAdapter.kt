package morteza.packag.com.onlinestorekotlin

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.e_product_row.view.*

//basically we are defining our custom adapter
//why custom adapter coz for custom adapter we can display custom data
//normal adapter we can only display single item

class EProductAdapter(var context: Context, var arrayList: ArrayList<EProduct>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //it requries an context
    //EproductAdapter inherits from abstract class which is RecyclerView.Adapter whose implementation done through some other classes and that implementation is being done now

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

//this returns a view Holder
        val productView = LayoutInflater.from(context).inflate(R.layout.e_product_row, parent, false)
        //we are inflating e_product_row.xml into a view

        return ProductViewHolder(productView)

        //we r returing object of productViewHolder which is defined by inner class which is taking a view and intializing the data
    }

    override fun getItemCount(): Int {
//returns number of items inside the recyclerview

        return arrayList.size

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        (holder as ProductViewHolder).initializeRowUIComponents(arrayList.get(position).id, arrayList.get(position).name, arrayList.get(position).price, arrayList.get(position).pictureName)

        //we are downcasting the holder as ProductViewHolder
        //initialization is done here
        //array list is the data set
    }

    //inner class is used to put data to custom row we created

    inner class ProductViewHolder(pView: View)
        : RecyclerView.ViewHolder(pView) {


        fun initializeRowUIComponents(id: Int, name: String, price: Int, picName: String) {

            //initialize the component of eproductrow.xml
            //itemView refers to the entire e_product_row.xml which we are gonna inflate to recycler view

            itemView.txtId.text = id.toString()
            itemView.txtName.text = name
            itemView.txtPrice.text = price.toString()

            var picUrl = "http://192.168.1.124/OnlineStoreApp/osimages/"
            picUrl = picUrl.replace(" ", "%20") //url should not have any spaces so we replace " " with "%20"
            Picasso.get().load(picUrl + picName).into(itemView.imgProduct) //with help of Picasso library we can get images from server here local server no need tp put on pur drawable

//when this function is called an on click listner is going to be defined for the plus button


            itemView.imgAdd.setOnClickListener {


                Person.addToCartProductID = id
                var amountFragment = AmountFragment()
                //we are referring to the fragment we created which is amount fragment we are creating object of it
                var fragmentManager = (itemView.context as Activity).fragmentManager
                amountFragment.show(fragmentManager, "TAG")



            }


        }



    }

}