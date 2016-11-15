package examproject.cmd;

import java.util.ArrayList;

public class Main {

    private static ScreenManager screenManager = new ScreenManager();

    public static void main(String[] args) {
        showMainMenu();
    }

    private static void showMainMenu() {
        ArrayList<String> mainMenu = new ArrayList<String>();
        mainMenu.add("Chairman login.");
        mainMenu.add("Treasurer login.");

        mainMenu.add("Exit.");

        int selectedOption = screenManager.getOptionsView(" - Main menu - ", mainMenu);
        switch (selectedOption) {
            case 0:
                // Chairman login option
                showChairmanLogin();
                break;
            case 1:
                // Treasurer login option
                showTreasurerLogin();
                break;
            case 2:
                // Exit option
                break;
        }
    }

    /*
     *  Chairman views
     */
    private static void showChairmanLogin() {
        String username = screenManager.getStringInputView(" - [Chairman] username: - ", 4, 10);
        String password = screenManager.getStringInputView(" - [Chairman] password: - ", 4, 10);

        // TODO: Validation

        showChairmanMenu();
    }

    private static void showChairmanMenu() {
        ArrayList<String> chairmanMenu = new ArrayList<String>();
        chairmanMenu.add("Add new member.");

        chairmanMenu.add("Log out (back to main menu).");

        int selectedOption = screenManager.getOptionsView(" - Chairman menu - ", chairmanMenu);
        switch (selectedOption) {
            case 0:
                // Add new member option
                System.out.println("\nNot yet implemented!");
                break;
            case 1:
                // Back to main menu option
                showMainMenu();
                break;
        }
    }

    /*
     *  Treasurer views
     */
    private static void showTreasurerLogin() {
        String username = screenManager.getStringInputView(" - [Treasurer] username: - ", 4, 10);
        String password = screenManager.getStringInputView(" - [Treasurer] password: - ", 4, 10);

        // TODO: Validation

        showTreasurerMenu();
    }


    private static void showTreasurerMenu() {
        ArrayList<String> treasurerMenu = new ArrayList<String>();
        treasurerMenu.add("Show members.");

        treasurerMenu.add("Log out (back to main menu).");

        int selectedOption = screenManager.getOptionsView(" - Treasurer menu - ", treasurerMenu);
        switch (selectedOption) {
            case 0:
                // Show members option
                System.out.println("\nNot yet implemented!");
                break;
            case 1:
                // Back to main menu option
                showMainMenu();
                break;
        }
    }
}
