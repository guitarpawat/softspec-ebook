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
        var temp : List<Book>
        if(sort == "title") {
            temp = repository.getBooks().sortedWith(Comparator { o1, o2 -> (o1.title.compareTo(o2.title)) })
        } else if(sort == "year") {
            temp = repository.getBooks().sortedWith(Comparator { o1, o2 -> (o1.publicationYear-o2.publicationYear) })
        }
        else return
        repository.setTempList(temp)
    }

    fun search(keyword: String) {
        repository.search(keyword.split(" "))
    }
}