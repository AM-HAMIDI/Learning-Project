package com.mahsan.library.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

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
        GenericNode<LibraryItem> node = libraryItems.getHeadNode();
        while (node != null) {
            if (node.getData().equals(libraryItem))
                node.getData().setStatus(status);
            node = node.getNextNode();
        }
    }

    public void borrowLibraryItem(LibraryItem libraryItem , LocalDate returnDate){
        GenericNode<LibraryItem> node = libraryItems.getHeadNode();
        while (node != null) {
            if (node.getData().equals(libraryItem)){
                node.getData().setStatus(Status.BORROWED);
                node.getData().setReturnDate(returnDate);
            }
            node = node.getNextNode();
        }
    }

    public void returnLibraryItem(LibraryItem libraryItem){
        GenericNode<LibraryItem> node = libraryItems.getHeadNode();
        while (node != null) {
            if (node.getData().equals(libraryItem)){
                node.getData().setStatus(Status.EXIST);
                node.getData().setReturnDate(null);
            }
            node = node.getNextNode();
        }
    }

    public String getItemsStringList(Predicate<LibraryItem> filter) {
        StringBuilder itemsStringBuilder = new StringBuilder();
        GenericNode<LibraryItem> node = libraryItems.getHeadNode();
        while (node != null) {
            LibraryItem item = node.getData();
            if (filter.test(item))
                itemsStringBuilder.append(item).append("\n");
            node = node.getNextNode();
        }

        return itemsStringBuilder.toString();
    }

    public ArrayList<LibraryItem> searchItems(Predicate<LibraryItem> filter) {
        GenericNode<LibraryItem> node = libraryItems.getHeadNode();
        ArrayList<LibraryItem> targetLibraryItems = new ArrayList<>();
        while (node != null) {
            LibraryItem item = node.getData();
            if (filter.test(item))
                targetLibraryItems.add(item);
            node = node.getNextNode();
        }
        return targetLibraryItems;
    }

    public ArrayList<LibraryItem> sortItems(Predicate<LibraryItem> filter, Comparator<LibraryItem> comparator) {
        ArrayList<LibraryItem> items = searchItems(filter);
        items.sort(comparator);
        return items;
    }

    public GenericLinkedList<LibraryItem> getItems(Predicate<LibraryItem> filter) {
        GenericLinkedList<LibraryItem> items = new GenericLinkedList<>();
        GenericNode<LibraryItem> node = libraryItems.getHeadNode();
        while (node != null) {
            LibraryItem item = node.getData();
            if (filter.test(item))
                items.insert(item);
            node = node.getNextNode();
        }
        return items;
    }

    // ------- Phase1-compatible Book API wrappers -------

    public void insertBook(Book book) {
        insertLibraryItem(book);
    }

    public void removeBook(Book book) {
        removeLibraryItem(book);
    }

    public void updateBook(Book book, Status status) {
        updateLibraryItem(book, status);
    }

    public GenericLinkedList<Book> getBooks() {
        GenericLinkedList<Book> books = new GenericLinkedList<>();
        GenericNode<LibraryItem> node = libraryItems.getHeadNode();
        while (node != null) {
            LibraryItem item = node.getData();
            if (item instanceof Book book) {
                books.insert(book);
            }
            node = node.getNextNode();
        }
        return books;
    }

    public ArrayList<Book> getBooksArrayList() {
        ArrayList<Book> books = new ArrayList<>();
        GenericNode<LibraryItem> node = libraryItems.getHeadNode();
        while (node != null) {
            LibraryItem item = node.getData();
            if (item instanceof Book book) {
                books.add(book);
            }
            node = node.getNextNode();
        }
        return books;
    }

    public Book searchBooksByTitle(String title) {
        ArrayList<LibraryItem> items = searchItems(
                LibraryPredicates.and(LibraryPredicates.isBook(), LibraryPredicates.titleEquals(title)));
        for (LibraryItem item : items) {
            if (item instanceof Book book)
                return book;
        }
        return null;
    }

    public ArrayList<Book> searchBooksByAuthor(String author) {
        ArrayList<Book> result = new ArrayList<>();
        ArrayList<LibraryItem> items = searchItems(
                LibraryPredicates.and(LibraryPredicates.isBook(), LibraryPredicates.authorEquals(author)));
        for (LibraryItem item : items) {
            if (item instanceof Book book)
                result.add(book);
        }
        return result;
    }

    public ArrayList<Book> sortBooksByReleaseYear() {
        ArrayList<Book> result = new ArrayList<>();
        ArrayList<LibraryItem> items = sortItems(LibraryPredicates.isBook(),
                LibraryComparators.bookReleaseYearAscComparator());
        for (LibraryItem item : items) {
            if (item instanceof Book book)
                result.add(book);
        }
        return result;
    }
}