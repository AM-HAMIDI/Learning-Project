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
        ArrayList<Book> booksArrayList = new ArrayList<>();
        for (LibraryItem item : library.sortItems(LibraryItemType.BOOK)) {
            if (item instanceof Book book)
                booksArrayList.add(book);
        }
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
            library.insertLibraryItem(new Book(title, author, releaseYear, status));
        }
    }

    // ------------ Insert tests ------------
    @Test
    public void testLibrarySingleInsertion() {
        ArrayList<Book> books = new ArrayList<>();
        for (LibraryItem item : library.sortItems(LibraryItemType.BOOK)) {
            if (item instanceof Book book)
                books.add(book);
        }
        assertTrue(books.isEmpty());
        Book book = new Book("book1", "author1", 1, Status.EXIST);
        library.insertLibraryItem(book);
        books.clear();
        for (LibraryItem item : library.sortItems(LibraryItemType.BOOK)) {
            if (item instanceof Book b)
                books.add(b);
        }
        assertFalse(books.isEmpty());
        assertEquals(book, books.get(0));
    }

    @Test
    public void testLibraryMultipleInsertion() {
        Book book1 = new Book("book1", "author1", 1, Status.EXIST);
        Book book2 = new Book("book2", "author2", 2, Status.BANNED);
        Book book3 = new Book("book3", "author3", 3, Status.BORROWED);
        library.insertLibraryItem(book1);
        library.insertLibraryItem(book2);
        library.insertLibraryItem(book3);
        ArrayList<Book> books = new ArrayList<>();
        for (LibraryItem item : library.sortItems(LibraryItemType.BOOK)) {
            if (item instanceof Book book)
                books.add(book);
        }
        assertEquals(book1, books.get(0));
        assertEquals(book2, books.get(1));
        assertEquals(book3, books.get(2));
    }

    // ------------ Remove tests ------------
    @Test
    public void testRemoveFromEmptyLibrary() {
        ArrayList<Book> books = new ArrayList<>();
        for (LibraryItem item : library.sortItems(LibraryItemType.BOOK)) {
            if (item instanceof Book book)
                books.add(book);
        }
        assertTrue(books.isEmpty());
        library.removeLibraryItem(new Book("book1", "author1", 1, Status.EXIST));
        books.clear();
        for (LibraryItem item : library.sortItems(LibraryItemType.BOOK)) {
            if (item instanceof Book book)
                books.add(book);
        }
        assertTrue(books.isEmpty());
    }

    @Test
    public void testRemoveNonExistingBook() {
        Book book1 = new Book("book1", "author1", 1, Status.EXIST);
        Book book2 = new Book("book2", "author2", 2, Status.BANNED);
        library.insertLibraryItem(book1);
        library.insertLibraryItem(book2);
        library.removeLibraryItem(new Book("book3", "author3", 3, Status.BORROWED));
        ArrayList<Book> books = new ArrayList<>();
        for (LibraryItem item : library.sortItems(LibraryItemType.BOOK)) {
            if (item instanceof Book book)
                books.add(book);
        }
        assertEquals(2, books.size());
        assertEquals(book1, books.get(0));
        assertEquals(book2, books.get(1));
    }

    @Test
    public void testRemoveSingleBook_1() {
        Book book1 = new Book("book1", "author1", 1, Status.EXIST);
        library.insertLibraryItem(book1);
        ArrayList<Book> books = new ArrayList<>();
        for (LibraryItem item : library.sortItems(LibraryItemType.BOOK)) {
            if (item instanceof Book book)
                books.add(book);
        }
        assertEquals(1, books.size());
        library.removeLibraryItem(book1);
        books.clear();
        for (LibraryItem item : library.sortItems(LibraryItemType.BOOK)) {
            if (item instanceof Book book)
                books.add(book);
        }
        assertTrue(books.isEmpty());
    }

    @Test
    public void testRemoveSingleBook_2() {
        Book book1 = new Book("book1", "author1", 1, Status.EXIST);
        Book book2 = new Book("book2", "author2", 2, Status.BANNED);
        Book book3 = new Book("book3", "author3", 3, Status.BORROWED);
        Book book4 = new Book("book1", "author1", 1, Status.EXIST);
        Book book5 = new Book("book2", "author2", 2, Status.BANNED);
        library.insertLibraryItem(book1);
        library.insertLibraryItem(book2);
        library.insertLibraryItem(book3);
        library.insertLibraryItem(book4);
        library.insertLibraryItem(book5);
        library.removeLibraryItem(new Book("book1", "author1", 1, Status.EXIST));
        ArrayList<Book> books = new ArrayList<>();
        for (LibraryItem item : library.sortItems(LibraryItemType.BOOK)) {
            if (item instanceof Book book)
                books.add(book);
        }
        assertEquals(3, books.size());
        assertEquals(new Book("book2", "author2", 2, Status.BANNED), books.get(0));
        assertEquals(new Book("book3", "author3", 3, Status.BORROWED), books.get(1));
        assertEquals(new Book("book2", "author2", 2, Status.BANNED), books.get(2));
    }

    @Test
    public void testRemoveMultipleElements() {
        Book book1 = new Book("book1", "author1", 1, Status.EXIST);
        Book book2 = new Book("book2", "author2", 2, Status.BANNED);
        Book book3 = new Book("book3", "author3", 3, Status.BORROWED);
        Book book4 = new Book("book1", "author1", 1, Status.EXIST);
        Book book5 = new Book("book4", "author4", 4, Status.BANNED);
        library.insertLibraryItem(book1);
        library.insertLibraryItem(book2);
        library.insertLibraryItem(book3);
        library.insertLibraryItem(book4);
        library.insertLibraryItem(book5);
        library.removeLibraryItem(new Book("book1", "author1", 1, Status.EXIST));
        ArrayList<Book> books = new ArrayList<>();
        for (LibraryItem item : library.sortItems(LibraryItemType.BOOK)) {
            if (item instanceof Book book)
                books.add(book);
        }
        assertEquals(3, books.size());
        assertEquals(new Book("book2", "author2", 2, Status.BANNED), books.get(0));
        assertEquals(new Book("book3", "author3", 3, Status.BORROWED), books.get(1));
        assertEquals(new Book("book4", "author4", 4, Status.BANNED), books.get(2));
        library.removeLibraryItem(new Book("book2", "author2", 2, Status.BANNED));
        books.clear();
        for (LibraryItem item : library.sortItems(LibraryItemType.BOOK)) {
            if (item instanceof Book book)
                books.add(book);
        }
        assertEquals(2, books.size());
        assertEquals(book3, books.get(0));
        assertEquals(book5, books.get(1));
    }

    @Test
    public void testUpdateBook() {
        Book book1 = new Book("book1", "author1", 1, Status.EXIST);
        Book book2 = new Book("book2", "author1", 12, Status.BANNED);
        Book book3 = new Book("book1", "author1", 1, Status.EXIST);
        library.insertLibraryItem(book1);
        library.insertLibraryItem(book2);
        library.insertLibraryItem(book3);
        ArrayList<Book> books = new ArrayList<>();
        for (LibraryItem item : library.sortItems(LibraryItemType.BOOK)) {
            if (item instanceof Book book)
                books.add(book);
        }
        assertEquals(book1, books.get(0));
        assertEquals(book2, books.get(1));
        assertEquals(book3, books.get(2));
        library.updateLibraryItem(new Book("book1", "author1", 1, Status.EXIST), Status.BANNED);
        Book updatedBook = new Book("book1", "author1", 1, Status.BANNED);
        books.clear();
        for (LibraryItem item : library.sortItems(LibraryItemType.BOOK)) {
            if (item instanceof Book book)
                books.add(book);
        }
        assertEquals(updatedBook, books.get(0));
        assertEquals(updatedBook, books.get(2));
    }

    @Test
    public void testSearchBooksByTitle() {
        Book book1 = new Book("book1", "author1", 1, Status.EXIST);
        Book book2 = new Book("book2", "author2", 2, Status.BANNED);
        Book book3 = new Book("book3", "author3", 3, Status.BORROWED);
        library.insertLibraryItem(book1);
        library.insertLibraryItem(book2);
        library.insertLibraryItem(book3);
        ArrayList<LibraryItem> found = library.searchItems(LibraryItemType.BOOK,
                LibraryPredicates.titleEquals("book1"));
        Book book = found.isEmpty() ? null : (Book) found.get(0);
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
        library.insertLibraryItem(book1);
        library.insertLibraryItem(book2);
        library.insertLibraryItem(book3);
        library.insertLibraryItem(book4);
        library.insertLibraryItem(book5);
        library.insertLibraryItem(book6);
        ArrayList<LibraryItem> found = library.searchItems(LibraryItemType.BOOK,
                LibraryPredicates.authorEquals("author2"));
        ArrayList<Book> authorBooks = new ArrayList<>();
        for (LibraryItem item : found) {
            if (item instanceof Book book)
                authorBooks.add(book);
        }
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
        library.insertLibraryItem(book1);
        library.insertLibraryItem(book2);
        library.insertLibraryItem(book3);
        library.insertLibraryItem(book4);
        library.insertLibraryItem(book5);
        ArrayList<Book> sortedBooks = new ArrayList<>();
        for (LibraryItem item : library.sortItems(LibraryItemType.BOOK)) {
            if (item instanceof Book book)
                sortedBooks.add(book);
        }
        assertEquals(book1, sortedBooks.get(0));
        assertEquals(book4, sortedBooks.get(1));
        assertEquals(book3, sortedBooks.get(2));
        assertEquals(book5, sortedBooks.get(3));
        assertEquals(book2, sortedBooks.get(4));
    }
}
