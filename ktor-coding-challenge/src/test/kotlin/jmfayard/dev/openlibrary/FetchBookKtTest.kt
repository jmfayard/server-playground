package jmfayard.dev.openlibrary

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import jmfayard.dev.openlibrary.domain.Book
import jmfayard.dev.util.jsonMockResponse
import okhttp3.mockwebserver.MockWebServer

class FetchBookKtTest : StringSpec({


    "Fetching a correct ISBN should work" {
        val server = MockWebServer()
        server.start()
        server.enqueue(jsonMockResponse("json/open-library-book.json"))
        server.enqueue(jsonMockResponse("json/open-library-author.json"))

        val expected = Book(
            -1, "George B. McClellan and Civil War history", "0873386035", "Thomas J. Rowland",
            null, "https://covers.openlibrary.org/b/id/663570-M.jpg", "in the shadow of Grant and Sherman", "Kent State University Press"
        )
        fetchBookInfos("0873386035", baseUrl = "http://localhost:${server.port}") shouldBe expected

        server.shutdown()
    }

})