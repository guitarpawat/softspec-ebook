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

    fun sort(sort: String) {
        if(sort == "title") {
            repository.getBooks().sortWith(Comparator { o1, o2 -> (o1.title.compareTo(o2.title)) })
        } else if(sort == "year") {
            repository.getBooks().sortWith(Comparator { o1, o2 -> (o1.publicationYear-o2.publicationYear) })
        }
        view.setBookList(repository.getBooks())
    }

    fun search(keyword: String) {
        view.setBookList(repository.search(keyword.split(" ")))
    }
}