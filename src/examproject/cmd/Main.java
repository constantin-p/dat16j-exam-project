package examproject.cmd;

import examproject.core.ManagementSystem;
import examproject.core.Discount;
import examproject.core.Member;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        mainMenu.add("Coach login.");

        mainMenu.add("Exit.");

        int selectedOption = screenManager.showOptionsView(" - Main menu - ", mainMenu);
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
                //Coach login option
                showCoachLogin();
            case 3:
                // Exit option
                break;
        }
    }

    /*
     *  Chairman views
     */
    private static void showChairmanLogin() {
        while (true) {
            String username = screenManager.showStringInputView(" - [Chairman] username: - ", 4, 10);
            String password = screenManager.showStringInputView(" - [Chairman] password: - ", 4, 10);

            boolean okStatus = app.chairmanSignIn(username, password);
            if (okStatus) {
                showChairmanMenu();
                return;
            } else {
                screenManager.showInfoView("Log in error!");
            }
        }
    }

    private static void showChairmanMenu() {
        ArrayList<String> chairmanMenu = new ArrayList<String>();
        chairmanMenu.add("Add new member.");

        chairmanMenu.add("Log out (back to main menu).");

        int selectedOption = screenManager.showOptionsView(" - Chairman menu - ", chairmanMenu);
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
            String username = screenManager.showStringInputView(" - [Treasurer] username: - ", 4, 10);
            String password = screenManager.showStringInputView(" - [Treasurer] password: - ", 4, 10);

            boolean okStatus = app.treasurerSignIn(username, password);
            if (okStatus) {
                showTreasurerMenu();
                return;
            } else {
                screenManager.showInfoView("Log in error");
            }
        }
    }

    private static void showTreasurerMenu() {
        ArrayList<String> treasurerMenu = new ArrayList<String>();
        treasurerMenu.add("Show members.");

        treasurerMenu.add("Log out (back to main menu).");

        int selectedOption = screenManager.showOptionsView(" - Treasurer menu - ", treasurerMenu);
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
        String firstName = screenManager.showStringInputView(" - [Chairman] Member first name: - ", 4, 10);
        String lastName = screenManager.showStringInputView(" - [Chairman] Member last name: - ", 4, 10);
        String cprNumber = screenManager.showStringInputView(" - [Chairman] Member CPR number: - ", 4, 10);

        app.addMember(firstName, lastName, new Date(), cprNumber);

        System.out.println(" Member added!");
        showChairmanMenu();
    }


    /*
     * Coach views
     */
    private static void showCoachLogin() {
        while (true) {
            String username = screenManager.showStringInputView(" - [Coach] username: - ", 4, 10);
            String password = screenManager.showStringInputView(" - [Coach] password: - ", 4, 10);

            boolean okStatus = app.coachSignIn(username, password);
            if (okStatus) {
                showCoachMenu();
                return;
            } else {
                screenManager.showInfoView("Log in error!");
            }
        }
    }

    private static void showCoachMenu() {
        ArrayList<String> coachMenu = new ArrayList<String>();
        coachMenu.add("View my members.");

        coachMenu.add("Log out (back to main menu).");

        int selectedOption = screenManager.showOptionsView(" - Coach menu - ", coachMenu);
        switch (selectedOption) {
            case 0:
                // View my members option
                // TODO display only members assigned to specific coach
                System.out.println("\nNot yet implemented!");
                break;
            case 1:
                // Back to main menu option
                showMainMenu();
                break;
        }

    }
}
