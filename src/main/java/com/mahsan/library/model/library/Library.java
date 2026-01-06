package com.mahsan.library.model.library;

import com.mahsan.library.model.base.Status;
import com.mahsan.library.model.base.Container;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

public class Library {
    private final Container container;

    public Library() {
        container = new Container();
        container.registerSortableType(LibraryItemType.BOOK, LibraryComparators.bookReleaseYearAscComparator());
        container.registerSortableType(LibraryItemType.THESIS, LibraryComparators.thesisDefenseYearAscComparator());
        container.registerNonSortableType(LibraryItemType.MAGAZINE);
        container.registerNonSortableType(LibraryItemType.REFERENCE);
    }

    public void insertLibraryItem(LibraryItem libraryItem) {
        container.insert(libraryItem);
    }

    public void removeLibraryItem(LibraryItem libraryItem) {
        container.remove(libraryItem);
    }

    public void updateLibraryItem(LibraryItem libraryItem, Status status) {
        LibraryItem item = container.getElement(libraryItem);
        if (item != null)
            item.setStatus(status);
    }

    public void borrowLibraryItem(LibraryItem libraryItem, LocalDate returnDate) {
        LibraryItem item = container.getElement(libraryItem);
        if (item != null) {
            item.setStatus(Status.BORROWED);
            item.setReturnDate(returnDate);
        }
    }

    public void returnLibraryItem(LibraryItem libraryItem) {
        LibraryItem item = container.getElement(libraryItem);
        if (item != null) {
            item.setStatus(Status.EXIST);
            item.setReturnDate(null);
        }
    }

    public String getItemsStringList(Predicate<LibraryItem> filter) {
        StringBuilder itemsStringBuilder = new StringBuilder();
        for (LibraryItem item : container.getElements()) {
            if (filter.test(item)) {
                itemsStringBuilder.append(item).append("\n");
            }
        }
        return itemsStringBuilder.toString();
    }

    public ArrayList<LibraryItem> searchItems(Predicate<LibraryItem> filter) {
        ArrayList<LibraryItem> result = new ArrayList<>();
        for (LibraryItem item : container.getElements()) {
            if (filter.test(item)) {
                result.add(item);
            }
        }
        return result;
    }

    public ArrayList<LibraryItem> searchItems(LibraryItemType itemType, Predicate<LibraryItem> filter) {
        ArrayList<LibraryItem> result = new ArrayList<>();
        for (LibraryItem item : container.getElements(itemType)) {
            if (filter.test(item)) {
                result.add(item);
            }
        }
        return result;
    }

    public ArrayList<LibraryItem> sortItems(LibraryItemType itemType) {
        Collection<LibraryItem> items = container.getElements(itemType);
        ArrayList<LibraryItem> result = new ArrayList<>();
        if (items instanceof SortedSet<LibraryItem> sortedSet) {
            result.addAll(sortedSet);
        } else {
            result.addAll(items);
        }
        return result;
    }
}