package pawat.guitar.ebook

object MockBookRepository : BookRepository() {

    override fun loadAllBooks() {
        bookList.clear()
        bookList.add(Book(1,"How to win BNK election",500.0,44))
        bookList.add(Book(2,"How to write Android App",199.0,2000))
        bookList.add(Book(5,"Sleep today",39.99,9))
        setChanged()
        notifyObservers()
    }

}