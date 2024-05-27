package entities

import java.util.Date

class Book (
    var title: String,
    var author: Author,
    var price: Double,
    var publicationDate: Date,
    var discontinued: Boolean
) {

    init {
        booksList.add(this)
    }

    fun getBookInfo(): String {
        return "" +
                "Title: ${this.title} \n" +
                "Author: ${this.author.name} \n" +
                "Price: ${this.price} \n" +
                "Publication Date: ${this.publicationDate} \n" +
                "Discontinued: ${this.discontinued} \n"
    }

    fun updateBook(attribute: Int, newValue: Any): String {
        /*
        * Each attribute has an assigned Int value:
        * 0 -> title
        * 1 -> author
        * 2 -> price
        * 3 -> publicationDate
        * 4 -> discontinued
        *
        * Returns true if the operation was completed, false if not
        */

        when (attribute) {
            (0) -> {
                this.title = newValue as String
            }
            (1) -> {
                val auxAuthor: Author? = Author.findAuthor(newValue.toString())
                if (auxAuthor != null) {
                    this.author = auxAuthor
                } else {
                    return "Author not found\n"
                }
            }
            (2) -> {
                this.price = newValue as Double
            }
            (3) -> {
                this.publicationDate = newValue as Date
            }
            (4) -> {
                this.discontinued = newValue as Boolean
            }
            else -> {
                return "The attribute does not exist"
            }
        }
        return ""
    }

    companion object {
        var booksList = ArrayList<Book>()

        fun findBook(bookTitle: String): Book? {
            return booksList.find { it.title.lowercase().replace(" ", "") == bookTitle.lowercase().replace(" ", "") }
        }

        fun deleteBook(bookTitle: String): Boolean {
            val auxBook: Book? = findBook(bookTitle)
            if (auxBook != null) {
                val auxAuthor: Author = Author.authorsList.find { it.booksWritten.contains(auxBook) }!!
                auxAuthor.booksWritten.remove(auxBook)
                booksList.remove(auxBook)
                return true
            }
            return false
        }
    }

}