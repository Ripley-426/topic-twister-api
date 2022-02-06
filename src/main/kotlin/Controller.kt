package com.example
import com.example.services.WordValidator
import org.springframework.http.MediaType
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.web.bind.annotation.*
import services.LetterRandomizer
import java.net.URI

@RestController
class GreetingController {

    val dataSource = dataSource()
    val connection = dataSource.connection

    @GetMapping("/{name}")

    fun get(@PathVariable name: String) = "Hello, $name"

    @PostMapping(value = ["/add-name"], consumes = [MediaType.TEXT_PLAIN_VALUE])
    fun post(@RequestBody requestBody: String) : String {
        initDb()
        val stmt = connection.createStatement()
        stmt.executeUpdate("INSERT INTO names values('$requestBody')")
        return "Added $requestBody"
    }

    @GetMapping("/everyone")

    fun getAll() : String {
        initDb()
        val stmt = connection.createStatement()
        val rs = stmt.executeQuery("SELECT name FROM names")
        val output = ArrayList<String>()
        while (rs.next()) {
            output.add(rs.getString("name"))
        }
        val names = output.joinToString(", ")
        return "Here are the names: $names"
    }

    private fun initDb() {
        val stmt = connection.createStatement()
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS names (name text)")
    }

    private fun dataSource(): HikariDataSource {
        val config = HikariConfig()
        var dbUri = URI(System.getenv("DATABASE_URL") ?: "postgresql://localhost:5432/")
        var dbUserInfo =  dbUri.userInfo
        var username: String?; var password: String?;
        if (dbUserInfo != null) {
            username = dbUserInfo.split(":")[0]
            password = dbUserInfo.split(":")[1]
        } else {
            username = System.getenv("DATABASE_USERNAME") ?: null
            password = System.getenv("DATABASE_PASSWORD") ?: null
        }
        if (username != null) {
            config.username = username
        }
        if (password != null) {
            config.password = password
        }
        val dbUrl = "jdbc:postgresql://${dbUri.host}:${dbUri.port}${dbUri.path}"
        config.jdbcUrl = dbUrl
        return HikariDataSource(config)
    }

    @GetMapping("/letterRandomizer")

    fun get() : String {
        val randomizer = LetterRandomizer()
        return randomizer.getRandomLetter()
    }

    @GetMapping("/wordValidator")

    fun get(@RequestParam topic: String, @RequestParam word: String): Boolean{
        val validator = WordValidator()

        return validator.Validate(topic, word)
    }
}