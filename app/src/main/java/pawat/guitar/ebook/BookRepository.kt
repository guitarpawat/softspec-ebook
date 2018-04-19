package pawat.guitar.ebook

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

    open fun setTempList(list: List<Book>) {
        returnList = ArrayList(list)
        setChanged()
        notifyObservers()
    }

    open fun resetTempList() {
        returnList = null
    }

}