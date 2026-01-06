package com.mahsan.library.model.library;

import com.mahsan.library.model.entities.Book;
import com.mahsan.library.model.entities.Thesis;

import java.util.Comparator;

public final class LibraryComparators {

    public static Comparator<LibraryItem> bookReleaseYearAscComparator() {
        return (item1, item2) -> {
            if (item1 instanceof Book b1 && item2 instanceof Book b2) {
                return Integer.compare(b1.getReleaseYear(), b2.getReleaseYear());
            } else if (item1 instanceof Book) {
                return -1;
            } else if (item2 instanceof Book) {
                return 1;
            } else {
                return 0;
            }
        };
    }

    public static Comparator<LibraryItem> bookReleaseYearDescComparator() {
        return bookReleaseYearAscComparator().reversed();
    }

    public static Comparator<LibraryItem> thesisDefenseYearAscComparator() {
        return (item1, item2) -> {
            if (item1 instanceof Thesis t1 && item2 instanceof Thesis t2) {
                return Integer.compare(t1.getDefenseYear(), t2.getDefenseYear());
            } else if (item1 instanceof Thesis) {
                return -1;
            } else if (item2 instanceof Thesis) {
                return 1;
            } else {
                return 0;
            }
        };
    }

    public static Comparator<LibraryItem> thesisDefenseYearDescComparator() {
        return thesisDefenseYearAscComparator().reversed();
    }
}
