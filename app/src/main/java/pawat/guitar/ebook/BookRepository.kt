package pawat.guitar.ebook

import java.util.*
import kotlin.collections.ArrayList

abstract class BookRepository : Observable() {
    abstract fun loadAllBooks()
    abstract fun getBooks(): ArrayList<Book>



    fun search(keyword: List<String>):ArrayList<Book> {
        var search = ArrayList<Book>()
        var books = getBooks()
        for(i in 0 until books.size) {
            var valid = true
            for(j in 0 until keyword.size) {
                if(!(books[i].title+" "+books[i].publicationYear.toString()).contains(keyword[j],true)) {
                    valid = false
                    break
                }
            }
            if(valid == true) {
                search.add(books[i])
            }
        }
        return search
    }

}