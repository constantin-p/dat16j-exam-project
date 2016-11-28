package examproject.cmd;

import examproject.core.*;

import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.util.*;

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

            Response response = app.chairmanSignIn(username, password);
            if (response.success) {
                showChairmanMenu();
                return;
            } else {
                screenManager.showInfoView("Log in error! " + response.info);
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
                showChairmanNewMemberForm();
                break;
            case 1:
                // Back to main menu option
                showMainMenu();
                break;
        }
    }

    private static void showChairmanNewMemberForm() {
        String firstName = screenManager
                .showStringInputView(" - [Chairman > Add new member] First name: - ",
                        4, 10);
        String lastName = screenManager
                .showStringInputView(" - [Chairman > Add new member] Last name: - ",
                        4, 10);
        String CPRNumber = screenManager
                .showStringInputView(" - [Chairman > Add new member] CPR number: - ",
                        10, 10);
        ZonedDateTime dateOfBirth = screenManager
                .showDateInputView(" - [Chairman > Add new member] Date of birth: - ");

        // Active | Passive
        List<String> accountType = new ArrayList<String>();
        accountType.add("Active.");
        accountType.add("Passive.");

        boolean isActive = screenManager
                .showOptionsView(" - [Chairman > Add new member] Status: - ", accountType) == 0;

        boolean isElite = false;
        if (isActive) {
            // Elite | Amateur (only for active members)
            List<String> trainingType = new ArrayList<String>();
            trainingType.add("Elite.");
            trainingType.add("Amateur.");

            isElite = screenManager
                    .showOptionsView(" - [Chairman] Member Training status: - ", trainingType) == 0;
        }

        // Preferred discipline
        List<Discipline> disciplines = app.getDisciplines();

        Discipline preferredDiscipline = showDisciplineList("[Chairman > Add new member] ");

        Response response = app.addMember(firstName, lastName, CPRNumber, dateOfBirth,
                ZonedDateTime.now(ZoneOffset.UTC), isActive, isElite, preferredDiscipline);
        if (response.success) {
            screenManager.showInfoView("Member added!");
            showChairmanMenu();
        } else {
            screenManager.showInfoView("Error! " + response.info);
            showChairmanMenu();
        }
    }

    /*
     *  Treasurer views
     */
    private static void showTreasurerLogin() {
        while (true) {
            String username = screenManager.showStringInputView(" - [Treasurer] username: - ",
                    4, 10);
            String password = screenManager.showStringInputView(" - [Treasurer] password: - ",
                    4, 10);

            Response response = app.treasurerSignIn(username, password);
            if (response.success) {
                showTreasurerMenu();
                return;
            } else {
                screenManager.showInfoView("Log in error! " + response.info);
            }
        }
    }

    private static void showTreasurerMenu() {
        ArrayList<String> treasurerMenu = new ArrayList<String>();
        treasurerMenu.add("Show members.");
        treasurerMenu.add("Show members with late payments.");

        treasurerMenu.add("Log out (back to main menu).");

        int selectedOption = screenManager.showOptionsView(" - Treasurer menu - ", treasurerMenu);
        switch (selectedOption) {
            case 0:
                // Show members option
                showTreasurerMemberList();
                break;
            case 1:
                // Show members with late payments option
                showMembersWithLatePayments();
                break;
            case 2:
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

            Response response = app.coachSignIn(username, password);
            if (response.success) {
                showCoachMenu();
                return;
            } else {
                screenManager.showInfoView("Log in error! " + response.info);
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
                showCoachDisciplineList();
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
    private static void showCoachDisciplineList() {
        List<Discipline> disciplines = app.getDisciplines();
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

//    private static void showDisciplineLeaderboard(Discipline discipline) {
//        ArrayList<Member> leaderboardScores = discipline.getLeaderboard().getScores();
//        ArrayList<String> options = new ArrayList<String>();
//
//        // setOptionsView accepts an ArrayList of strings, so
//        // loop throw all the members and create a string for the option label
//        for(int i = 0; i < leaderboardScores.size(); i++) {
//            Member currentParticipant = leaderboardScores.get(i);
//            options.add(currentParticipant.firstName + " " + currentParticipant.lastName + " ");
//        }
//
//
//        String viewLabel = " - <" + discipline.name + "> leaderboard - ";
//        screenManager.showInfoView(viewLabel, options);
//    }
//


    /*
     *  Member views (Coach)
     */

    private static void showCoachMemberList() {
        List<Member> members = app.getMembers();
        List<String> options = new ArrayList<String>();

        // setOptionsView accepts an ArrayList of strings, so
        // loop throw all the members and create a string for the option label
        for(int i = 0; i < members.size(); i++) {
            Member currentMember = members.get(i);
            options.add(currentMember.firstName + " " + currentMember.lastName + " " + currentMember.CPRNumber);
        }

        int selectedMemberIndex = screenManager.showOptionsView(" - Coach Member list - ", options);
        showCoachMemberActions(members.get(selectedMemberIndex));
    }

    private static void showCoachMemberActions(Member member) {
        ArrayList<String> coachMemberActionsMenu = new ArrayList<String>();
        coachMemberActionsMenu.add("Available competitions.");
        coachMemberActionsMenu.add("Register time.");

        coachMemberActionsMenu.add("Members list (back to member list).");
        coachMemberActionsMenu.add("Exit (back to coach menu)");

        String viewLabel = " - <" + member.firstName + " " + member.lastName + "> actions menu - ";
        int selectedOption = screenManager.showOptionsView(viewLabel, coachMemberActionsMenu);
        switch (selectedOption) {
            case 0:
                // View available competitions
                Competition selectedCompetition = showCompetitionList(member);
                selectedCompetition.enterCompetition(member);
                screenManager.showInfoView("Member <" + member.firstName + " " + member.lastName + "> has been registered to the following competition: " + selectedCompetition.name);
                break;
            case 1:
                // Register time
                showMemberTimeForm(member);
                break;

            case 2:
                // Back to member list option
                showCoachMemberList();
                break;
            case 3:
                // Back to coach menu
                showCoachMenu();
        }
    }
    private static Competition showCompetitionList(Member member) {
        List<Competition> competitions = app.getCompetitions();
        ArrayList<String> options = new ArrayList<String>();

        // setOptionsView accepts an ArrayList of strings, so
        // loop throw all the members and create a string for the option label
        for (int i = 0; i < competitions.size(); i++) {
            Competition  currentCompetition = competitions.get(i);
            options.add(currentCompetition.name + " " + currentCompetition.discipline.name);
        }

        int selectedCompetitionIndex = screenManager.showOptionsView(" - Competition list - ", options);
        return competitions.get(selectedCompetitionIndex);
    }


    /*
     *  Member views (Treasurer)
     */

    private static void showMemberTimeForm(Member member) {
        String time = screenManager.showTimeInputView(" - [Register time] Time: - ");
        ZonedDateTime date = screenManager.showDateInputView(" - [Register time] Date: - ");
        // Type


        screenManager.showInfoView("Time: " + time + " | Date: " + date);

        ArrayList<String> resultTypeActionsMenu = new ArrayList<String>();
        resultTypeActionsMenu.add("Competition time.");
        resultTypeActionsMenu.add("Individual time.");

        resultTypeActionsMenu.add("Member action list (back to member actions).");


        String viewLabel = " - <Time: " + time + " | Date: " + date + "> result type - ";
        int selectedOption = screenManager.showOptionsView(viewLabel, resultTypeActionsMenu);
        switch (selectedOption) {
            case 0:
                // View available competitions
                System.out.println("Not yet implemented!");

                break;
            case 1:
                member.registerLapTime(new LapTime(time, date));
                screenManager.showInfoView("Lap time registered: " + time + " on " + date);
                // Back to member actions option
                showCoachMemberActions(member);
                break;

            case 2:
                // Back to member actions option
                showCoachMemberActions(member);
                break;
        }
    }



    private static void showTreasurerMemberList() {
        List<Member> members = app.getMembers();
        List<String> options = new ArrayList<String>();

        // setOptionsView accepts an ArrayList of strings, so
        // loop throw all the members and create a string for the option label
        for(int i = 0; i < members.size(); i++) {
            Member currentMember = members.get(i);
            String currentMemberDiscounts = "";

            if(Objects.equals(currentMemberDiscounts, "")) {
                options.add(currentMember.firstName + " " + currentMember.lastName
                        + " " + currentMember.CPRNumber + " No discounts applied for this account.");
            } else {
                options.add(currentMember.firstName + " " + currentMember.lastName
                        + " " + currentMember.CPRNumber + " discounts: " + currentMemberDiscounts);
            }
        }

        int selectedMemberIndex = screenManager.showOptionsView(" - [Treasurer] Member list - ", options);
        showTreasurerMemberActions(members.get(selectedMemberIndex));
    }

    private static void showTreasurerMemberActions(Member member) {
        LinkedHashMap<String, Boolean> treasurerMemberActions = new LinkedHashMap<String, Boolean>();

        List<Discount> discounts = app.getDiscounts();
        boolean discountsAvailable = false;

        for(int i = 0; i < discounts.size(); i++) {
            System.out.println("DISCOUNT EQUAL" + member.hasDiscount(discounts.get(i)));
            Discount currentDiscount = discounts.get(i);
            currentDiscount.checkCondition(member);
            // If the discount is applicable and the member doesn't have it yet
            // we show the discount menu
            if(currentDiscount.checkCondition(member) && !member.hasDiscount(currentDiscount)) {
                discountsAvailable = true;
                break;
            }
        }

        treasurerMemberActions.put("Apply discount"
                + (discountsAvailable ? "." : " (no available discounts)."), discountsAvailable);

        boolean hasPaidThisYear = member.hasPaidThisYear();
        treasurerMemberActions.put("Pay fee"
                + (hasPaidThisYear ? "." : " (already paid this year's fee)."), hasPaidThisYear);

        treasurerMemberActions.put("Members list (back to member list).", true);


        String viewLabel = " - [Treasurer > Member actions menu ] <" + member.firstName + " " + member.lastName + "> - ";
        int selectedOption = screenManager.showOptionsView(viewLabel, treasurerMemberActions);
        System.out.println("SELECT " + selectedOption);
        switch (selectedOption) {

            case 0:
                // Show discount option
                if (discountsAvailable) {
                    showTreasurerDiscountList(member);
                } else {
                    // Show payment option
                    if (hasPaidThisYear) {
                        showPaymentActions(member);
                    } else {
                        showTreasurerMemberList();
                    }
                }
                break;
            case 1:
                // Show payment option

                if (discountsAvailable) {
                    if (hasPaidThisYear) {
                        showPaymentActions(member);
                    } else {
                        showTreasurerMemberActions(member); // loop
                    }
                } else {
                    // Show payment option
                    showTreasurerMemberList();
                }
                break;
            case 2:
                // Back to member list option
                showTreasurerMemberList();
                break;
        }
    }

    private static void showPaymentActions(Member member) {
        ArrayList<String> paymentActionsMenu = new ArrayList<String>();
        paymentActionsMenu.add("Accept (pay fee).");
        paymentActionsMenu.add("Decline (back to member actions menu).");

        double feeValue = member.calculateFee();
        String viewLabel = " - <" + member.firstName + " " + member.lastName + "> pay fee of: " + feeValue + " - ";
        int selectedOption = screenManager.showOptionsView(viewLabel, paymentActionsMenu);
        switch (selectedOption) {
            case 0:
                // Accept (pay fee) option
                member.registerPayment(new Payment(feeValue, "Member fee", ZonedDateTime.now(ZoneOffset.UTC)));
                screenManager.showInfoView("Payment registered!");
                showTreasurerMemberActions(member);
                break;
            case 1:
                // Decline (back to member actions menu) option
                showTreasurerMemberActions(member);
                break;
        }
    }


    private static void showMembersWithLatePayments() {
        List<Member> members = app.getMembers();
        ArrayList<String> options = new ArrayList<String>();

        // setOptionsView accepts an ArrayList of strings, so
        // loop throw all the members and create a string for the option label
        for(int i = 0; i < members.size(); i++) {
            Member currentMember = members.get(i);
            Response hasLatePayment = currentMember.hasLatePayment();
            if(hasLatePayment.success) {
                options.add(currentMember.firstName + " " + currentMember.lastName + " " + currentMember.CPRNumber + " late for: " + hasLatePayment.info);

            } else {
                members.remove(i);
                i--;
            }
        }

        int selectedMemberIndex = screenManager.showOptionsView(" - Member list - ", options);
        showTreasurerMemberActions(members.get(selectedMemberIndex));
    }
    /*
     *  Discount views
     */
    private static void showTreasurerDiscountList(Member member) {
        List<Discount> discounts = app.getDiscounts();
        ArrayList<String> options = new ArrayList<String>();

        // TODO: REFACTOR show enabled discounts and add as an option only the available ones
        // setOptionsView accepts an ArrayList of strings, so
        // loop throw all the discounts and create a string for the option label
        for (int i = 0; i < discounts.size(); i++) {
            Discount currentDiscount = discounts.get(i);
            if (!member.hasDiscount(currentDiscount)) {
                options.add("<" + currentDiscount.getType() + ">  modifier: "
                        + (currentDiscount.getModifier() * 100) + "%.");
            } else {
                discounts.remove(i);
                i--;
            }
        }

        int selectedDiscount = screenManager.showOptionsView(" - [Treasurer] Discount list - ", options);
        member.registerDiscount(discounts.get(selectedDiscount));
        screenManager.showInfoView("Discount applied!");
        showTreasurerMemberActions(member);
    }



    /*
     *  Helpers
     */
    private static Discipline showDisciplineList(String labelPrefix) {
        List<Discipline> disciplines = app.getDisciplines();
        List<String> options = new ArrayList<String>();

        // setOptionsView accepts an ArrayList of strings, so
        // loop throw all the members and create a string for the option label
        for(int i = 0; i < disciplines.size(); i++) {
            Discipline currentDiscipline = disciplines.get(i);
            options.add(currentDiscipline.name);
        }

        int selectedDisciplineIndex = screenManager.showOptionsView(" - "
                + labelPrefix + "Discipline list - ", options);
        return disciplines.get(selectedDisciplineIndex);
    }
}
