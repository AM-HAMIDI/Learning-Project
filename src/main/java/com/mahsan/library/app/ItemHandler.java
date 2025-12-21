package com.mahsan.library.app;

import com.mahsan.library.cli.CliManager;
import com.mahsan.library.model.Library;

public abstract class ItemHandler {
    private CliManager cliManager;
    private Library library;

    public ItemHandler(CliManager cliManager , Library library){
        this.cliManager = cliManager;
        this.library = library;
    }

    public CliManager getCliManager(){
        return cliManager;
    }

    public Library getLibrary(){
        return library;
    }

    public abstract String handleInsertItem();
    public abstract String handleRemoveItem();
    public abstract String handleUpdateItem();
    public abstract String handlePrintItemsList();
    public abstract String handleSearchItems();
    public abstract String handleSortItems();
    public abstract String getFields();
}
