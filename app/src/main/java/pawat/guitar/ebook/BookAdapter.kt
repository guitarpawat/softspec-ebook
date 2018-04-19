package pawat.guitar.ebook

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.book_layout.view.*

class BookAdapter(context: Context?, books: ArrayList<Book>?, resource: Int = R.layout.book_layout) : ArrayAdapter<Book>(context, resource, books) {

    val activity = context
    val books = books
    val layout = resource



    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(activity)
        val rowView = inflater.inflate(R.layout.book_layout, null, true)
        var book = books!![position]
        if(book.imageURL.isNotEmpty()) {
            if(DownloadImageActivity.imgCache.containsKey(book.id)) {
                rowView.imageView.setImageBitmap(DownloadImageActivity.imgCache[book.id])
            }else {
                DownloadImageActivity(book.id, rowView.imageView).execute(book.imageURL)
            }
        }
        rowView.titleTextView.text = book.title
        rowView.priceTextView.text = String.format("%.2f %s",book.price,AppInfo.CURRENCY.info)
        return rowView
    }

}