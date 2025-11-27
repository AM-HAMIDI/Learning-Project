package Test;

import Modules.Book;
import Modules.GenericLinkedList;
import Modules.Library;
import Common.Status;

import java.util.ArrayList;

public class Test {
    public static void main(String [] args) {
        Library library = new Library();

        // Insert some sample books
        library.insertBook(new Book("1984", "George Orwell", 1949, Status.EXIST));
        library.insertBook(new Book("The Hobbit", "J.R.R. Tolkien", 1937, Status.EXIST));
        library.insertBook(new Book("Harry Potter", "J.K. Rowling", 1997, Status.BANNED));
        library.insertBook(new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, Status.EXIST));
        library.insertBook(new Book("To Kill a Mockingbird", "Harper Lee", 1960, Status.BORROWED));
        library.insertBook(new Book("Brave New World", "Aldous Huxley", 1932, Status.EXIST));
        library.insertBook(new Book("Fahrenheit 451", "Ray Bradbury", 1953, Status.EXIST));
        library.insertBook(new Book("Dune", "Frank Herbert", 1965, Status.BORROWED));
        library.insertBook(new Book("The Catcher in the Rye", "J.D. Salinger", 1951, Status.EXIST));
        library.insertBook(new Book("Animal Farm", "George Orwell", 1945, Status.BANNED));

        // Print books in library
        System.out.println("Library initial state : ");
        library.printBooksList();

        // Search book by title
        Book harryPotterBook = library.searchBooksByTitle("Harry Potter");
        System.out.println("\nSearch book by title :");
        if (harryPotterBook != null) System.out.println("Found: " + harryPotterBook);
        else System.out.println("Book not found!");

        // Search book by author
        ArrayList<Book> orwellBooks = library.searchBooksByAuthor("George Orwell");
        System.out.println("\nSearch books by author :");
        if(!orwellBooks.isEmpty()) orwellBooks.forEach(System.out::println);
        else System.out.println("Author not found!");

        // Update book
        library.updateBook(
                new Book("1984", "George Orwell", 1949, Status.EXIST),
                Status.BORROWED
        );
        System.out.println("\nLibrary after update : ");
        library.printBooksList();

        // Remove book
        library.removeBook(
                new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, Status.EXIST)
        );
        System.out.println("\nLibrary after remove : ");
        library.printBooksList();

        // Sort books
        ArrayList<Book> sortedBooks = library.sortBooksByReleaseYear();
        System.out.println("\nLibrary after sort : ");
        sortedBooks.forEach(System.out::println);

        // Generic linked list insertion :
        GenericLinkedList<Integer> linkedList = new GenericLinkedList<>();
        // Test insertion
        System.out.println("Insertion test : ");
        linkedList.insert(1);
        linkedList.insert(-1);
        linkedList.insert(100);
        linkedList.insert(1);
        linkedList.insert(50);
        linkedList.insert(99);
        linkedList.insert(-1);
        linkedList.printList();

        // Test removal :
        System.out.println("Removal test : ");
        linkedList.removeByKey(-1);
        linkedList.printList();
    }
}
