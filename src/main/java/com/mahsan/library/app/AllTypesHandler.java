package com.mahsan.library.app;

import com.mahsan.library.cli.CliManager;
import com.mahsan.library.model.Library;
import com.mahsan.library.model.LibraryItem;
import com.mahsan.library.model.LibraryPredicates;

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

        Predicate<LibraryItem> filter = LibraryPredicates.titleEquals(title);

        ArrayList<LibraryItem> matchedItems = library.searchItems(filter);

        if (matchedItems.isEmpty())
            return "No items found with this title\n";

        for (LibraryItem item : matchedItems) {
            library.removeLibraryItem(item);
        }

        return "item(s) removed successfully!\n";
    }

    public String handleUpdateItem(){
        
    }
}
