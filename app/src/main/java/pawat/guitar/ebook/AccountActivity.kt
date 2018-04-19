package pawat.guitar.ebook

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_account.*
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.EditText


class AccountActivity : AppCompatActivity(), BookView {

    lateinit var presenter: BookPresenter
    lateinit var adapter: ArrayAdapter<Book>

    var state: Parcelable? = null
    var sort = "title"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        presenter = BookPresenter(this,User.own)

        fundTextView.text = String.format("Fund: %.2f %s",User.getFund(),AppInfo.CURRENCY.info)

        ownList.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var book = ownList.adapter.getItem(position) as Book
                val myIntent = Intent(this@AccountActivity, BookInfoActivity::class.java)
                myIntent.putExtra("book",book.toBundle())
                myIntent.putExtra("action","Read")
                myIntent.putExtra("from","own")
                startActivity(myIntent)
            }

        }

        searchEditText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                presenter.search(searchEditText.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })
        presenter.start()
    }

    override fun setBookList(books: ArrayList<Book>) {
        adapter = BookAdapter(this,books)
        ownList.adapter = adapter
    }

    override fun onPause() {
        state = ownList.onSaveInstanceState()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        if(state != null) {
            ownList.onRestoreInstanceState(state)
        }
        state = null
    }

    fun onAddFundButtonClick(view: View) {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Fund")

        val input = EditText(this)

        input.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        builder.setView(input)

        builder.setPositiveButton("OK", object: DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                User.deposite(input.text.toString().toDouble())
                fundTextView.text = String.format("Fund: %.2f %s",User.getFund(),AppInfo.CURRENCY.info)
            }

        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }

    fun onSortButtonClick(view: View) {
        presenter.sort(sort)
        if(sort == "title") {
            sort = "year"
            sortButton.text = "Sort Year"
        } else if(sort == "year") {
            sort = "title"
            sortButton.text = "Sort Title"
        }
    }

    override fun onBackPressed() {
        val myIntent = Intent(this, MainActivity::class.java)
        startActivity(myIntent)
    }
}
