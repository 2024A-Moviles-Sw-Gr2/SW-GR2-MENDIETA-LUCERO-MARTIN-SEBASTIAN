import entities.Author
import entities.Book
import java.text.SimpleDateFormat

fun main() {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")

    val persistence: Persistence = Persistence()
    persistence.readData()



    persistence.writeData()
}