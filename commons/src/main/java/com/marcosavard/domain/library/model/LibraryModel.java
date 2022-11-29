package com.marcosavard.domain.library.model;

import com.marcosavard.commons.lang.reflect.meta.annotations.Component;
import com.marcosavard.commons.lang.reflect.meta.annotations.Description;
import com.marcosavard.commons.lang.reflect.meta.annotations.Readonly;

import java.lang.annotation.Documented;
import java.util.List;
import java.util.Optional;

public class LibraryModel {

    @Description("A library with books and writers")
    public static class Library {
        public final @Description("of the library") String name = "Unnamed";
        public Optional<String> address;
        public @Component List<Writer> writers;
        public @Component List<Book> books;
    }

    public static class Writer {
        public final String name = "?";
        public List<Book> books;
    }

    public static class Book {
        public final String name = "Title";
        public final BookCategory category = BookCategory.MYSTERY;
        public int nbPages = 1;
        public List<Writer> authors;
    }

    public enum BookCategory {
        SCIENCE_FICTION, BIOGRAPHY, MYSTERY
    }


}




