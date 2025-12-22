package com.mahsan.library.app;

import com.mahsan.library.cli.CliManager;
import com.mahsan.library.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.function.Predicate;

public class ThesisHandler extends ItemHandler {

    public ThesisHandler(CliManager cliManager, Library library) {
        super(cliManager, library , LibraryPredicates.isThesis() , "Thesis");
    }

    @Override
    public String handleInsertItem() {
        String title = getCliManager().getInputString("title");
        if (title.isEmpty())
            return "title is invalid!\n";

        String author = getCliManager().getInputString("author");
        if (author.isEmpty())
            return "author is invalid!\n";

        int defenseYear = getCliManager().getInputInteger("defenseYear");
        if (defenseYear == -1)
            return "defense year is invalid!\n";

        Status status = getCliManager().getInputStatus();
        if (status == null)
            return "status is invalid!\n";

        getLibrary().insertLibraryItem(new Thesis(title, author, defenseYear, status));
        return "Thesis added successfully!\n";
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
                String author = getCliManager().getInputString("author");
                if (author.isEmpty())
                    return "author is invalid!\n";
                filter = LibraryPredicates.authorEquals(author);
            }
            case 4 -> {
                int defenseYear = getCliManager().getInputInteger("defenseYear");
                if (defenseYear < 0)
                    return "defenseYear is invalid!\n";
                filter = LibraryPredicates.thesisDefenseYearIs(defenseYear);
            }
            default -> {
                return "search type is invalid!\n";
            }
        }

        filter = LibraryPredicates.and(
                LibraryPredicates.isThesis(),
                filter);

        return search(filter, "Theses");
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
        return "1 - title\n2 - status\n3 - author\n4 - defenseYear";
    }

    @Override
    public String handleSortItems() {
        return "sort operation is not determinded for this type\n";
    }
}
