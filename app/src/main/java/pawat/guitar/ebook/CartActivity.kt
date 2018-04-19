package pawat.guitar.ebook

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity(), BookView {

    lateinit var presenter: BookPresenter
    lateinit var adapter: ArrayAdapter<Book>

    var state: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        presenter = BookPresenter(this,User.cart)

        fundTextView.text = String.format("Fund: %.2f %s",User.getFund(),AppInfo.CURRENCY.info)
        totalTextView.text = String.format("Total: %.2f %s",User.cart.getTotalPrice(),AppInfo.CURRENCY.info)

        cartList.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var book = cartList.adapter.getItem(position) as Book
                val myIntent = Intent(this@CartActivity, BookInfoActivity::class.java)
                myIntent.putExtra("book",book.toBundle())
                myIntent.putExtra("action","Remove from Cart")
                myIntent.putExtra("from","cart")
                startActivity(myIntent)
            }

        }

        presenter.start()



    }

    override fun setBookList(books: ArrayList<Book>) {
        adapter = BookAdapter(this,books)
        cartList.adapter = adapter
    }

    override fun onPause() {
        state = cartList.onSaveInstanceState()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        if(state != null) {
            cartList.onRestoreInstanceState(state)
        }
        state = null
    }

    fun onCheckoutButtonClick(view: View) {
        if(User.cart.getTotalPrice() == 0.0) {
            Toast.makeText(this,"Please select some books.",Toast.LENGTH_LONG).show()
        }
        else if(User.cart.checkout()) {
            Toast.makeText(this,"Checkout Successful",Toast.LENGTH_LONG).show()
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
        }
        else Toast.makeText(this,"Insufficient Fund",Toast.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
        val myIntent = Intent(this, MainActivity::class.java)
        startActivity(myIntent)
    }
}
