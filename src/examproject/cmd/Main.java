package examproject.cmd;

import examproject.core.ManagementSystem;

import java.util.ArrayList;
import java.util.Date;

public class Main {

    private static ScreenManager screenManager = new ScreenManager();
    private static ManagementSystem app = new ManagementSystem();

    public static void main(String[] args) {
        showMainMenu();
    }

    private static void showMainMenu() {
        ArrayList<String> mainMenu = new ArrayList<String>();
        mainMenu.add("Chairman login.");
        mainMenu.add("Treasurer login.");

        mainMenu.add("Exit.");

        int selectedOption = screenManager.setOptionsView(" - Main menu - ", mainMenu);
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
        while (true) {
            String username = screenManager.setStringInputView(" - [Chairman] username: - ", 4, 10);
            String password = screenManager.setStringInputView(" - [Chairman] password: - ", 4, 10);

            boolean okStatus = app.chairmanSignIn(username, password);
            if (okStatus) {
                showChairmanMenu();
                return;
            } else {
                screenManager.setInfoView("Log in error!");
            }
        }
    }

    private static void showChairmanMenu() {
        ArrayList<String> chairmanMenu = new ArrayList<String>();
        chairmanMenu.add("Add new member.");

        chairmanMenu.add("Log out (back to main menu).");

        int selectedOption = screenManager.setOptionsView(" - Chairman menu - ", chairmanMenu);
        switch (selectedOption) {
            case 0:
                // Add new member option
                showMemberNewForm();
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
        while (true) {
            String username = screenManager.setStringInputView(" - [Treasurer] username: - ", 4, 10);
            String password = screenManager.setStringInputView(" - [Treasurer] password: - ", 4, 10);

            boolean okStatus = app.treasurerSignIn(username, password);
            if (okStatus) {
                showTreasurerMenu();
                return;
            } else {
                screenManager.setInfoView("Log in error");
            }

        }
    }


    private static void showTreasurerMenu() {
        ArrayList<String> treasurerMenu = new ArrayList<String>();
        treasurerMenu.add("Show members.");

        treasurerMenu.add("Log out (back to main menu).");

        int selectedOption = screenManager.setOptionsView(" - Treasurer menu - ", treasurerMenu);
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


    /*
     *  Member views
     */
    private static void showMemberNewForm() {
        String firstName = screenManager.setStringInputView(" - [Chairman] Member first name: - ", 4, 10);
        String lastName = screenManager.setStringInputView(" - [Chairman] Member last name: - ", 4, 10);
        String cprNumber = screenManager.setStringInputView(" - [Chairman] Member CPR number: - ", 4, 10);

        app.addMember(firstName, lastName, new Date(), cprNumber);

        System.out.println(" Member added!");
        showChairmanMenu();
    }
}
