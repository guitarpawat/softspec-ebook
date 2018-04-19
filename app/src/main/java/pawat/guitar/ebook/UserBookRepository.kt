package pawat.guitar.ebook

import java.util.*

class UserBookRepository: BookRepository() {
    private val boughtInfo = HashMap<Int, Long>()

    override fun loadAllBooks() {
        resetTempList()
        setChanged()
        notifyObservers()
    }

    fun addAllBooks(books: ArrayList<Book>) {
        books.forEach { addBook(it) }
        loadAllBooks()
    }

    override fun addBook(book: Book) {
        boughtInfo[book.id] = Calendar.getInstance().timeInMillis
        super.addBook(book)
    }

    override fun moveTo(id: Int, dest: BookRepository) {
        boughtInfo.remove(id)
        super.moveTo(id, dest)
    }

    fun getBoughtTimeMills(id: Int): Long {
        if(boughtInfo.containsKey(id)){
            return boughtInfo[id]!!
        }
        return 0L
    }
}