import entities.Author
import entities.Book

fun main() {
    Persistence.readData()
    while (true) {
        println(
            "================= AUTHOR - BOOKS PROGRAM =================\n" +
            "1. Create book\n" +
            "2. Create author\n" +
            "3. Select book\n" +
            "4. Select author\n\n" +
            "0. Exit\n"
        )
        var userSelection: String? = readlnOrNull()
        when (userSelection?.toInt()) {
            (1) -> {
                println(
                    "Enter the book fields separated by semicolons\n" +
                    "title;authorName;price;publicationDate(yyyy-MM-dd);discontinued?"
                )
                var bookFields: String? = readlnOrNull()
                if (bookFields != null) {
                    var bookFieldsArr: Array<String> = bookFields.split(";").toTypedArray()
                    var bookAuthor: Author? = Author.findAuthor(bookFieldsArr[1])
                    if (bookAuthor != null) {
                        Book(
                            bookFieldsArr[0],
                            bookAuthor,
                            bookFieldsArr[2].toDouble(),
                            Persistence.dateFormat.parse(bookFieldsArr[3]),
                            bookFieldsArr[4].toBoolean()
                        )
                        println("** Book created\n")
                    } else {
                        println("** Author not found\n")
                    }
                } else {
                    println("** Enter book fields\n")
                }
            }
            (2) -> {
                println(
                    "Enter the author fields separated by semicolons\n" +
                    "name;age;nationality;literaryGenre"
                )
                var authorFields: String? = readlnOrNull()
                if (authorFields != null) {
                    var authorFieldsArr: Array<String> = authorFields.split(";").toTypedArray()
                    Author(
                        authorFieldsArr[0],
                        authorFieldsArr[1].toInt(),
                        authorFieldsArr[2],
                        authorFieldsArr[3]
                    )
                    println("** Author created\n")
                } else {
                    println("** Enter author fields\n")
                }
            }
            (3) -> {
                println("Enter the book name")
                var bookTitle: String? = readlnOrNull()
                if (bookTitle != null) {
                    var bookFound: Book? = Book.findBook(bookTitle)
                    if (bookFound != null) {
                        println(
                            "*************************************\n" +
                            bookFound.getBookInfo() +
                            "*************************************\n\n" +
                            "1. Delete book\n" +
                            "2. Update book\n\n" +
                            "Press any other key to go back to menu"
                        )
                        var bookUserSelection: String? = readlnOrNull()
                        when (bookUserSelection?.toInt()) {
                            (1) -> {
                                if (Book.deleteBook(bookFound.title)) {
                                    println("** Book deleted")
                                } else {
                                    println("** Book couldn't be deleted")
                                }
                            }
                            (2) -> {
                                println(
                                    "Enter the attribute number and its new value separated with a semicolon\n" +
                                            "* 0 -> title\n" +
                                            "* 1 -> author\n" +
                                            "* 2 -> price\n" +
                                            "* 3 -> publicationDate (yyyy-MM-dd)\n" +
                                            "* 4 -> discontinued"
                                )
                                var bookAttributeUpdate: String? = readlnOrNull()
                                if (bookAttributeUpdate != null) {
                                    var attributeToUpdateArr: Array<String> = bookAttributeUpdate.split(";").toTypedArray()
                                    println(bookFound.updateBook(attributeToUpdateArr[0].toInt(), attributeToUpdateArr[1]))
                                    println(
                                        "*************************************\n" +
                                        bookFound.getBookInfo() +
                                        "*************************************\n"
                                    )
                                }
                            }
                            else -> {}
                        }
                    } else {
                        println("** Book not found\n")
                    }
                } else {
                    println("** Enter a book title\n")
                }
            }
            (4) -> {
                println("Enter the author name")
                var authorName: String? = readlnOrNull()
                if (authorName != null) {
                    var authorFound: Author? = Author.findAuthor(authorName)
                    if (authorFound != null) {
                        println(
                            "*************************************\n" +
                            authorFound.getAuthorInfo() + "\n" +
                            "*************************************\n\n" +
                            "1. Delete author\n" +
                            "2. Update author\n\n" +
                            "Press any other key to go back to menu"
                        )
                        var authorUserSelection: String? = readlnOrNull()
                        when (authorUserSelection?.toInt()) {
                            (1) -> {
                                if (Author.deleteAuthor(authorFound.name)) {
                                    println("** Author deleted")
                                } else {
                                    println("** Author couldn't be deleted")
                                }
                            }
                            (2) -> {
                                println(
                                    "Enter the attribute number and its new value separated with a semicolon\n" +
                                            "* 0 -> name\n" +
                                            "* 1 -> age\n" +
                                            "* 2 -> nationality\n" +
                                            "* 3 -> literaryGenre"
                                )
                                var authorAttributeUpdate: String? = readlnOrNull()
                                if (authorAttributeUpdate != null) {
                                    var attributeToUpdateArr: Array<String> = authorAttributeUpdate.split(";").toTypedArray()
                                    println(authorFound.updateAuthor(attributeToUpdateArr[0].toInt(), attributeToUpdateArr[1]))
                                    println(
                                        "*************************************\n" +
                                        authorFound.getAuthorInfo() + "\n" +
                                        "*************************************\n"
                                    )
                                }
                            }
                            else -> {}
                        }
                    } else {
                        println("** Author not found\n")
                    }
                } else {
                    println("** Enter an author name\n")
                }
            }
            (0) -> {
                break
            }
        }
    }
    Persistence.writeData()
}