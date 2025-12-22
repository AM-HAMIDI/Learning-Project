package com.mahsan.library.app;

import com.mahsan.library.cli.CliManager;
import com.mahsan.library.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.function.Predicate;

public class BookHandler extends ItemHandler{
    public BookHandler(CliManager cliManager , Library library){
        super(cliManager , library , LibraryPredicates.isBook() , "Book");
    }

    @Override
    public String handleInsertItem() {
        String title = getCliManager().getInputString("title");
        if (title.isEmpty())
            return "title is invalid!\n";

        String author = getCliManager().getInputString("author");
        if (author.isEmpty())
            return "author is invalid!\n";

        int releaseYear = getCliManager().getInputInteger("releaseYear");
        if (releaseYear == -1)
            return "release year is invalid!\n";

        Status status = getCliManager().getInputStatus();
        if (status == null)
            return "status is invalid!\n";

        getLibrary().insertLibraryItem(new Book(title , author , releaseYear , status));

        return "Book added successfully!\n";
    }

    @Override
    public String handleSearchItems() {
        System.out.println(getFields());
        System.out.println("Choose a search type [1-4]:");

        int searchType;
        try {
            String input = getCliManager().getScanner().nextLine().trim();
            searchType = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return "search type is invalid!\n";
        }

        Predicate<LibraryItem> filter;
        switch (searchType) {
            case 1 -> {
                String title = getCliManager().getInputString("title");
                if (title.isEmpty()) return "title is invalid!\n";
                filter = LibraryPredicates.titleEquals(title);
            }
            case 2 -> {
                Status status = getCliManager().getInputStatus();
                if (status == null) return "status is invalid!\n";
                filter = LibraryPredicates.statusEquals(status);
            }
            case 3 -> {
                String author = getCliManager().getInputString("author");
                if (author.isEmpty()) return "author is invalid!\n";
                filter = LibraryPredicates.authorEquals(author);
            }
            case 4 -> {
                int releaseYear = getCliManager().getInputInteger("releaseYear");
                if(releaseYear < 0) return "releaseYear is invalid!\n";
                filter = LibraryPredicates.bookReleaseYearIs(releaseYear);
            }
            default -> {
                return "search type is invalid!\n";
            }
        }

        filter = LibraryPredicates.and(
                LibraryPredicates.isBook(),
                filter
        );

        return search(filter , "Books");
    }

    private String search(Predicate<LibraryItem> filter , String label) {
        ArrayList<LibraryItem> matchedItems = getLibrary().searchItems(filter);
        if (matchedItems.isEmpty())
            return "No " + label + " found matching your search\n";

        StringBuilder result = new StringBuilder("Found " + label + ":\n");
        for (LibraryItem item : matchedItems) {
            result.append(item).append("\n");
        }
        return result.toString();
    }

    public String getFields() {
        return "1 - title\n2 - status\n3 - author\n4 - releaseYear";
    }

    @Override
    public String handleSortItems(){
        var filter = LibraryPredicates.isBook();
        var comparator = LibraryComparators.bookReleaseYearAscComparator();

        ArrayList<LibraryItem> sortedItems = getLibrary().sortItems(filter , comparator);
        if (sortedItems.isEmpty())
            return "library is empty!\n";

        StringBuilder result = new StringBuilder();
        result.append("sorted books list : \n");
        for (LibraryItem libraryItem : sortedItems) {
            result.append(libraryItem).append("\n");
        }
        return result.toString();
    }
}
