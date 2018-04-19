package pawat.guitar.ebook

import android.support.annotation.CallSuper
import java.util.Observable
import kotlin.collections.ArrayList

abstract class BookRepository : Observable() {

    protected val bookList = ArrayList<Book>()
    protected var returnList : ArrayList<Book>? = null

    abstract fun loadAllBooks()

    open fun getBooks(): ArrayList<Book> {
        if(returnList == null) {
            returnList = ArrayList(bookList)
        }
        return returnList!!
    }

    open fun search(keyword: List<String>) {
        if(keyword.isEmpty()) {
            resetTempList()
            return
        }
        var search = ArrayList<Book>()
        for(i in 0 until bookList.size) {
            var valid = true
            for(j in 0 until keyword.size) {
                if(!(bookList[i].title+" "+bookList[i].publicationYear.toString()).contains(keyword[j],true)) {
                    valid = false
                    break
                }
            }
            if(valid) {
                search.add(bookList[i])
            }
        }
        setTempList(search)
    }

    fun setTempList(list: List<Book>) {
        returnList = ArrayList(list)
        setChanged()
        notifyObservers()
    }

    fun resetTempList() {
        returnList = null
    }

    open fun getBook(id: Int): Book? {
        val mock = Book(id,"")
        for(i in 0 until bookList.size) {
            if(mock == bookList[i]) {
                return bookList[i]
            }
        }
        return null
    }

    @CallSuper
    protected open fun addBook(book: Book) {
        bookList.add(book)
        setChanged()
        notifyObservers()
    }

    @CallSuper
    open fun moveTo(id: Int, dest: BookRepository) {
        var move = getBook(id)
        if(move == null) return
        dest.addBook(move)
        dest.resetTempList()
        bookList.remove(move)
        resetTempList()
        setChanged()
        notifyObservers()
    }

}