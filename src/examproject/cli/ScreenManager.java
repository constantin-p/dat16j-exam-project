package examproject.cli;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

class ScreenManager {

    private static final int CELL_WIDTH = 20;
    private static final String VIEW_SEP = "*--------------------------------------*";
    private static final String DATE_FORMAT = "MM-dd-yyyy";

    private Scanner scanner;

    ScreenManager() {
        this.scanner = new Scanner(System.in);
    }

    int showOptionsView(String label, LinkedHashMap<String, Boolean> options) {
        System.out.println(VIEW_SEP);
        System.out.println("\n" + label + "\n");

        int i = 0;
        for (Map.Entry<String, Boolean> entry : options.entrySet()) {
            if (entry.getValue()) {
                i++;
                System.out.println(" [" + (i) + "]   " + entry.getKey());
            } else {
                System.out.println(" [*]   " + entry.getKey());
            }
        }
        return showRangeInputView(1, i) - 1;
    }

    void showCallbackOptionsView(String label, LinkedHashMap<ScreenOption, Boolean> options) {
        HashMap<Integer, ScreenOption> shownOptions = new HashMap<Integer, ScreenOption>();
        System.out.println(VIEW_SEP);
        System.out.println("\n" + label + "\n");

        int i = 0;
        for (Map.Entry<ScreenOption, Boolean> entry : options.entrySet()) {
            if (entry.getValue()) {
                i++;
                shownOptions.put(i, entry.getKey());
                System.out.println(" [" + (i) + "]   " + entry.getKey().label);
            } else {
                System.out.println(" [*]   " + entry.getKey().label);
            }
        }

        shownOptions.get(showRangeInputView(1, i)).callback.run();
    }

    void showCallbackOptionsTableView(String label, LinkedHashMap<ScreenTableOption, Boolean> options) {
        HashMap<Integer, ScreenTableOption> shownOptions = new HashMap<Integer, ScreenTableOption>();
        System.out.println(VIEW_SEP);
        System.out.println("\n" + label + "\n");

        int i = 0;
        boolean isFirstRow = true;
        for (Map.Entry<ScreenTableOption, Boolean> entry : options.entrySet()) {
            // If we have rows we also have access to the column names, so display the table header
            if (isFirstRow) {
                System.out.println("       " + ScreenTableOption.getHeaderStringFromRow(entry.getKey().row, CELL_WIDTH));
                System.out.println("       " + ScreenTableOption.getSepStringFromRow(entry.getKey().row, CELL_WIDTH));
                isFirstRow = false;
            }

            if (entry.getValue()) {
                i++;
                shownOptions.put(i, entry.getKey());
                System.out.println(" [" + (i) + "]   " + entry.getKey().getRowString(CELL_WIDTH));
            } else {
                System.out.println(" [*]   " + entry.getKey().getRowString(CELL_WIDTH));
            }
        }

        shownOptions.get(showRangeInputView(1, i)).callback.run();
    }

    void showInfoView(String label) {
        System.out.println(VIEW_SEP);
        System.out.println("\n" + label + "\n");
    }

    void showErrorView(String label) {
        System.out.println(VIEW_SEP);
        System.out.println("\n[ERROR] " + label + "\n");
    }

    private int rangeInputViewLoop(int min, int max) {
        while (true) {
            if (this.scanner.hasNextInt()) {
                int input = this.scanner.nextInt();
                if (input < min || input > max) {
                    // Invalid input, loop until a valid input is given (inside the range)
                    System.out.println(" Invalid option! [not in range] Try again.");
                } else {
                    // Valid input, return the value
                    return input;
                }
            } else {
                // Invalid input, loop until a valid input is given (typeof int)
                System.out.println(" Invalid option! [not an integer] Try again.");
                scanner.next();
            }
        }
    }

    private int showRangeInputView(int min, int max) {
        System.out.println("\n Enter your choice [" + (min == max
                ? min : min + "-" + max) + "]:");
        return this.rangeInputViewLoop(min, max);
    }

