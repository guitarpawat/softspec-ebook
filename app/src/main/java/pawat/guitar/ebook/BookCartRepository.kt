package pawat.guitar.ebook

class BookCartRepository: BookRepository() {
    override fun loadAllBooks() {
        resetTempList()
        setChanged()
        notifyObservers()
    }

    fun getTotalPrice(): Double {
        var total = 0.0;
        bookList.forEach {
            total = it.price
        }
        return total
    }

    fun checkout(): Boolean {
        if(User.withdraw(getTotalPrice())) {
            User.own.addAllBooks(bookList)
            bookList.clear()
            loadAllBooks()
            return true
        }
        else return false
    }
}