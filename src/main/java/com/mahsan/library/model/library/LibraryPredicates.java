package com.mahsan.library.model.library;

import com.mahsan.library.model.base.HasAuthor;
import com.mahsan.library.model.base.HasPublisher;
import com.mahsan.library.model.base.Status;
import com.mahsan.library.model.entities.Book;
import com.mahsan.library.model.entities.Magazine;
import com.mahsan.library.model.entities.Reference;
import com.mahsan.library.model.entities.Thesis;

import java.util.Arrays;
import java.util.function.Predicate;

public final class LibraryPredicates {

    public static Predicate<LibraryItem> all() {
        return item -> true;
    }

    // Type predicates
    public static Predicate<LibraryItem> isBook() {
        return libraryItem -> libraryItem instanceof Book;
    }

    public static Predicate<LibraryItem> isMagazine() {
        return libraryItem -> libraryItem instanceof Magazine;
    }

    public static Predicate<LibraryItem> isReference() {
        return libraryItem -> libraryItem instanceof Reference;
    }

    public static Predicate<LibraryItem> isThesis() {
        return libraryItem -> libraryItem instanceof Thesis;
    }

    // Fields Predicates
    public static Predicate<LibraryItem> titleEquals(String title) {
        return libraryItem -> libraryItem.getTitle().equals(title);
    }

    public static Predicate<LibraryItem> statusEquals(Status status) {
        return libraryItem -> libraryItem.getStatus() == status;
    }

    public static Predicate<LibraryItem> authorEquals(String author) {
        return libraryItem -> libraryItem instanceof HasAuthor &&
                ((HasAuthor) libraryItem).getAuthor().equals(author);
    }

    public static Predicate<LibraryItem> publisherEquals(String publisher) {
        return libraryItem -> libraryItem instanceof HasPublisher &&
                ((HasPublisher) libraryItem).getPublisher().equals(publisher);
    }

    public static Predicate<LibraryItem> bookReleaseYearIs(int year) {
        return libraryItem -> libraryItem instanceof Book book &&
                book.getReleaseYear() == year;
    }

    public static Predicate<LibraryItem> magazineIssueNumberIs(int issueNumber) {
        return libraryItem -> libraryItem instanceof Magazine magazine &&
                magazine.getIssueNumber() == issueNumber;
    }

    public static Predicate<LibraryItem> referenceCategoryEquals(String category) {
        return libraryItem -> libraryItem instanceof Reference reference &&
                reference.getCategory().equals(category);
    }

    public static Predicate<LibraryItem> thesisDefenseYearIs(int year) {
        return libraryItem -> libraryItem instanceof Thesis thesis &&
                thesis.getDefenseYear() == year;
    }

    public static Predicate<LibraryItem> and(Predicate<LibraryItem>... predicates) {
        return Arrays.stream(predicates).reduce(Predicate::and).orElse(item -> true);
    }

    public static Predicate<LibraryItem> or(Predicate<LibraryItem>... predicates) {
        return Arrays.stream(predicates).reduce(Predicate::or).orElse(item -> false);
    }

    public static Predicate<LibraryItem> ofType(String type) {
        return switch (type.toLowerCase()) {
            case "book" -> isBook();
            case "magazine" -> isMagazine();
            case "thesis" -> isThesis();
            case "reference" -> isReference();
            default -> item -> true;
        };
    }
}
