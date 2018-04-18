package pawat.guitar.ebook

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.book_layout.view.*


class BookAdapter(context: Context?, books: ArrayList<Book>?, resource: Int = R.layout.book_layout) : ArrayAdapter<Book>(context, resource, books) {

    val activity = context
    val books = books
    val layout = resource

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val rowView = inflater.inflate(R.layout.book_layout, null, true)
        var book = books!![position]
        if(book.imageURL.isNotEmpty()) {
            TODO("Phase 1")
        }
        rowView.titleTextView.text = book.title
        rowView.priceTextView.text = String.format("%.2f %s",book.price,AppInfo.CURRENCY.info)
        return rowView
    }
}