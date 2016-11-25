package examproject.cmd;

import examproject.core.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
                showMemberList();
                break;
            case 1:
                // Back to main menu option
                showMainMenu();
                break;
        }
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
        coachMenu.add("View disciplines leaderboards");

        coachMenu.add("Log out (back to main menu).");

        int selectedOption = screenManager.showOptionsView(" - Coach menu - ", coachMenu);
        switch (selectedOption) {
            case 0:
                // View my members option
                // TODO display only members assigned to specific coach
                showCoachMemberList();
                break;
            case 1:
                // View leaderboards option
                // TODO display array ofleaderboards for different disciplines of members assigned to coach
                showDisciplineList();
                break;
            case 2:
                // Back to main menu option
                showMainMenu();
                break;
        }

    }

    /*
     *  Discipline views
     */
    private static void showDisciplineList() {
        ArrayList<Discipline> disciplines = app.getDisciplines();
        ArrayList<String> options = new ArrayList<String>();

        // setOptionsView accepts an ArrayList of strings, so
        // loop throw all the members and create a string for the option label
        for(int i = 0; i < disciplines.size(); i++) {
            Discipline currentDiscipline = disciplines.get(i);
            options.add(currentDiscipline.name);
        }

        int selectedDisciplineIndex = screenManager.showOptionsView(" - Discipline list - ", options);
        System.out.println("Not yet implemented!");
//        showDisciplineLeaderboard(disciplines.get(selectedDisciplineIndex));
    }

    private static void showDisciplineLeaderboard(Discipline discipline) {
        ArrayList<Member> leaderboardScores = discipline.getLeaderboard().getScores();
        ArrayList<String> options = new ArrayList<String>();

        // setOptionsView accepts an ArrayList of strings, so
        // loop throw all the members and create a string for the option label
        for(int i = 0; i < leaderboardScores.size(); i++) {
            Member currentParticipant = leaderboardScores.get(i);
            options.add(currentParticipant.firstName + " " + currentParticipant.lastName + " ");
        }


        String viewLabel = " - <" + discipline.name + "> leaderboard - ";
        screenManager.showInfoView(viewLabel, options);
    }



    /*
     *  Member views (Coach)
     */

    private static void showCoachMemberList() {
        ArrayList<Member> members = app.getMembers();
        ArrayList<String> options = new ArrayList<String>();

        // setOptionsView accepts an ArrayList of strings, so
        // loop throw all the members and create a string for the option label
        for(int i = 0; i < members.size(); i++) {
            Member currentMember = members.get(i);
            options.add(currentMember.firstName + " " + currentMember.lastName + " " + currentMember.cprNumber);
        }

        int selectedMemberIndex = screenManager.showOptionsView(" - Coach Member list - ", options);
        showCoachMemberActions(members.get(selectedMemberIndex));
    }

    private static void showCoachMemberActions(Member member) {
        ArrayList<String> coachMemberActionsMenu = new ArrayList<String>();
        coachMemberActionsMenu.add("Available competitions.");
        coachMemberActionsMenu.add("Members list (back to member list).");

        coachMemberActionsMenu.add("Exit (back to coach menu)");

        String viewLabel = " - <" + member.firstName + " " + member.lastName + "> actions menu - ";
        int selectedOption = screenManager.showOptionsView(viewLabel, coachMemberActionsMenu);
        switch (selectedOption) {
            case 0:
                // View available competitions
                System.out.println("Not yet implemented!");
                break;
            case 1:
                // Back to member list option
                showCoachMemberList();
                break;
            case 2:
                // Back to coach menu
                showCoachMenu();
        }
    }


    /*
     *  Member views (Treasurer)
     */
    private static void showMemberNewForm() {
        String firstName = screenManager.showStringInputView(" - [Chairman] Member first name: - ", 4, 10);
        String lastName = screenManager.showStringInputView(" - [Chairman] Member last name: - ", 4, 10);
        String cprNumber = screenManager.showStringInputView(" - [Chairman] Member CPR number: - ", 4, 10);

        // TODO: return validation, member already registered error messages
        app.addMember(firstName, lastName, new Date(), cprNumber);

        screenManager.showInfoView("Member added");
        showChairmanMenu();
    }

    private static void showMemberList() {
        ArrayList<Member> members = app.getMembers();
        ArrayList<String> options = new ArrayList<String>();

        // setOptionsView accepts an ArrayList of strings, so
        // loop throw all the members and create a string for the option label
        for(int i = 0; i < members.size(); i++) {
            Member currentMember = members.get(i);
            String currentMemberDicounts = currentMember.getAppliedDiscountsString();

            if(Objects.equals(currentMemberDicounts, "")) {
                options.add(currentMember.firstName + " " + currentMember.lastName + " " + currentMember.cprNumber + " No discounts applied for this account.");
            } else {
                options.add(currentMember.firstName + " " + currentMember.lastName + " " + currentMember.cprNumber + " discounts: " + currentMemberDicounts);
            }
        }

        int selectedMemberIndex = screenManager.showOptionsView(" - Member list - ", options);
        showMemberActions(members.get(selectedMemberIndex));
    }

    private static void showMemberActions(Member member) {
        ArrayList<String> memberActionsMenu = new ArrayList<String>();
        memberActionsMenu.add("Apply discount.");

        memberActionsMenu.add("Members list (back to member list).");

        String viewLabel = " - <" + member.firstName + " " + member.lastName + "> actions menu - ";
        int selectedOption = screenManager.showOptionsView(viewLabel, memberActionsMenu);
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


    /*
     *  Discount views
     */
    private static void showDiscountList(Member member) {
        List<Discount> discounts = app.getDiscounts();
        ArrayList<String> options = new ArrayList<String>();

        // TODO: show enabled discounts and add as an option only the available ones
        // setOptionsView accepts an ArrayList of strings, so
        // loop throw all the discounts and create a string for the option label
        for (int i = 0; i < discounts.size(); i++) {
            Discount currentDiscount = discounts.get(i);
            options.add("<" + currentDiscount.getType() + ">  modifier: " + (currentDiscount.getModifier() * 100) + "%.");
        }

        int selectedDiscount = screenManager.showOptionsView(" - Discount list - ", options);
        System.out.println(selectedDiscount);
        // TODO: apply the discount (save it to the selected member)
        member.applyDiscount(discounts.get(selectedDiscount));
    }
}
