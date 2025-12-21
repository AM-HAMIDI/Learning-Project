package com.mahsan.library.app;

import com.mahsan.library.cli.CliManager;
import com.mahsan.library.model.*;

import java.util.ArrayList;
import java.util.function.Predicate;

public class ReferenceHandler extends ItemHandler {

    public ReferenceHandler(CliManager cliManager, Library library) {
        super(cliManager, library);
    }

    @Override
    public String handleInsertItem() {
        String title = getCliManager().getInputString("title");
        if (title.isEmpty())
            return "title is invalid!\n";

        String category = getCliManager().getInputString("category");
        if (category.isEmpty())
            return "category is invalid!\n";

        String publisher = getCliManager().getInputString("publisher");
        if (publisher.isEmpty())
            return "publisher is invalid!\n";

        Status status = getCliManager().getInputStatus();
        if (status == null)
            return "status is invalid!\n";

        getLibrary().insertLibraryItem(new Reference(title, category, publisher, status));
        return "Reference added successfully!\n";
    }

    @Override
    public String handleRemoveItem() {
        String title = getCliManager().getInputString("title");
        if (title.isEmpty())
            return "title is invalid!\n";

        var filter = LibraryPredicates.and(
                LibraryPredicates.isReference(),
                LibraryPredicates.titleEquals(title));

        ArrayList<LibraryItem> matchedItems = getLibrary().searchItems(filter);
        if (matchedItems.isEmpty())
            return "This reference doesn't exists\n";

        getLibrary().removeLibraryItem(matchedItems.get(0));
        return "Reference removed successfully!\n";
    }

    @Override
    public String handleUpdateItem() {
        String title = getCliManager().getInputString("title");
        if (title.isEmpty())
            return "title is invalid!\n";

        Status status = getCliManager().getInputStatus();
        if (status == null)
            return "status is invalid!\n";

        var filter = LibraryPredicates.and(
                LibraryPredicates.isReference(),
                LibraryPredicates.titleEquals(title));

        ArrayList<LibraryItem> matchedItems = getLibrary().searchItems(filter);
        if (matchedItems.isEmpty())
            return "reference not found!\n";

        getLibrary().updateLibraryItem(matchedItems.get(0), status);
        return "Reference updated successfully!\n";
    }

    @Override
    public String handlePrintItemsList() {
        var filter = LibraryPredicates.isReference();
        return "References list :\n" + getLibrary().getItemsStringList(filter) + "\n";
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
                if (title.isEmpty())
                    return "title is invalid!\n";
                filter = LibraryPredicates.titleEquals(title);
            }
            case 2 -> {
                Status status = getCliManager().getInputStatus();
                if (status == null)
                    return "status is invalid!\n";
                filter = LibraryPredicates.statusEquals(status);
            }
            case 3 -> {
                String publisher = getCliManager().getInputString("publisher");
                if (publisher.isEmpty())
                    return "publisher is invalid!\n";
                filter = LibraryPredicates.publisherEquals(publisher);
            }
            case 4 -> {
                String category = getCliManager().getInputString("category");
                if (category.isEmpty())
                    return "category is invalid!\n";
                filter = LibraryPredicates.referenceCategoryEquals(category);
            }
            default -> {
                return "search type is invalid!\n";
            }
        }

        filter = LibraryPredicates.and(
                LibraryPredicates.isReference(),
                filter);

        return search(filter, "References");
    }

    private String search(Predicate<LibraryItem> filter, String label) {
        ArrayList<LibraryItem> matchedItems = getLibrary().searchItems(filter);
        if (matchedItems.isEmpty())
            return "No " + label + " found matching your search\n";

        StringBuilder result = new StringBuilder("Found " + label + ":\n");
        for (LibraryItem item : matchedItems) {
            result.append(item).append("\n");
        }
        return result.toString();
    }

    @Override
    public String getFields() {
        return "1 - title\n2 - status\n3 - publisher\n4 - category";
    }

    @Override
    public String handleSortItems() {
        return "sort operation is not determinded for this type\n";
    }
}
