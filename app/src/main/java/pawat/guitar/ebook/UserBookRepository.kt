package pawat.guitar.ebook

import java.time.LocalDateTime

class UserBookRepository: BookRepository() {
    //private val boughtInfo = HashMap<Int,LocalDateTime>()

    override fun loadAllBooks() {
        resetTempList()
        setChanged()
        notifyObservers()
    }

    fun addAllBooks(books: ArrayList<Book>) {
        bookList.addAll(books)
        loadAllBooks()
    }
}