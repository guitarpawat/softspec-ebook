package pawat.guitar.ebook

import android.os.Bundle


class Book(val id: Int,
           val title: String,
           val price: Double = 0.0,
           val publicationYear: Int = 0,
           val imageURL: String = "") {

    override fun toString(): String {
        return "$title ($price)"
    }

    override fun equals(other: Any?): Boolean {
        if(other == null) return false
        if(other !is Book) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }

    fun toBundle(): Bundle {
        val bundle = Bundle()
        bundle.putInt("id",id)
        bundle.putString("title",title)
        bundle.putDouble("price",price)
        bundle.putInt("year",publicationYear)
        bundle.putString("image",imageURL)
        return bundle
    }
}