    private int showRangeInputView(String label, int min, int max) {
        System.out.println("\n " + label + " [" + (min == max
                ? min : min + "-" + max) + "]:");
        return this.rangeInputViewLoop(min, max);
    }

    LocalTime showTimeInputView(String label) {
        System.out.println(VIEW_SEP);
        System.out.println("\n" + label + "\n");

        // Minutes (0 - 60)
        int min = this.showRangeInputView("Enter the minutes", 0, 59);

        // Seconds (0 - 60)
        int sec = this.showRangeInputView("Enter the seconds", 0, 59);

        return LocalTime.of(0, min, sec);
    }

    ZonedDateTime showDateInputView(String label) {
        System.out.println(VIEW_SEP);
        System.out.println("\n" + label + "\n");

        while (true) {
            // Day (1 - 31)
            int day = this.showRangeInputView("Enter the day", 1, 31);

            // Month (1 - 12)
            int month = this.showRangeInputView("Enter the month", 1, 12);

            // Year (1900 - 2050)
            int year = this.showRangeInputView("Enter the year", 1900, 2050);

            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MM yyyy");
            String dateString = padLeft(Integer.toString(day), 2, '0') + " "
                    + padLeft(Integer.toString(month), 2, '0') + " "
                    + Integer.toString(year);

            ZonedDateTime date;
            boolean endLoop = false;
            try {
                LocalDate localDate = LocalDate.parse(dateString, format);
                date = localDate.atStartOfDay(ZoneId.of("UTC"));
                System.out.println(day + " " + month + " " + year + "    " + date);
                endLoop = true;
            } catch (DateTimeParseException exc) {
                System.out.println("Error: " + exc.getMessage());
                continue;
            }
            if (endLoop) {
                return date;
            }
        }
    }

    String showStringInputView(String label, int minLength, int maxLength) {
        System.out.println(VIEW_SEP);
        System.out.println("\n" + label + "\n");

        while (true) {
            String input = this.scanner.next();
            if (input.length() < minLength) {
                // Invalid input, loop until a valid input is given (has the right size)
                System.out.println(" Invalid input! [too short] Try again.");
            } else if (input.length() > maxLength) {
                // Invalid input, loop until a valid input is given (has the right size)
                System.out.println(" Invalid input! [too long] Try again.");
            } else {
                // Valid input, return the value
                return input;
            }
        }
    }


    /*
     *  Helpers
     */
    public static String parseType(String str) {
        if (str.length() == 0) {
            return "";
        }
        str = str.replace('_', ' ');
        return str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public static String parseDate(ZonedDateTime date) {
        return date.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public static String padRight(String str, int length) {
        return padRight(str, length, ' ');
    }

    public static String padRight(String str, int length, char padChar) {
        return String.format("%1$-" + length + "s", str).replace(' ', padChar);
    }

    public static String padLeft(String str, int length) {
        return padLeft(str, length, ' ');
    }

    public static String padLeft(String str, int length, char padChar) {
        return String.format("%1$" + length + "s", str).replace(' ', padChar);
    }

    // Algorithm modified from:
    // http://stackoverflow.com/questions/3597550/ideal-method-to-truncate-a-string-with-ellipsis
    public static String ellipse(String text, int max) {
        if (text.length() <= max)
            return text;

        // Start by chopping off at the word before max
        // This is an over-approximation due to thin-characters...
        int end = text.lastIndexOf(' ', max - 3);

        // Just one long word. Chop it off.
        if (end == -1)
            return text.substring(0, max-3) + "...";

        // Step forward as long as textWidth allows.
        int newEnd = end;
        do {
            end = newEnd;
            newEnd = text.indexOf(' ', end + 1);

            // No more spaces.
            if (newEnd == -1)
                newEnd = text.length();

        } while ((text.substring(0, newEnd) + "...").length() < max);

        return text.substring(0, end) + "...";
    }
}
