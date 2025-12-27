package com.mahsan.library.model;

import java.util.*;

public class Container {
    private final HashMap<LibraryItemType, Collection<LibraryItem>> containersMap = new HashMap<>();

    public void registerNonSortableType(LibraryItemType type) {
        containersMap.put(type, new HashSet<>());
    }

    public void registerSortableType(LibraryItemType type, Comparator<LibraryItem> comparator) {
        containersMap.put(type, new TreeSet<>(comparator));
    }

    private Set<LibraryItemType> getRegisteredTypes() {
        return containersMap.keySet();
    }

    public void insert(LibraryItem item) {
        LibraryItemType type = item.getItemType();
        Collection<LibraryItem> container = containersMap.get(type);
        if (container != null)
            container.add(item);
    }

    public void remove(LibraryItem item) {
        LibraryItemType type = item.getItemType();
        Collection<LibraryItem> container = containersMap.get(type);
        if (container != null)
            container.remove(item);
    }

    public boolean contains(LibraryItem item) {
        LibraryItemType type = item.getItemType();
        Collection<LibraryItem> container = containersMap.get(type);
        return container != null && container.contains(item);
    }

    public int getSize(LibraryItemType itemType) {
        Collection<LibraryItem> container = containersMap.get(itemType);
        return container != null ? container.size() : 0;
    }

    public int getSize() {
        int size = 0;
        for (LibraryItemType itemType : getRegisteredTypes()) {
            size += getSize(itemType);
        }
        return size;
    }

    public ArrayList<LibraryItem> getElements(LibraryItemType type) {
        Collection<LibraryItem> container = containersMap.get(type);
        if (container == null)
            return new ArrayList<>();
        return new ArrayList<>(container);
    }

    public ArrayList<LibraryItem> getElements() {
        ArrayList<LibraryItem> elements = new ArrayList<>();
        for (LibraryItemType itemType : getRegisteredTypes()) {
            elements.addAll(new ArrayList<>(containersMap.get(itemType)));
        }
        return elements;
    }

    public LibraryItem getElement(LibraryItem item) {
        LibraryItemType type = item.getItemType();
        Collection<LibraryItem> container = containersMap.get(type);
        switch (container) {
            case null -> {
                return null;
            }
            case TreeSet<LibraryItem> treeSet -> {
                LibraryItem found = treeSet.ceiling(item);
                if (found != null && found.equals(item)) {
                    return found;
                }
                return null;
            }
            case HashSet<LibraryItem> hashSet -> {
                if (hashSet.contains(item)) {
                    for (LibraryItem li : hashSet) {
                        if (li.equals(item))
                            return li;
                    }
                }
                return null;
            }
            default -> {
                for (LibraryItem li : container) {
                    if (li.equals(item))
                        return li;
                }
                return null;
            }
        }
    }
}