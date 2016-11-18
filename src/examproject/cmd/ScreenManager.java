package examproject.cmd;

import java.util.ArrayList;
import java.util.Scanner;

class ScreenManager {

    private Scanner scanner;

    ScreenManager() {
        this.scanner = new Scanner(System.in);
    }

    int showOptionsView(String label, ArrayList<String> options) {
        System.out.println("*--------------------------------------*");
        System.out.println("\n" + label + "\n");

        for (int i = 0; i < options.size(); i++) {
            System.out.println(" [" + (i + 1) + "]   " + options.get(i));
        }

        return showRangeInputView(1, options.size()) - 1;
    }

    void showInfoView(String label) {
        System.out.println("*--------------------------------------*");
        System.out.println("\n" + label + "\n");
    }

    int showRangeInputView(int min, int max) {
        System.out.println("\n Enter your choice [" + min + "-" + max + "]:");
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
}
