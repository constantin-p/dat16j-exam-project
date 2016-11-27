package examproject.db;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class TableHandler {

    private String fileName;
    private FileTime lastModifiedTime;
    private List<String> cachedColumnValues;

    public TableHandler(String fileName) {
        this.fileName = fileName;

        // Read the column values and store them for data validation
        this.syncTableColumns();
    }

    public List<HashMap<String, String>> getAll() {
        ArrayList<HashMap<String, String>> rows = new ArrayList<HashMap<String, String>>();

        // Update the cached column values if the file was modified outside our code
        if (!this.isSyncedWithFile()) {
            this.syncTableColumns();
        }

        try (Stream<String> stream = Files.lines(Paths.get(this.fileName), StandardCharsets.UTF_8)) {

            stream.forEach(line -> {
                try {
                    List<String> values = CSVFileHandler.parseLine(new StringReader(line));
                    HashMap<String, String> rowValueMap = new HashMap<String, String>();

                    //  Check if the row contains the right amount of values
                    if (this.cachedColumnValues.size() != values.size()) {
                        throw new Exception("Unexpected row values count (" + values.size() + " instead of " + this.cachedColumnValues.size() + ")");
                    }

                    // Check the value map against the table's column names
                    for (int i = 0; i < this.cachedColumnValues.size(); i++) {
                        rowValueMap.put(this.cachedColumnValues.get(i), values.get(i));
                    }

                    rows.add(rowValueMap);
                } catch (Exception e) {
                    e.getMessage();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        return rows;
    }

    public void insert(HashMap<String, String> rowValuesMap) {
        List<String> rowValues = new ArrayList<String>();

        // Update the cached column values if the file was modified outside our code
        if (!this.isSyncedWithFile()) {
            this.syncTableColumns();
        }

        // Check the value map against the table's column names
        for (String column : this.cachedColumnValues) {
            if (!rowValuesMap.containsKey(column)) {
                throw new IllegalArgumentException("No value defined for the [" + column + "] column!");
            }
            rowValues.add(rowValuesMap.get(column));
        }

        // Insert the row into the tables file
        try (BufferedWriter bWriter = Files.newBufferedWriter(Paths.get(this.fileName),
                StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            CSVFileHandler.writeLine(bWriter, rowValues);
            this.registerLocalTableOperation();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
     *  Helpers
     */
    private boolean isSyncedWithFile() {
        try {
            FileTime currentModifiedTime = Files.getLastModifiedTime(Paths.get(this.fileName));
            return currentModifiedTime.equals(this.lastModifiedTime);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void syncTableColumns() {
        this.cachedColumnValues = this.getLine(0);
        this.registerLocalTableOperation();
    }

    private void registerLocalTableOperation() {
        try {
            this.lastModifiedTime = Files.getLastModifiedTime(Paths.get(this.fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> getLine(int index) {
        try (Stream<String> stream = Files.lines(Paths.get(this.fileName), StandardCharsets.UTF_8)) {
            String line = stream
                    .skip(index)
                    .findFirst()
                    .get();
            return CSVFileHandler.parseLine(new StringReader(line));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
