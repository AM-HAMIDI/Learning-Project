import java.util.ArrayList;
import java.util.Comparator;


public class Library {
    private ArrayList<Book> books;

    public Library(){
        books = new ArrayList<>();
    }

    public void insertBook(Book book){
        books.add(book);
    }

    public void removeBook(Book book){
        books.remove(book);
    }

    public void updateBook(Book book , Status status){
        Book oldBook = new Book(book.getTitle() , book.getAuthor() , book.getReleaseYear() , book.getStatus());
        GenericNode<Book> node = books.getHeadNode();
        while(node != null){
            if(node.getData().equals(oldBook)) node.getData().setStatus(status);
            node = node.getNextNode();
        }
    }

    public void printBooksList(){
        books.forEach(System.out::println);
    }

    public Book searchBooksByTitle(String title){
        for(Book book : books){
            if(book.getTitle().equals(title))
                return book;
        }
        return null;
    }

    public ArrayList<Book> searchBooksByAuthor(String author){
        ArrayList<Book> authorBooks = new ArrayList<>();

        for(Book book : books){
            if(book.getAuthor().equals(author))
                authorBooks.add(book);
        }

        return authorBooks;
    }

    public ArrayList<Book> sortBooksByReleaseYear(){
        ArrayList<Book> sortedBooks = new ArrayList<>(books);
        sortedBooks.sort(Comparator.comparingInt(Book::getReleaseYear));
        return sortedBooks;
    }
}
