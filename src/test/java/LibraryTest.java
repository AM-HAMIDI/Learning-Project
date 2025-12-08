import com.fasterxml.jackson.databind.JsonNode;
import com.mahsan.library.io.JsonHandler;
import com.mahsan.library.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {
    private static final String projectRootPath = System.getProperty("user.dir");
    private static final String configFilePath = projectRootPath + "/config/config.json";
    private static final JsonHandler configJsonHandler = new JsonHandler(configFilePath);
    private static String commandHistoryFilePath = projectRootPath + configJsonHandler.getProperty("CommandHistory");;
    private static JsonHandler commandHistoryJsonHandler = new JsonHandler(commandHistoryFilePath);;
    private static String libraryFilePath = projectRootPath + configJsonHandler.getProperty("BookListsFilePath");
    private static JsonHandler libraryJsonHandler = new JsonHandler(libraryFilePath);;
    private Library library;

    @BeforeEach
    public void setUp() {
        library = new Library();
    }

    @Test
    public void testLibraryInitialization() {
        initializeLibrary(library);
        ArrayList<Book> booksArrayList = library.getBooksArrayList();
        assertEquals(10, booksArrayList.size());
        Book book1 = new Book("1984", "George Orwell", 1949, Status.EXIST);
        assertEquals(book1, booksArrayList.get(0));
        Book book5 = new Book("To Kill a Mockingbird", "Harper Lee", 1960, Status.BORROWED);
        assertEquals(book5, booksArrayList.get(4));
        Book book10 = new Book("Animal Farm", "George Orwell", 1945, Status.BANNED);
        assertEquals(book10, booksArrayList.get(9));
    }

    private void initializeLibrary(Library library) {
        ArrayList<JsonNode> jsonNodes = libraryJsonHandler.getArrayElements();
        for (JsonNode jsonNode : jsonNodes) {
            String title = JsonHandler.getProperty(jsonNode, "title");
            String author = JsonHandler.getProperty(jsonNode, "author");
            int releaseYear = Integer.parseInt(JsonHandler.getProperty(jsonNode, "releaseYear"));
            Status status = Status.getStatus(JsonHandler.getProperty(jsonNode, "status"));
            library.insertBook(new Book(title, author, releaseYear, status));
        }
    }

    // ------------ Insert tests ------------
    @Test
    public void testLibrarySingleInsertion() {
        assertTrue(library.getBooks().isEmpty());
        Book book = new Book("book1", "author1", 1, Status.EXIST);
        library.insertBook(book);
        assertFalse(library.getBooks().isEmpty());
        assertEquals(book, library.getBooks().getHeadNode().getData());
    }

    @Test
    public void testLibraryMultipleInsertion() {
        Book book1 = new Book("book1", "author1", 1, Status.EXIST);
        Book book2 = new Book("book2", "author2", 2, Status.BANNED);
        Book book3 = new Book("book3", "author3", 3, Status.BORROWED);
        library.insertBook(book1);
        library.insertBook(book2);
        library.insertBook(book3);
        ArrayList<Book> books = library.getBooksArrayList();
        assertEquals(book1, books.get(0));
        assertEquals(book2, books.get(1));
        assertEquals(book3, books.get(2));
    }

    // ------------ Remove tests ------------
    @Test
    public void testRemoveFromEmptyLibrary() {
        assertTrue(library.getBooks().isEmpty());
        library.removeBook(new Book("book1", "author1", 1, Status.EXIST));
        assertTrue(library.getBooks().isEmpty());
    }

    @Test
    public void testRemoveNonExistingBook() {
        Book book1 = new Book("book1", "author1", 1, Status.EXIST);
        Book book2 = new Book("book2", "author2", 2, Status.BANNED);
        library.insertBook(book1);
        library.insertBook(book2);
        library.removeBook(new Book("book3", "author3", 3, Status.BORROWED));
        assertEquals(2, library.getBooks().getSize());
        assertEquals(book1, library.getBooks().getHeadNode().getData());
        assertEquals(book2, library.getBooks().getHeadNode().getNextNode().getData());
    }

    @Test
    public void testRemoveSingleBook_1() {
        Book book1 = new Book("book1", "author1", 1, Status.EXIST);
        library.insertBook(book1);
        assertEquals(1, library.getBooks().getSize());
        library.removeBook(book1);
        assertTrue(library.getBooks().isEmpty());
    }

    @Test
    public void testRemoveSingleBook_2() {
        Book book1 = new Book("book1", "author1", 1, Status.EXIST);
        Book book2 = new Book("book2", "author2", 2, Status.BANNED);
        Book book3 = new Book("book3", "author3", 3, Status.BORROWED);
        Book book4 = new Book("book1", "author1", 1, Status.EXIST);
        Book book5 = new Book("book2", "author2", 2, Status.BANNED);
        library.insertBook(book1);
        library.insertBook(book2);
        library.insertBook(book3);
        library.insertBook(book4);
        library.insertBook(book5);

        library.removeBook(new Book("book1", "author1", 1, Status.EXIST));

        ArrayList<Book> libraryBooks = library.getBooksArrayList();

        assertEquals(3, library.getBooks().getSize());
        assertEquals(3, libraryBooks.size());
        assertEquals(new Book("book2", "author2", 2, Status.BANNED), libraryBooks.get(0));
        assertEquals(new Book("book3", "author3", 3, Status.BORROWED), libraryBooks.get(1));
        assertEquals(new Book("book2", "author2", 2, Status.BANNED), libraryBooks.get(2));
    }

    @Test
    public void testRemoveMultipleElements() {
        Book book1 = new Book("book1", "author1", 1, Status.EXIST);
        Book book2 = new Book("book2", "author2", 2, Status.BANNED);
        Book book3 = new Book("book3", "author3", 3, Status.BORROWED);
        Book book4 = new Book("book1", "author1", 1, Status.EXIST);
        Book book5 = new Book("book4", "author4", 4, Status.BANNED);
        library.insertBook(book1);
        library.insertBook(book2);
        library.insertBook(book3);
        library.insertBook(book4);
        library.insertBook(book5);

        library.removeBook(new Book("book1", "author1", 1, Status.EXIST));

        ArrayList<Book> libraryBooks = library.getBooksArrayList();

        assertEquals(3, library.getBooks().getSize());
        assertEquals(3, libraryBooks.size());
        assertEquals(new Book("book2", "author2", 2, Status.BANNED), libraryBooks.get(0));
        assertEquals(new Book("book3", "author3", 3, Status.BORROWED), libraryBooks.get(1));
        assertEquals(new Book("book4", "author4", 4, Status.BANNED), libraryBooks.get(2));

        library.removeBook(new Book("book2", "author2", 2, Status.BANNED));
        libraryBooks = library.getBooksArrayList();
        assertEquals(2, libraryBooks.size());
        assertEquals(book3, libraryBooks.get(0));
        assertEquals(book5, libraryBooks.get(1));
    }

    @Test
    public void testUpdateBook() {
        Book book1 = new Book("book1", "author1", 1, Status.EXIST);
        Book book2 = new Book("book2", "author1", 12, Status.BANNED);
        Book book3 = new Book("book1", "author1", 1, Status.EXIST);
        library.insertBook(book1);
        library.insertBook(book2);
        library.insertBook(book3);

        ArrayList<Book> books = library.getBooksArrayList();
        assertEquals(book1, books.get(0));
        assertEquals(book2, books.get(1));
        assertEquals(book3, books.get(2));

        library.updateBook(new Book("book1", "author1", 1, Status.EXIST), Status.BANNED);
        Book updatedBook = new Book("book1", "author1", 1, Status.BANNED);

        ArrayList<Book> updatedBooks = library.getBooksArrayList();
        assertEquals(updatedBook, updatedBooks.get(0));
        assertEquals(updatedBook, updatedBooks.get(2));
    }

    @Test
    public void testSearchBooksByTitle() {
        Book book1 = new Book("book1", "author1", 1, Status.EXIST);
        Book book2 = new Book("book2", "author2", 2, Status.BANNED);
        Book book3 = new Book("book3", "author3", 3, Status.BORROWED);
        library.insertBook(book1);
        library.insertBook(book2);
        library.insertBook(book3);

        Book book = library.searchBooksByTitle("book1");
        assertEquals(book1, book);
    }

    @Test
    public void testSearchBookByAuthor() {
        Book book1 = new Book("book1", "author1", 1, Status.EXIST);
        Book book2 = new Book("book2", "author2", 2, Status.BANNED);
        Book book3 = new Book("book3", "author3", 3, Status.BORROWED);
        Book book4 = new Book("book4", "author2", 1, Status.EXIST);
        Book book5 = new Book("book5", "author2", 2, Status.BANNED);
        Book book6 = new Book("book6", "author5", 3, Status.BORROWED);
        library.insertBook(book1);
        library.insertBook(book2);
        library.insertBook(book3);
        library.insertBook(book4);
        library.insertBook(book5);
        library.insertBook(book6);
        ArrayList<Book> authorBooks = library.searchBooksByAuthor("author2");
        assertEquals(3, authorBooks.size());
        assertEquals(book2, authorBooks.get(0));
        assertEquals(book4, authorBooks.get(1));
        assertEquals(book5, authorBooks.get(2));
    }

    @Test
    public void testSortBooksByReleaseYear() {
        Book book1 = new Book("book1", "author1", 1, Status.EXIST);
        Book book2 = new Book("book2", "author2", 20, Status.BANNED);
        Book book3 = new Book("book3", "author3", 3, Status.BORROWED);
        Book book4 = new Book("book4", "author2", 2, Status.EXIST);
        Book book5 = new Book("book5", "author2", 10, Status.BANNED);
        library.insertBook(book1);
        library.insertBook(book2);
        library.insertBook(book3);
        library.insertBook(book4);
        library.insertBook(book5);
        ArrayList<Book> sortedBooks = library.sortBooksByReleaseYear();
        assertEquals(book1, sortedBooks.get(0));
        assertEquals(book4, sortedBooks.get(1));
        assertEquals(book3, sortedBooks.get(2));
        assertEquals(book5, sortedBooks.get(3));
        assertEquals(book2, sortedBooks.get(4));
    }
}
