package pawat.guitar.ebook

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BookView {

    lateinit var presenter: BookPresenter
    lateinit var adapter: ArrayAdapter<Book>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(AppInfo.MOCK_UP.info == "true") {
            presenter = BookPresenter(this, MockBookRepository())
        } else {
            TODO("Phase 1")
        }

        presenter.start()

    }

    override fun setBookList(books: ArrayList<Book>) {
        adapter = BookAdapter(this,books)
        bookList.adapter = adapter
    }
}
