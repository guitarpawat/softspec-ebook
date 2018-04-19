package pawat.guitar.ebook

import android.os.AsyncTask
import org.json.JSONArray
import java.net.URL



object OnlineBookRepository: BookRepository() {

    var loaded = false

    override fun loadAllBooks() {
        resetTempList()
        if(!loaded) {
            bookList.clear()
            BookLoader().execute()
            loaded = true
        } else {
            setChanged()
            notifyObservers()
        }
    }

    private class BookLoader: AsyncTask<Void,Void,String>() {
        override fun doInBackground(vararg params: Void?): String {
            val url = URL(AppInfo.BOOKS_URL.info)
            return url.readText()
        }

        override fun onPostExecute(result: String?) {
            val jsonArr = JSONArray(result)
            (0..(jsonArr.length()-1)).forEach {
                var jo = jsonArr.getJSONObject(it)
                bookList.add(Book(jo.getInt("id"), jo.getString("title"), jo.getDouble("price"),
                        jo.getInt("pub_year"), jo.getString("img_url")))
            }
            setChanged()
            notifyObservers()
        }

    }
}