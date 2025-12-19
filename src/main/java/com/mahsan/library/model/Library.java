package com.mahsan.library.model;

import java.util.ArrayList;
import java.util.Comparator;

public class Library {
    private GenericLinkedList<LibraryItem> libraryItems;

    public Library() {
        libraryItems = new GenericLinkedList<>();
    }

    public void insertLibraryItem(LibraryItem libraryItem) {
        libraryItems.insert(libraryItem);
    }

    public void removeLibraryItem(LibraryItem libraryItem) {
        libraryItems.removeByKey(libraryItem);
    }

    public void updateLibraryItem(LibraryItem libraryItem, Status status) {
        Book oldBook = new Book(book.getTitle(), book.getAuthor(), book.getReleaseYear(), book.getStatus());
        GenericNode<Book> node = books.getHeadNode();
        while (node != null) {
            if (node.getData().equals(oldBook))
                node.getData().setStatus(status);
            node = node.getNextNode();
        }
    }

    public String getBooksStringList() {
        StringBuilder list = new StringBuilder();
        GenericNode<Book> node = books.getHeadNode();
        while (node != null) {
            list.append(node).append("\n");
            node = node.getNextNode();
        }
        return list.toString();
    }

    public ArrayList<LibraryItem> searchLibraryItemsByTitle(String title) {
        GenericNode<LibraryItem> node = libraryItems.getHeadNode();
        ArrayList<LibraryItem> targetLibraryItems = new ArrayList<>();
        while (node != null) {
            if (node.getData().getTitle().equals(title))
                targetLibraryItems.add(node.getData());
            else
                node = node.getNextNode();
        }
        return targetLibraryItems;
    }

    public ArrayList<Book> searchBooksByAuthor(String author) {
        ArrayList<Book> authorBooks = new ArrayList<>();
        GenericNode<Book> node = books.getHeadNode();
        while (node != null) {
            if (node.getData().getAuthor().equals(author))
                authorBooks.add(node.getData());
            node = node.getNextNode();
        }
        return authorBooks;
    }

    public ArrayList<Book> sortBooksByReleaseYear() {
        ArrayList<Book> sortedBooks = getBooksArrayList();
        sortedBooks.sort(Comparator.comparingInt(Book::getReleaseYear));
        return sortedBooks;
    }

    public GenericLinkedList<Book> getBooks() {
        return books;
    }

    public ArrayList<Book> getBooksArrayList() {
        ArrayList<Book> booksArrayList = new ArrayList<>();
        GenericNode<Book> node = books.getHeadNode();
        while (node != null) {
            booksArrayList.add(node.getData());
            node = node.getNextNode();
        }
        return booksArrayList;
    }
}