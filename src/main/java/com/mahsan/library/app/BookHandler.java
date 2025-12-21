package com.mahsan.library.app;

import com.mahsan.library.cli.CliManager;
import com.mahsan.library.model.*;

import java.util.ArrayList;

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
}
