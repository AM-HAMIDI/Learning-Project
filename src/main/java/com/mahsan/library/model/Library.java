package com.mahsan.library.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

public class Library {
    private GenericLinkedList<LibraryItem> libraryItems;

    public Library() {
        libraryItems = new GenericLinkedList<>();
    }

    public void insertLibraryItem(LibraryItem libraryItem) {
        libraryItems.insert(libraryItem);
    }

    public void removeLibraryItem(LibraryItem libraryItem) {
        libraryItems.removeByKey(libraryItem);
    }

    public void updateLibraryItem(LibraryItem libraryItem, Status status) {
        GenericNode<LibraryItem> node = libraryItems.getHeadNode();
        while (node != null) {
            if (node.getData().equals(libraryItem))
                node.getData().setStatus(status);
            node = node.getNextNode();
        }
    }

    public String getItemsStringList(Predicate<LibraryItem> filter) {
        StringBuilder itemsStringBuilder = new StringBuilder();
        GenericNode<LibraryItem> node = libraryItems.getHeadNode();
        while (node != null) {
            LibraryItem item = node.getData();
            if (filter.test(item))
                itemsStringBuilder.append(item).append("\n");
            node = node.getNextNode();
        }

        return itemsStringBuilder.toString();
    }

    public ArrayList<LibraryItem> searchItems(Predicate<LibraryItem> filter){
        GenericNode<LibraryItem> node = libraryItems.getHeadNode();
        ArrayList<LibraryItem> targetLibraryItems = new ArrayList<>();
        while (node != null) {
            LibraryItem item = node.getData();
            if(filter.test(item))
                targetLibraryItems.add(item);
            node = node.getNextNode();
        }
        return targetLibraryItems;
    }

    public ArrayList<LibraryItem> sortItems(Predicate<LibraryItem> filter , Comparator<LibraryItem> comparator){
        ArrayList<LibraryItem> items = searchItems(filter);
        items.sort(comparator);
        return items;
    }

    public GenericLinkedList<LibraryItem> getItems(Predicate<LibraryItem> filter){
        GenericLinkedList<LibraryItem> items = new GenericLinkedList<>();
        GenericNode<LibraryItem> node = libraryItems.getHeadNode();
        while(node != null){
            LibraryItem item = node.getData();
            if(filter.test(item))
                items.insert(item);
            node = node.getNextNode();
        }
        return items;
    }
}