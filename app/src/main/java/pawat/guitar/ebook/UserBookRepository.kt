package pawat.guitar.ebook

import android.view.View

class UserBookRepository: BookRepository() {
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