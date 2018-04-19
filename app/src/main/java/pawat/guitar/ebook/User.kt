package pawat.guitar.ebook


object User {
    private var fund = 0.0
    val cart = BookCartRepository()
    val own = UserBookRepository()

    fun deposite(amount: Double): Boolean {
        if(amount <= 0) return false
        fund += amount
        return true
    }

    fun withdraw(amount: Double): Boolean {
        if(amount <= 0 || amount > fund) return false
        fund -= amount
        return true
    }

    fun getFund(): Double {
        return fund
    }
}
