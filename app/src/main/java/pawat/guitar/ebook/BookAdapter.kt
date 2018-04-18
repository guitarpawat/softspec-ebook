package pawat.guitar.ebook

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.view.LayoutInflater
import android.widget.ImageView
import kotlinx.android.synthetic.main.book_layout.view.*

class BookAdapter(context: Context?, books: ArrayList<Book>?, resource: Int = R.layout.book_layout) : ArrayAdapter<Book>(context, resource, books) {

    val activity = context
    val books = books
    val layout = resource

    companion object {
        val imgCache = HashMap<Int,Bitmap?>()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(activity)
        val rowView = inflater.inflate(R.layout.book_layout, null, true)
        var book = books!![position]
        if(book.imageURL.isNotEmpty()) {
            if(imgCache.containsKey(book.id)) {
                rowView.imageView.setImageBitmap(imgCache[book.id])
            }else {
                DownloadImageActivity(book.id, rowView.imageView).execute(book.imageURL)
            }
        }
        rowView.titleTextView.text = book.title
        rowView.priceTextView.text = String.format("%.2f %s",book.price,AppInfo.CURRENCY.info)
        return rowView
    }

    inner class DownloadImageActivity(val id: Int, val iv: ImageView): AsyncTask<String, Void, Bitmap>() {

        override fun doInBackground(vararg params: String?): Bitmap {
            val get = java.net.URL(params[0]).openStream()
            return BitmapFactory.decodeStream(get)
        }

        override fun onPostExecute(result: Bitmap?) {
            imgCache.put(id,result)
            iv.setImageBitmap(result)
        }

    }
}