package com.mahsan.library.app.handlers;

import com.mahsan.library.cli.CliManager;
import com.mahsan.library.model.library.Library;
import com.mahsan.library.model.library.LibraryItem;
import com.mahsan.library.model.library.LibraryPredicates;
import com.mahsan.library.model.base.Status;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.function.Predicate;

public abstract class ItemHandler {
    private CliManager cliManager;
    private Library library;
    private Predicate<LibraryItem> filter;
    private String itemTypeStr;

    public ItemHandler(CliManager cliManager , Library library , Predicate<LibraryItem> filter , String itemTypeStr){
        this.cliManager = cliManager;
        this.library = library;
        this.filter = filter;
        this.itemTypeStr = itemTypeStr;
    }

    public CliManager getCliManager(){
        return cliManager;
    }

    public Library getLibrary(){
        return library;
    }

    public String handleRemoveItem() {
        String title = getCliManager().getInputString("title");
        if (title.isEmpty())
            return "title is invalid!\n";

        var filter = LibraryPredicates.and(
                this.filter,
                LibraryPredicates.titleEquals(title));

        ArrayList<LibraryItem> matchedItems = getLibrary().searchItems(filter);
        if (matchedItems.isEmpty())
            return "This " + itemTypeStr +" doesn't exists\n";

        getLibrary().removeLibraryItem(matchedItems.get(0));
        return itemTypeStr + " removed successfully!\n";
    }

    public String handleUpdateItem() {
        String title = getCliManager().getInputString("title");
        if (title.isEmpty())
            return "title is invalid!\n";

        Status status = getCliManager().getInputStatus();
        if (status == null)
            return "status is invalid!\n";

        var filter = LibraryPredicates.and(
                this.filter,
                LibraryPredicates.titleEquals(title));

        ArrayList<LibraryItem> matchedItems = getLibrary().searchItems(filter);
        if (matchedItems.isEmpty())
            return itemTypeStr + " not found!\n";

        getLibrary().updateLibraryItem(matchedItems.get(0), status);
        return itemTypeStr + " updated successfully!\n";
    }

    public String handlePrintItemsList() {
        return itemTypeStr + " list :\n" + getLibrary().getItemsStringList(filter) + "\n";
    }

    public String handleBorrowItems(){
        String title = getCliManager().getInputString("title");
        if (title.isEmpty())
            return "title is invalid!\n";

        var filter = LibraryPredicates.and(
                this.filter,
                LibraryPredicates.titleEquals(title)
        );

        ArrayList<LibraryItem> matchedItems = getLibrary().searchItems(filter);
        if (matchedItems.isEmpty())
            return itemTypeStr + " not found!\n";

        if(matchedItems.get(0).getStatus() != Status.EXIST)
            return itemTypeStr + " does not exist!\n";

        String returnDateStr = getCliManager().getInputString("returnDate (in this format : YYYY/MM/DD) : ");

        try {
            LocalDate returnDate = LocalDate.parse(returnDateStr , LibraryItem.dateTimeFormatter);
            getLibrary().borrowLibraryItem(matchedItems.get(0) , returnDate);
            return itemTypeStr + " borrowed successfully!\n";
        } catch (DateTimeParseException e) {
            return "Invalid date format!";
        }
    }

    public String handleReturnItems(){
        String title = getCliManager().getInputString("title");
        if (title.isEmpty())
            return "title is invalid!\n";

        var filter = LibraryPredicates.and(
                this.filter,
                LibraryPredicates.titleEquals(title)
        );

        ArrayList<LibraryItem> matchedItems = getLibrary().searchItems(filter);
        if (matchedItems.isEmpty())
            return itemTypeStr + " not found!\n";

        if(matchedItems.get(0).getStatus() != Status.BORROWED)
            return itemTypeStr + " does not borrowed!\n";

        getLibrary().returnLibraryItem(matchedItems.get(0));
        return itemTypeStr + " returned successfully!\n";
    }

    public String handlePrintBorrowedItems() {
        var filter = LibraryPredicates.and(
                this.filter,
                LibraryPredicates.statusEquals(Status.BORROWED)
        );

        return "Borrowed " + itemTypeStr +" list :\n" + getLibrary().getItemsStringList(filter) + "\n";
    }

    public abstract String handleInsertItem();
    public abstract String handleSearchItems();
    public abstract String handleSortItems();
    public abstract String getFields();
}
