package pawat.guitar.ebook

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView

class DownloadImageActivity(val id: Int, val iv: ImageView): AsyncTask<String, Void, Bitmap>() {

    companion object {
        val imgCache = HashMap<Int,Bitmap?>()
    }

    override fun doInBackground(vararg params: String?): Bitmap {
        val get = java.net.URL(params[0]).openStream()
        return BitmapFactory.decodeStream(get)
    }

    override fun onPostExecute(result: Bitmap?) {
        imgCache[id] = result
        iv.setImageBitmap(result)
    }
}