package examproject.db;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Database {
    private static final String TABLE_NAME_REGEXP = "[A-Za-z0-9-]+";
    private static final String TABLE_FILENAME_EXTENSION = ".csv";
    private static final String DB_FOLDER = "_db";

    public static TableHandler createTable(String name, List<String> columnNames) {
        // Validate name
        validateTableName(name);

        String tableFilename = name + TABLE_FILENAME_EXTENSION;

        System.out.println("FILE SEARCH" + Files.exists(Paths.get(DB_FOLDER)) +  Paths.get(DB_FOLDER));
        if (!Files.exists(Paths.get(DB_FOLDER))) {
            System.out.println("FILE NOT HERE");
            try {
                Files.createDirectories(Paths.get(DB_FOLDER));
                System.out.println("CREATE FILE");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Create the file and insert the column values row
        try (BufferedWriter bWriter = Files.newBufferedWriter(Paths.get(DB_FOLDER, tableFilename),
                StandardCharsets.UTF_8)) {
            CSVFileHandler.writeLine(bWriter, columnNames);
            return new TableHandler(Paths.get(DB_FOLDER, tableFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static TableHandler getTable(String name) {
        // Validate name
        validateTableName(name);

        String tableFilename = name + TABLE_FILENAME_EXTENSION;

        // Get the table file
        if (Files.exists(Paths.get(DB_FOLDER, tableFilename))) {
            return new TableHandler(Paths.get(DB_FOLDER, tableFilename));
        } else {
            throw new IllegalArgumentException("No table found for the given name (" + name + ")!");
        }
    }


    /*
     *  Helpers
     */
    private static void validateTableName(String name) {
        if (!name.matches(TABLE_NAME_REGEXP)) {
            throw new IllegalArgumentException("Invalid name! The name string can contain only alphanumeric chars and dashes (-).");
        }
    }
}
