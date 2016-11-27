package examproject.db;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TableHandler {

    private Path filePath;
    private FileTime lastModifiedTime;
    private List<String> cachedColumnValues;

    public TableHandler(Path filePath) {
        this.filePath = filePath;

        // Read the column values and store them for data validation
        this.syncTableColumns();
    }

    public HashMap<String, String> get(HashMap<String, String> query) {
        HashMap<String, String> row = new HashMap<String, String>();

        // Update the cached column values if the file was modified outside our code
        if (!this.isSyncedWithFile()) {
            this.syncTableColumns();
        }

        // Check the query for invalid keys
        for (Map.Entry<String, String> querySet : query.entrySet()) {
            if (!this.cachedColumnValues.contains(querySet.getKey())) {
                throw new IllegalArgumentException("Invalid query key [" + querySet.getKey()
                        + "]! No column with the given name.");
            }
        }

        try (Stream<String> stream = Files.lines(this.filePath, StandardCharsets.UTF_8)) {
            List<HashMap<String, String>> lines = stream
                .map( line -> {
                    return this.mapRowValues(line);
                })
                .filter( rowValueMap -> {
                    if (rowValueMap != null) {
                        for (Map.Entry<String, String> querySet : query.entrySet()) {
                            if (!rowValueMap.get(querySet.getKey()).equals(querySet.getValue())) {
                                return false;
                            }
                        }
                        return true;
                    }
                    return false;
                }).collect(Collectors.toList());

            return lines.size() >= 1
                    ? lines.get(0)
                    : null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<HashMap<String, String>> getAll() {
        ArrayList<HashMap<String, String>> rows = new ArrayList<HashMap<String, String>>();

        // Update the cached column values if the file was modified outside our code
        if (!this.isSyncedWithFile()) {
            this.syncTableColumns();
        }

        try (Stream<String> stream = Files.lines(this.filePath, StandardCharsets.UTF_8)) {

            stream.forEach(line -> {
                HashMap<String, String> rowValueMap = this.mapRowValues(line);
                if (rowValueMap != null) {
                    rows.add(rowValueMap);
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
        try (BufferedWriter bWriter = Files.newBufferedWriter(this.filePath,
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
            FileTime currentModifiedTime = Files.getLastModifiedTime(this.filePath);
            return currentModifiedTime.equals(this.lastModifiedTime);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void syncTableColumns() {
        this.cachedColumnValues = this.getRowAt(0);
        this.registerLocalTableOperation();
    }

    private void registerLocalTableOperation() {
        try {
            this.lastModifiedTime = Files.getLastModifiedTime(this.filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> getRowAt(int index) {
        try (Stream<String> stream = Files.lines(this.filePath, StandardCharsets.UTF_8)) {
            List<String> lines = stream.collect(Collectors.toList());

            String line = lines.get(index);
            return CSVFileHandler.parseLine(new StringReader(line));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private HashMap<String, String> mapRowValues(String line) {
        try {
            List<String> values = CSVFileHandler.parseLine(new StringReader(line));
            HashMap<String, String> rowValueMap = new HashMap<String, String>();

            //  Check if the row contains the right amount of values
            if (this.cachedColumnValues.size() != values.size()) {
                throw new Exception("Unexpected row values count (" + values.size()
                        + " instead of " + this.cachedColumnValues.size() + ")");
            }

            // Map the values to the column names
            for (int i = 0; i < this.cachedColumnValues.size(); i++) {
                rowValueMap.put(this.cachedColumnValues.get(i), values.get(i));
            }

            return rowValueMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
