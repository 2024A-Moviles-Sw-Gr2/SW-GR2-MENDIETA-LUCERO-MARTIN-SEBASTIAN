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
                        println(bookFound.getBookInfo())
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
                        println(authorFound.getAuthorInfo())
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