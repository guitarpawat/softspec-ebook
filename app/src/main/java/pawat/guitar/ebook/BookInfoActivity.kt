package pawat.guitar.ebook

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_book_info.*


class BookInfoActivity : AppCompatActivity() {

    lateinit var book: Bundle
    lateinit var repository: BookRepository
    var id: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_info)

        if(AppInfo.MOCK_UP.info == "true") {
            repository = MockBookRepository
        } else {
            repository = OnlineBookRepository
        }

        book = intent.extras.getBundle("book")

        id = book.getInt("id")
        nameTextView.text = book.getString("title")
        priceTextView.text = book.getDouble("price").toString()+" "+AppInfo.CURRENCY.info
        yearTextView.text = book.getInt("year").toString()
        actionButton.text = intent.getStringExtra("action")

        if(DownloadImageActivity.imgCache.containsKey(id)) {
            bookView.setImageBitmap(DownloadImageActivity.imgCache[id])
        } else {
            DownloadImageActivity(id,bookView).execute(book.getString("image"))
        }
    }

    fun onActionButtonClick(view: View) {
        if(actionButton.text == "Add to Cart") {
            repository.moveTo(id,User.cart)
            actionButton.text = "Remove from Cart"
        }
        else if(actionButton.text == "Remove from Cart") {
            User.cart.moveTo(id,repository)
            actionButton.text = "Add to Cart"
        }
    }

    override fun onBackPressed() {
        val from = intent.getStringExtra("from")
        if (from == "main") {
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
        } else if(from == "cart") {
            val myIntent = Intent(this, CartActivity::class.java)
            startActivity(myIntent)
        } else if(from == "own") {
            val myIntent = Intent(this, AccountActivity::class.java)
            startActivity(myIntent)
        } else {
            super.onBackPressed()
        }
    }
}
