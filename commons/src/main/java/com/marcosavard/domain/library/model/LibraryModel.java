package com.marcosavard.domain.library.model;

import com.marcosavard.commons.meta.annotations.Component;
import com.marcosavard.commons.meta.annotations.Description;
import com.marcosavard.commons.meta.annotations.NotNull;
import com.marcosavard.commons.meta.annotations.Readonly;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class LibraryModel {

    public class Library {
        public @Readonly String name;
        public Optional<String> address;
        public @Component List<Writer> writers;
        public @Component List<Book> books;
    }

    public class Writer {
        public @Readonly String name;
        public List<Book> books;
    }

    public class Book {
        public @Readonly String name;
        public @Readonly BookCategory category;
        public int nbPages;
        public List<Writer> authors;
    }

    public enum BookCategory {
        SCIENCE_FICTION, BIOGRAPHY, MYSTERY
    }


}
