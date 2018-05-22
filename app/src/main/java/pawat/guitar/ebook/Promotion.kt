package pawat.guitar.ebook

import android.os.Bundle
import java.util.*

class Promotion {
    var bookIDlist = ArrayList<Int>()
    var price : Double
    var id : Int
    var title : String

    constructor(list: ArrayList<Int>,price : Double,id : Int, title : String){
        bookIDlist.addAll(list)
        this.price = price
        this.id = id
        this.title = title
    }

    override fun toString(): String {
        return bookIDlist.toString()+"$title ($price)"
    }

    fun toBundle(): Bundle {
        val bundle = Bundle()
        bundle.putInt("id",id)
        bundle.putString("title",title)
        bundle.putDouble("price",price)
        bundle.putIntegerArrayList("list",bookIDlist)
        return bundle
    }
}