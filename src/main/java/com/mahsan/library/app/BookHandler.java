package com.mahsan.library.app;

import com.mahsan.library.cli.CliManager;
import com.mahsan.library.model.*;

import java.util.ArrayList;
import java.util.function.Predicate;

public class BookHandler extends ItemHandler{
    public BookHandler(CliManager cliManager , Library library){
        super(cliManager , library);
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
    public String handleRemoveItem() {
        String title = getCliManager().getInputString("title");
        if (title.isEmpty())
            return "title is invalid!\n";

        var filter = LibraryPredicates.and(
                LibraryPredicates.isBook(),
                LibraryPredicates.titleEquals(title)
        );

        ArrayList<LibraryItem> matchedItems = getLibrary().searchItems(filter);

        if (matchedItems.isEmpty())
            return "This book doesn't exists\n";

        getLibrary().removeLibraryItem(matchedItems.get(0));
        return "Book removed successfully!\n";
    }

    @Override
    public String handleUpdateItem(){
        String title = getCliManager().getInputString("title");
        if (title.isEmpty())
            return "title is invalid!\n";

        Status status = getCliManager().getInputStatus();
        if (status == null)
            return "status is invalid!\n";

        var filter = LibraryPredicates.and(
                LibraryPredicates.isBook(),
                LibraryPredicates.titleEquals(title)
        );

        ArrayList<LibraryItem> matchedItems = getLibrary().searchItems(filter);
        if (matchedItems.isEmpty())
            return "book not found!\n";

        getLibrary().updateLibraryItem(matchedItems.get(0) , status);
        return "Book updated successfully!\n";
    }

    @Override
    public String handlePrintItemsList() {
        var filter = LibraryPredicates.isBook();
        return "Books list :\n" + getLibrary().getItemsStringList(filter) + "\n";
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

        return search(filter);
    }

    private String search(Predicate<LibraryItem> filter) {
        ArrayList<LibraryItem> matchedItems = getLibrary().searchItems(filter);
        if (matchedItems.isEmpty()) return "No Books found matching your search\n";

        StringBuilder result = new StringBuilder("Found Books:\n");
        for (LibraryItem item : matchedItems) {
            result.append(item).append("\n");
        }
        return result.toString();
    }

    public String getFields() {
        return "1 - title\n2 - status\n3 - author\n4 - releaseYear";
    }


}
