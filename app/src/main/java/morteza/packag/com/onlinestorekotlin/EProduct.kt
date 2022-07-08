package morteza.packag.com.onlinestorekotlin

//A class is created such that custom data could be put
//and a constructor to put the data

class EProduct {

//these are the items we are getting from server or database

    var id: Int //id of product
    var name: String
    var price: Int
    var pictureName: String //picture name is also string


    constructor(id: Int, name: String, price: Int,
                picture: String) {

        this.id = id //this keyword is used coz name of variable and assignment variable is same
        this.name = name
        this.price = price
        this.pictureName = picture

    }


}