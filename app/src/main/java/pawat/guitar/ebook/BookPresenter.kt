package pawat.guitar.ebook

import java.util.*

class BookPresenter(val view: BookView,
                    val repository: BookRepository): Observer {
    fun start() {
        repository.addObserver(this)
        repository.loadAllBooks()
    }

    override fun update(obj: Observable?, arg: Any?) {
        if(obj == repository) {
            view.setBookList(repository.getBooks())
        }
    }
}