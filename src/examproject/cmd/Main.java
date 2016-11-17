package examproject.cmd;

import examproject.core.Discount;
import examproject.core.ManagementSystem;
import examproject.core.Member;
import examproject.core.SeniorDiscount;

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
                showMemberList();
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

    private static void showMemberList() {
        ArrayList<Member> members = app.getMembers();
        ArrayList<String> options = new ArrayList<String>();
        for(int i = 0; i < members.size(); i++) {
            Member currentMember = members.get(i);
            options.add(currentMember.firstName + " " + currentMember.lastName);
        }
        int selectedMember = screenManager.setOptionsView(" - Member list - ", options);
        System.out.println(selectedMember);
        showMemberActions(members.get(selectedMember));
    }

    private static void showMemberActions(Member member) {
        ArrayList<String> memberActionsMenu = new ArrayList<String>();
        memberActionsMenu.add("Apply discount");

        memberActionsMenu.add("Members list (back to members list");

        int selectedOption = screenManager.setOptionsView(" - <" + member.firstName + " " + member.lastName + "> actions menu - ", memberActionsMenu);
        switch (selectedOption) {
            case 0:
                // Show discount option
                showDiscountList(member);
                break;
            case 1:
                // Back to member list option
                showMemberList();
                break;
        }
    }

    private static void showDiscountList(Member member) {
        SeniorDiscount discount = app.getDiscounts();
        ArrayList<String> options = new ArrayList<String>();
        options.add(discount.getType() + " modifier " + discount.getModifier());
        int selectedDiscount = screenManager.setOptionsView(" - Discount list - ", options);
        System.out.println(selectedDiscount);
    }
}
