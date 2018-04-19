package pawat.guitar.ebook

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.View


class MainActivity : AppCompatActivity(), BookView {

    lateinit var presenter: BookPresenter
    lateinit var adapter: ArrayAdapter<Book>
    var sort = "title"

    var state: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchEditText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                presenter.search(searchEditText.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })

        if(AppInfo.MOCK_UP.info == "true") {
            presenter = BookPresenter(this, MockBookRepository())
        } else {
            presenter = BookPresenter(this, OnlineBookRepository())
        }

        presenter.start()

    }

    override fun setBookList(books: ArrayList<Book>) {
        adapter = BookAdapter(this,books)
        bookList.adapter = adapter
    }

    override fun onPause() {
        state = bookList.onSaveInstanceState()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        if(state != null) {
            bookList.onRestoreInstanceState(state)
        }
        state = null
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
}
