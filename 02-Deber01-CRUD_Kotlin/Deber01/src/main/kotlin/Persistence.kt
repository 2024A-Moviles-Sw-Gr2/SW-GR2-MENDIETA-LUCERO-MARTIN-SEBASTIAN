import entities.Author
import entities.Book
import java.io.File
import java.text.SimpleDateFormat

class Persistence () {

    companion object {
        private val booksFile: File = File("src/main/kotlin/files/books.txt")
        private val authorsFile: File = File("src/main/kotlin/files/authors.txt")
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")

        private fun readAuthors(): Unit {
            authorsFile.readLines().forEach {
                var lineData: Array<String> = it.split(";").toTypedArray()
                Author(
                    lineData[0],
                    lineData[1].toInt(),
                    lineData[2],
                    lineData[3]
                )
            }
        }

        private fun readBooks(): Unit {
            booksFile.readLines().forEach {
                var lineData: Array<String> = it.split(";").toTypedArray()
                var auxAuthor = Author.authorsList.find {
                        currentAuthor: Author ->
                    currentAuthor.name == lineData[1]
                }
                Book(
                    lineData[0],
                    auxAuthor!!,
                    lineData[2].toDouble(),
                    dateFormat.parse(lineData[3]),
                    lineData[4].toBoolean()
                )
            }
        }

        fun readData(): Unit {
            this.readAuthors()
            this.readBooks()
        }

        private fun writeAuthors(): Unit {
            var textData: String = ""
            Author.authorsList.forEach {
                textData += "${it.name};${it.age};${it.nationality};${it.literaryGenre}\n"
            }
            authorsFile.writeText(textData)
        }

        private fun writeBooks(): Unit {
            var textData: String = ""
            Book.booksList.forEach {
                textData += "${it.title};${it.author.name};${it.price};${dateFormat.format(it.publicationDate)};${it.discontinued}\n"
            }
            booksFile.writeText(textData)
        }

        fun writeData(): Unit {
            this.writeAuthors()
            this.writeBooks()
        }
    }

}