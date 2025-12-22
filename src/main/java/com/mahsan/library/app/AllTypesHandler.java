package com.mahsan.library.app;

import com.mahsan.library.cli.CliManager;
import com.mahsan.library.model.*;

import java.util.ArrayList;
import java.util.function.Predicate;

public class AllTypesHandler {

    private final CliManager cliManager;
    private final Library library;

    public AllTypesHandler(CliManager cliManager, Library library) {
        this.cliManager = cliManager;
        this.library = library;
    }

    public String handleRemoveItem() {
        String title = cliManager.getInputString("title");
        if (title.isEmpty())
            return "title is invalid!\n";

        var filter = LibraryPredicates.titleEquals(title);

        ArrayList<LibraryItem> matchedItems = library.searchItems(filter);

        if (matchedItems.isEmpty())
            return "No items found with this title\n";

        for (LibraryItem item : matchedItems) {
            library.removeLibraryItem(item);
        }

        return "item(s) removed successfully!\n";
    }

    public String handlePrintItemsList(){
        var filter = LibraryPredicates.all();
        return "All items list :\n" + library.getItemsStringList(filter) + "\n";
    }

    public String handleSearchItems() {
        System.out.println(getFields());
        System.out.println("Choose a search type [1-2]:");

        int searchType;
        try {
            String input = cliManager.getScanner().nextLine().trim();
            searchType = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return "search type is invalid!\n";
        }

        Predicate<LibraryItem> filter;
        switch (searchType) {
            case 1 -> {
                String title = cliManager.getInputString("title");
                if (title.isEmpty()) return "title is invalid!\n";
                filter = LibraryPredicates.titleEquals(title);
            }
            case 2 -> {
                Status status = cliManager.getInputStatus();
                if (status == null) return "status is invalid!\n";
                filter = LibraryPredicates.statusEquals(status);
            }
            default -> {
                return "search type is invalid!\n";
            }
        }

        return search(filter);
    }

    private String search(Predicate<LibraryItem> filter) {
        ArrayList<LibraryItem> matchedItems = library.searchItems(filter);
        if (matchedItems.isEmpty()) return "No items found matching your search\n";

        StringBuilder result = new StringBuilder("Found items:\n");
        for (LibraryItem item : matchedItems) {
            result.append(item).append("\n");
        }
        return result.toString();
    }

    public String getFields() {
        return "1 - title\n2 - status\n";
    }

    public String handlePrintBorrowedItems() {
        var filter = LibraryPredicates.and(
                LibraryPredicates.all(),
                LibraryPredicates.statusEquals(Status.BORROWED)
        );

        return "Borrowed items list :\n" + library.getItemsStringList(filter) + "\n";
    }

}
