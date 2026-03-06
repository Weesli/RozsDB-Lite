package net.weesli.rozsdblite.io;

import net.weesli.rozsdblite.model.DatabaseImpl;
import java.util.*;

public class FileManagement {

    private final Writer writer;
    private final Reader reader;

    public FileManagement(DatabaseImpl database) {
        writer = new Writer(database, this);
        reader = new Reader(database, this);
    }

    public void save() {
        writer.flush();
    }

    public Writer getWriter() {
        return writer;
    }

    public Reader getReader() {
        return reader;
    }
}
