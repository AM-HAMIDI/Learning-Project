package com.mahsan.library.app.handlers;

import com.mahsan.library.cli.CliManager;
import com.mahsan.library.model.base.Status;
import com.mahsan.library.model.entities.Magazine;
import com.mahsan.library.model.library.Library;
import com.mahsan.library.model.library.LibraryItem;
import com.mahsan.library.model.library.LibraryPredicates;

import java.util.ArrayList;
import java.util.function.Predicate;

public class MagazineHandler extends ItemHandler {

    public MagazineHandler(CliManager cliManager, Library library) {
        super(cliManager, library , LibraryPredicates.isMagazine() , "Magazine");
    }

    @Override
    public String handleInsertItem() {
        String title = getCliManager().getInputString("title");
        if (title.isEmpty())
            return "title is invalid!\n";

        int issueNumber = getCliManager().getInputInteger("issueNumber");
        if (issueNumber == -1)
            return "issue number is invalid!\n";

        String publisher = getCliManager().getInputString("publisher");
        if (publisher.isEmpty())
            return "publisher is invalid!\n";

        Status status = getCliManager().getInputStatus();
        if (status == null)
            return "status is invalid!\n";

        getLibrary().insertLibraryItem(new Magazine(title, issueNumber, publisher, status));
        return "Magazine added successfully!\n";
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
                int issueNumber = getCliManager().getInputInteger("issueNumber");
                if (issueNumber < 0)
                    return "issueNumber is invalid!\n";
                filter = LibraryPredicates.magazineIssueNumberIs(issueNumber);
            }
            default -> {
                return "search type is invalid!\n";
            }
        }

        filter = LibraryPredicates.and(
                LibraryPredicates.isMagazine(),
                filter);

        return search(filter, "Magazines");
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
        return "1 - title\n2 - status\n3 - publisher\n4 - issueNumber";
    }

    @Override
    public String handleSortItems() {
        return "sort operation is not determinded for this type\n";
    }
}
