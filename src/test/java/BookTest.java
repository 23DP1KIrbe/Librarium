import com.example.vadimaprojekts.exceptions.UserExistsException;
import com.example.vadimaprojekts.module.Book;
import com.example.vadimaprojekts.module.User;
import com.example.vadimaprojekts.service.APIService;
import com.example.vadimaprojekts.service.Session;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.vadimaprojekts.service.Session.session;
import static org.junit.jupiter.api.Assertions.*;

public class BookTest {
    private APIService apiService;
    private final String TEST_BOOKS_FILE = "./booksTest.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private Book book;

    @BeforeEach
    public void setUp() throws IOException {
        apiService = new APIService();


        book = new Book(
                "Test Book",
                "Desc",
                Arrays.asList("Author"),
                Arrays.asList("1234567890123"),
                Arrays.asList("Category"),
                "http://example.com/image.jpg",
                "en",
                "1",
                new ArrayList<>(),
                new ArrayList<>()
        );

        try (FileWriter writer = new FileWriter(TEST_BOOKS_FILE)) {
            gson.toJson(Collections.singletonList(book), writer);
        }
        Session.getInstance().setBook(book);
    }

    @AfterEach
    public void cleanUp() throws IOException {
        try (FileWriter writer = new FileWriter(TEST_BOOKS_FILE)) {
            gson.toJson(new ArrayList<>(), writer);
        }
        Session.getInstance().setBook(null);
    }

    @Test
    public void testAddToTotalReaders_addsUserIfNotPresent() throws IOException {
        apiService.addTotalReadersToBook("user123");
        List<Book> books = getBooksFromFile();
        assertTrue(books.get(0).getTotalReaders().contains("user123"));
    }

    @Test
    public void testAddToTotalReaders_removesUserIfPresent() throws IOException {
        apiService.addTotalReadersToBook("user123");
        apiService.addTotalReadersToBook("user123");
        List<Book> books = getBooksFromFile();
        assertFalse(books.get(0).getTotalReaders().contains("user123"));
    }

    @Test
    public void testAddToTotalBuyers_addsUserIfNotPresent() throws IOException {
        apiService.addTotalBuyersToBook("buyer123");
        List<Book> books = getBooksFromFile();
        assertTrue(books.get(0).getTotalBuyers().contains("buyer123"));
    }

    @Test
    public void testAddToTotalBuyers_removesUserIfPresent() throws IOException {
        apiService.addTotalBuyersToBook("buyer123");
        apiService.addTotalBuyersToBook("buyer123");
        List<Book> books = getBooksFromFile();
        assertFalse(books.get(0).getTotalBuyers().contains("buyer123"));
    }

    @Test
    public void testAddTotalReaders_doesNothingIfNoSessionBook() throws IOException {
        Session.getInstance().setBook(null);
        apiService.addTotalReadersToBook("userX");
        List<Book> books = getBooksFromFile();
        assertEquals(0, books.get(0).getTotalReaders().size());
    }

    @Test
    public void testAddTotalBuyers_doesNothingIfNoSessionBook() throws IOException {
        Session.getInstance().setBook(null);
        apiService.addTotalBuyersToBook("userX");
        List<Book> books = getBooksFromFile();
        assertEquals(0, books.get(0).getTotalBuyers().size());
    }

    private List<Book> getBooksFromFile() throws IOException {
        try (FileReader reader = new FileReader(TEST_BOOKS_FILE)) {
            List<Book> books = gson.fromJson(reader, new TypeToken<List<Book>>() {}.getType());
            return (books != null) ? books : new ArrayList<>();
        }
    }

    @Test
    public void bookSessionIsSetUp() throws UserExistsException {
        session.setBook(new Book("Title", "Desc", Arrays.asList("Author"), Arrays.asList("1234567890123"), Arrays.asList("Category"), "http://example.com/image.jpg", "en", "1", new ArrayList<>(), new ArrayList<>()));
        assertTrue(session.getBook().getTitle().equals("Title"));
    }
}
