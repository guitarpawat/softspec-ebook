package pawat.guitar.ebook

class MockBookRepository : BookRepository() {
    val bookList = ArrayList<Book>()
    override fun loadAllBooks() {
        bookList.clear()
        bookList.add(Book(1,"How to win BNK election",500.0))
        bookList.add(Book(2,"How to write Android App",199.0))
        bookList.add(Book(5,"Sleep today",39.99))
        setChanged()
        notifyObservers()
    }

    override fun getBooks(): ArrayList<Book> {
        return bookList
    }
}