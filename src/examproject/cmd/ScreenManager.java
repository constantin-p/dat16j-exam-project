package examproject.cmd;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ScreenManager {

    private Scanner scanner;

    ScreenManager() {
        this.scanner = new Scanner(System.in);
    }

    int showOptionsView(String label, List<String> options) {
        System.out.println("*--------------------------------------*");
        System.out.println("\n" + label + "\n");

        for (int i = 0; i < options.size(); i++) {
            System.out.println(" [" + (i + 1) + "]   " + options.get(i));
        }

        return showRangeInputView(1, options.size()) - 1;
    }

    int showOptionsView(String label, ArrayList<String> disabledOptions, ArrayList<String> options) {
        System.out.println("*--------------------------------------*");
        System.out.println("\n" + label + "\n");

        for (int i = 0; i < disabledOptions.size(); i++) {
            System.out.println(" [*]   " + disabledOptions.get(i));
        }

        for (int i = 0; i < options.size(); i++) {
            System.out.println(" [" + (i + 1) + "]   " + options.get(i));
        }

        return showRangeInputView(1, options.size()) - 1;
    }

    void showInfoView(String label) {
        System.out.println("*--------------------------------------*");
        System.out.println("\n" + label + "\n");
    }

    // TODO: use showOptionsView
    void showInfoView(String label, ArrayList data) {
        System.out.println("*--------------------------------------*");
        System.out.println("\n" + label + "\n");

        for (int i = 0; i < data.size(); i++) {
            System.out.println(" [" + (i + 1) + "]   " + data.get(i));
        }
    }

    int rangeInputViewLoop(int min, int max) {
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

    int showRangeInputView(int min, int max) {
        System.out.println("\n Enter your choice [" + min + "-" + max + "]:");
        return this.rangeInputViewLoop(min, max);
    }

    int showRangeInputView(String label, int min, int max) {
        System.out.println("\n " + label + " [" + min + "-" + max + "]:");
        return this.rangeInputViewLoop(min, max);
    }

    String showTimeInputView(String label) {
        System.out.println("*--------------------------------------*");
        System.out.println("\n" + label + "\n");


        // Minutes (0 - 60)
        int min = this.showRangeInputView("Enter the minutes", 0, 60);

        // Seconds (0 - 60)
        int sec = this.showRangeInputView("Enter the seconds", 0, 60);

        String timeString = padLeft(Integer.toString(min), 2, '0') + ":"
                + padLeft(Integer.toString(sec), 2, '0');

        return timeString;
    }

    ZonedDateTime showDateInputView(String label) {
        System.out.println("*--------------------------------------*");
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
        System.out.println("*--------------------------------------*");
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


    private String padLeft(String s, int n, char c) {
        return String.format("%1$" + n + "s", s).replace(' ', c);
    }
}
