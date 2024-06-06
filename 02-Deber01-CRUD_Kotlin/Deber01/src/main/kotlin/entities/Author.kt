package entities

import kotlin.collections.ArrayList

class Author (
    var name: String,
    var age: Int,
    var nationality: String,
    var literaryGenre: String,
    var booksWritten: ArrayList<Book> = ArrayList()
) {

    init {
        authorsList.add(this)
    }

    fun getAuthorInfo(): String {
        return "" +
                "Name: ${this.name}\n" +
                "Age: ${this.age}\n" +
                "Nationality: ${this.nationality}\n" +
                "Literary genre: ${this.literaryGenre}\n" +
                "Books: ${this.booksWritten.map { it.title }}"
    }

    fun updateAuthor(attribute: Int, newValue: String): String {
        /*
        * Each attribute has an assigned Int value, except "booksWritten":
        * 0 -> name
        * 1 -> age
        * 2 -> nationality
        * 3 -> literaryGenre
        *
        * Returns true if the operation was completed, false if not
        */

        when (attribute) {
            (0) -> {
                this.name = newValue
            }
            (1) -> {
                this.age = newValue.toInt()
            }
            (2) -> {
                this.nationality = newValue
            }
            (3) -> {
                this.literaryGenre = newValue
            }
            else -> {
                return "** The attribute does not exist"
            }
        }
        return "** Author updated"
    }

    companion object {
        var authorsList = ArrayList<Author>()

        fun findAuthor(authorName: String): Author? {
            return authorsList.find { it.name.lowercase().replace(" ", "") == authorName.lowercase().replace(" ", "") }
        }

        fun deleteAuthor(authorName: String): Boolean {
            val auxAuthor: Author? = findAuthor(authorName)
            if (auxAuthor != null) {
                auxAuthor.booksWritten.forEach {
                    Book.booksList.remove(it)
                }
                authorsList.remove(auxAuthor)
                return true
            }
            return false
        }
    }

}