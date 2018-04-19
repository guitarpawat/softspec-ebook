package pawat.guitar.ebook

import android.util.Log
import java.util.*
import kotlin.collections.ArrayList

class BookPresenter(val view: BookView,
                    val repository: BookRepository): Observer {

    var filter = false

    fun start() {
        start(false)
    }

    fun start(filter: Boolean) {
        this.filter = filter
        repository.addObserver(this)
        repository.loadAllBooks()
    }

    override fun update(obj: Observable?, arg: Any?) {
        if(obj == repository) {
            view.setBookList(repository.getBooks())
            Log.wtf(obj.toString(),repository.getBooks().size.toString())
        }
    }

    fun sort(sort: String) {
        var temp : List<Book>
        if(sort == "title") {
            temp = repository.getBooks().sortedWith(Comparator { o1, o2 -> (o1.title.compareTo(o2.title)) })
        } else if(sort == "year") {
            temp = repository.getBooks().sortedWith(Comparator { o1, o2 -> (o1.publicationYear-o2.publicationYear) })
        } else return
        repository.setTempList(temp)
    }

    fun search(keyword: String) {
        repository.search(keyword.split(" "))
    }
}