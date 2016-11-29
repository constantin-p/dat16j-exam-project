package examproject.cmd;

import examproject.core.*;

import java.time.LocalTime;
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
        LinkedHashMap<ScreenOption, Boolean> mainMenu = new LinkedHashMap<ScreenOption, Boolean>();

        mainMenu.put(new ScreenOption("Chairman login.", () -> showChairmanLogin()), true);
        mainMenu.put(new ScreenOption("Treasurer login.", () -> showTreasurerLogin()), true);
        mainMenu.put(new ScreenOption("Coach login.", () -> showCoachLogin()), true);

        mainMenu.put(new ScreenOption("× Exit.", () -> screenManager.showInfoView("Goodbye!")), true);

        screenManager.showCallbackOptionsView(" － Main menu － ", mainMenu);
    }


    /*
     *  Chairman views
     */
    private static void showChairmanLogin() {
        while (true) {
            String username = screenManager
                    .showStringInputView(" － [Chairman] username: － ",
                            4, 10);
            String password = screenManager
                    .showStringInputView(" － [Chairman] password: － ",
                            4, 10);

            Response response = app.chairmanSignIn(username, password);
            if (response.success) {
                showChairmanMenu();
                return;
            } else {
                screenManager.showErrorView("Log in error! " + response.info);
            }
        }
    }

    private static void showChairmanMenu() {
        LinkedHashMap<ScreenOption, Boolean> chairmanMenu = new LinkedHashMap<ScreenOption, Boolean>();

        chairmanMenu.put(new ScreenOption("Add new member.",
                () -> showChairmanNewMemberForm()), true);
        chairmanMenu.put(new ScreenOption("↤ Log out (back to main menu).",
                () -> showMainMenu()), true);

        screenManager.showCallbackOptionsView(" － Chairman menu － ", chairmanMenu);
    }

    private static void showChairmanNewMemberForm() {
        String firstName = screenManager
                .showStringInputView(" － [Chairman > Add new member] First name: － ",
                        4, 10);
        String lastName = screenManager
                .showStringInputView(" － [Chairman > Add new member] Last name: － ",
                        4, 10);
        String CPRNumber = screenManager
                .showStringInputView(" － [Chairman > Add new member] CPR number: － ",
                        10, 10);
        ZonedDateTime dateOfBirth = screenManager
                .showDateInputView(" － [Chairman > Add new member] Date of birth: － ");

        // Active | Passive
        LinkedHashMap<String, Boolean> accountType = new LinkedHashMap<String, Boolean>();

        accountType.put("Active.", true);
        accountType.put("Passive.", true);

        boolean isActive = screenManager
                .showOptionsView(" － [Chairman > Add new member] Status: － ", accountType) == 0;

        boolean isElite = false;
        if (isActive) {
            // Elite | Amateur (only for active members)
            LinkedHashMap<String, Boolean> trainingType = new LinkedHashMap<String, Boolean>();

            trainingType.put("Elite.", true);
            trainingType.put("Amateur.", true);

            isElite = screenManager
                    .showOptionsView(" － [Chairman] Member Training status: － ", trainingType) == 0;
        }

        // Preferred discipline
        List<Discipline> disciplines = app.getDisciplines();

        Discipline preferredDiscipline = showDisciplineList("[Chairman > Add new member] ");

        // Assign a coach only for elite members
        Coach assignedCoach = isElite
                ? showCoachList("[Chairman > Add new member] ")
                : null;

        Response response = app.addMember(firstName, lastName, CPRNumber, dateOfBirth,
                ZonedDateTime.now(ZoneOffset.UTC), isActive, isElite, preferredDiscipline, assignedCoach);
        if (response.success) {
            screenManager.showInfoView("Member added!");
            showChairmanMenu();
        } else {
            screenManager.showErrorView(response.info);
            showChairmanMenu();
        }
    }


    /*
     *  Treasurer views
     */
    private static void showTreasurerLogin() {
        while (true) {
            String username = screenManager
                    .showStringInputView(" － [Treasurer] username: － ",
                    4, 10);
            String password = screenManager
                    .showStringInputView(" － [Treasurer] password: － ",
                    4, 10);

            Response response = app.treasurerSignIn(username, password);
            if (response.success) {
                showTreasurerMenu();
                return;
            } else {
                screenManager.showErrorView("Log in error! " + response.info);
            }
        }
    }

    private static void showTreasurerMenu() {
        LinkedHashMap<ScreenOption, Boolean> treasurerMenu = new LinkedHashMap<ScreenOption, Boolean>();

        List<Member> members = app.getMembers();
        boolean membersWithLatePayments = false;

        for(int i = 0; i < members.size(); i++) {
            Member currentMember = members.get(i);
            if(currentMember.hasLatePayment().success) {
                membersWithLatePayments = true;
                break;
            }
        }

        treasurerMenu.put(new ScreenOption("Show members.", () -> showTreasurerMemberList()), true);
        treasurerMenu.put(new ScreenOption("Show members with late payments" + (membersWithLatePayments
                ? "." : " (no member with late payments)."),
                () -> showTreasurerMembersWithLatePayments()), membersWithLatePayments);

        treasurerMenu.put(new ScreenOption("↤ Log out (back to main menu).",
                () -> showMainMenu()), true);

        screenManager.showCallbackOptionsView(" － Treasurer menu － ", treasurerMenu);
    }

    private static void showTreasurerMemberList() {
        List<Member> members = app.getMembers();
        List<Discount> discounts = app.getDiscounts();
        LinkedHashMap<ScreenTableOption, Boolean> options = new LinkedHashMap<ScreenTableOption, Boolean>();

        for(int i = 0; i < members.size(); i++) {
            Member currentMember = members.get(i);
            String noDiscounts = "no discount applied";
            String currentMemberDiscounts = noDiscounts;

            // Loop throw all the discounts and create a string for the discounts label
            for (int j = 0; j < discounts.size(); j++) {
                Discount currentDiscount = discounts.get(j);

                if (currentMember.hasDiscount(currentDiscount)) {
                    if (currentMemberDiscounts.equals(noDiscounts)) {
                        currentMemberDiscounts = ScreenManager.parseType(currentDiscount.getType());
                    } else {
                        currentMemberDiscounts += ", " + ScreenManager.parseType(currentDiscount.getType());
                    }
                }
            }

            LinkedHashMap<String, String> row = new LinkedHashMap<String, String>();
            row.put("First name", currentMember.firstName);
            row.put("Last name", currentMember.lastName);
            row.put("CPR number", currentMember.CPRNumber);
            row.put("Discounts", currentMemberDiscounts);

            options.put(new ScreenTableOption(row, () -> {
                showTreasurerMemberActions(currentMember);
            }), true);
        }

        screenManager.showCallbackOptionsTableView(" － [Treasurer] Member list － ", options);
    }

    private static void showTreasurerMemberActions(Member member) {
        LinkedHashMap<ScreenOption, Boolean> treasurerMemberActions = new LinkedHashMap<ScreenOption, Boolean>();

        List<Discount> discounts = app.getDiscounts();
        boolean discountsAvailable = false;

        for(int i = 0; i < discounts.size(); i++) {
            Discount currentDiscount = discounts.get(i);
            // If the discount is applicable and the member doesn't have it yet
            // we show the discount menu
            if(currentDiscount.checkCondition(member) && !member.hasDiscount(currentDiscount)) {
                discountsAvailable = true;
                break;
            }
        }

        treasurerMemberActions.put(new ScreenOption("Apply discount"
                + (discountsAvailable ? "." : " (no discount available)."),
                () -> showTreasurerDiscountList(member)), discountsAvailable);

        boolean hasPaidThisYear = member.hasPaidThisYear();
        treasurerMemberActions.put(new ScreenOption("Pay fee"
                + (!hasPaidThisYear ? "." : " (already paid this year's fee)."),
                () -> showTreasurerPaymentActions(member)), !hasPaidThisYear);

        treasurerMemberActions.put(new ScreenOption("↤ Treasurer menu (back to treasurer menu).",
                () -> showTreasurerMenu()), true);
        treasurerMemberActions.put(new ScreenOption("× Exit (back to main menu).",
                () -> showMainMenu()), true);

        String viewLabel = " － [Treasurer] Member actions menu for: " + member.firstName
                + " " + member.lastName + " － ";
        screenManager.showCallbackOptionsView(viewLabel, treasurerMemberActions);
    }

    private static void showTreasurerPaymentActions(Member member) {
        LinkedHashMap<ScreenOption, Boolean> options = new LinkedHashMap<ScreenOption, Boolean>();

        double feeValue = member.calculateFee();
        options.put(new ScreenOption("Accept (pay fee).",
                () -> {
                    // Accept (pay fee) option
                    member.registerPayment(new Payment(feeValue, "Member fee",
                            ZonedDateTime.now(ZoneOffset.UTC)));
                    screenManager.showInfoView("Payment registered!");
                    showTreasurerMemberActions(member);
                }), true);

        options.put(new ScreenOption("↤ Decline (back to member actions menu).",
                () -> showTreasurerMemberActions(member)), true);

        String viewLabel = " － <" + member.firstName + " " + member.lastName + "> pay fee of: " + feeValue + " － ";
        screenManager.showCallbackOptionsView(viewLabel, options);
    }

    private static void showTreasurerDiscountList(Member member) {
        List<Discount> discounts = app.getDiscounts();
        LinkedHashMap<ScreenTableOption, Boolean> options = new LinkedHashMap<ScreenTableOption, Boolean>();

        for (int i = 0; i < discounts.size(); i++) {
            Discount currentDiscount = discounts.get(i);

            LinkedHashMap<String, String> row = new LinkedHashMap<String, String>();
            row.put("Name", ScreenManager.parseType(currentDiscount.getType()));
            row.put("Modifier", (currentDiscount.getModifier() * 100) + "%");

            options.put(new ScreenTableOption(row, () -> {
                member.registerDiscount(currentDiscount);

                screenManager.showInfoView("Discount applied!");
                showTreasurerMemberActions(member);
            }), !member.hasDiscount(currentDiscount));
        }

        screenManager.showCallbackOptionsTableView(" － [Treasurer] Discount list － ", options);
    }

    private static void showTreasurerMembersWithLatePayments() {
        List<Member> members = app.getMembers();
        LinkedHashMap<ScreenTableOption, Boolean> options = new LinkedHashMap<ScreenTableOption, Boolean>();

        for(int i = 0; i < members.size(); i++) {
            Member currentMember = members.get(i);
            Response hasLatePayment = currentMember.hasLatePayment();

            if(hasLatePayment.success) {
                LinkedHashMap<String, String> row = new LinkedHashMap<String, String>();
                row.put("First name", currentMember.firstName);
                row.put("Last name", currentMember.lastName);
                row.put("CPR number", currentMember.CPRNumber);
                row.put("Note", hasLatePayment.info);

                options.put(new ScreenTableOption(row, () -> {
                    showTreasurerMemberActions(currentMember);
                }), true);
            }
        }

        screenManager.showCallbackOptionsTableView(" － Member list － ", options);
    }


    /*
     * Coach views
     */
    private static void showCoachLogin() {
        while (true) {
            String username = screenManager
                    .showStringInputView(" － [Coach] username: － ", 4, 10);
            String password = screenManager
                    .showStringInputView(" － [Coach] password: － ", 4, 10);

            Response response = app.coachSignIn(username, password);
            if (response.success) {
                showCoachMenu();
                return;
            } else {
                screenManager.showErrorView("Log in error! " + response.info);
            }
        }
    }

    private static void showCoachMenu() {
        LinkedHashMap<ScreenOption, Boolean> coachMenu = new LinkedHashMap<ScreenOption, Boolean>();
        List<Discipline> disciplines = app.getDisciplines();

        boolean hasResults = false;
        // Loop throw each discipline so we know if we have results registered
        for(int i = 0; i < disciplines.size(); i++) {
            Discipline currentDiscipline = disciplines.get(i);
            LinkedHashMap<Member, LapTime> leaderboardResults = app.getLeaderboard(currentDiscipline).getResults();
            if (!leaderboardResults.isEmpty()) {
                hasResults = true;
                break;
            }
        }

        boolean hasMembers = !app.getCoachMembers().isEmpty();
        coachMenu.put(new ScreenOption("View my members"
                + (hasMembers ? "." : " (no members assigned to you)."), () -> showCoachMemberList()), hasMembers);

        coachMenu.put(new ScreenOption("View discipline leaderboards"
                + (hasResults ? "." : " (no result registered)."), () -> showCoachDisciplineList()), hasResults);
        coachMenu.put(new ScreenOption("↤ Log out (back to main menu).", () -> showMainMenu()), true);

        screenManager.showCallbackOptionsView(" － Coach menu － ", coachMenu);
    }

    private static void showCoachMemberList() {
        List<Member> members = app.getCoachMembers();
        LinkedHashMap<ScreenTableOption, Boolean> options = new LinkedHashMap<ScreenTableOption, Boolean>();

        for(int i = 0; i < members.size(); i++) {
            Member currentMember = members.get(i);

            LinkedHashMap<String, String> row = new LinkedHashMap<String, String>();
            row.put("First name", currentMember.firstName);
            row.put("Last name", currentMember.lastName);
            row.put("Discipline", ScreenManager.parseType(currentMember.preferredDiscipline.name));

            options.put(new ScreenTableOption(row, () -> {
                showCoachMemberActions(currentMember);
            }), true);
        }

        screenManager.showCallbackOptionsTableView(" － Coach Member list － ", options);
    }

    private static void showCoachMemberActions(Member member) {
        LinkedHashMap<ScreenOption, Boolean> coachMemberActions = new LinkedHashMap<ScreenOption, Boolean>();

        List<Competition> competitions = app.getCompetitions();
        boolean competitionsAvailable = false;

        for(int i = 0; i < competitions.size(); i++) {
            Competition currentCompetition = competitions.get(i);
            // If there are competitions in which the member can but is not yet registered
            // we show the competitions menu
            if(member.preferredDiscipline.equals(currentCompetition.discipline.name)
                && !currentCompetition.hasMember(member)) {
                competitionsAvailable = true;
                break;
            }
        }

        coachMemberActions.put(new ScreenOption("Available competitions"
                + (competitionsAvailable ? "." : " (no competition available)."),
                () -> {
                    // View available competitions
                    Competition selectedCompetition = showCoachCompetitionList(member);
                    selectedCompetition.registerMember(member);
                    screenManager.showInfoView(member.firstName
                            + " " + member.lastName + " has been registered to the following competition: "
                            + ScreenManager.parseType(selectedCompetition.name));
                    showCoachMemberActions(member);
                }), competitionsAvailable);

        coachMemberActions.put(new ScreenOption("Register time.", () -> showCoachMemberTimeForm(member)), true);

        coachMemberActions.put(new ScreenOption("↤ Coach menu (back to coach menu).", () -> showCoachMenu()), true);
        coachMemberActions.put(new ScreenOption("× Exit (back to main menu).", () -> showMainMenu()), true);


        String viewLabel = " － [Coach] Member actions menu for: " + member.firstName
                + " " + member.lastName + " － ";
        screenManager.showCallbackOptionsView(viewLabel, coachMemberActions);
    }

    private static Competition showCoachCompetitionList(Member member) {
        List<Competition> competitions = app.getCompetitions();
        LinkedHashMap<String, Boolean> options = new LinkedHashMap<String, Boolean>();

        // setOptionsView accepts an ArrayList of strings, so
        // loop throw all the competitions and create a string for the option label
        // also disable competitions for which the member is already registered to
        for (int i = 0; i < competitions.size(); i++) {
            Competition currentCompetition = competitions.get(i);
            boolean isRegistered = currentCompetition.hasMember(member);
            boolean isSameDiscipline = member.preferredDiscipline.name.equals(currentCompetition.discipline.name);

            if (!isSameDiscipline) {
                options.put(currentCompetition.name + " " + currentCompetition.discipline.name
                        + " (different discipline).", false);
            } else {
                options.put(currentCompetition.name + " " + currentCompetition.discipline.name
                        + (isRegistered ? " (already registered)." : "."), !isRegistered);
            }

            // Remove the selected option so we have the right index returned from showOptionsView
            if (isRegistered || !isSameDiscipline) {
                competitions.remove(i);
                i--;
            }
        }

        String viewLabel = " － [Coach] Competition list for: " + member.firstName
                + " " + member.lastName + " － ";
        int selectedCompetitionIndex = screenManager.showOptionsView(viewLabel, options);
        return competitions.get(selectedCompetitionIndex);
    }

    private static void showCoachMemberTimeForm(Member member) {
        LocalTime time = screenManager.showTimeInputView(" － [Coach > Register member lap time] Time: － ");
        ZonedDateTime date = screenManager.showDateInputView(" － [Coach > Register member lap time] Date: － ");

        screenManager.showInfoView("Time: " + time + " － Date: " + ScreenManager.parseDate(date));

        LinkedHashMap<ScreenOption, Boolean> resultTypeActionsMenu = new LinkedHashMap<ScreenOption, Boolean>();

        List<Competition> competitions = app.getCompetitions();
        boolean competitionsAvailable = false;

        for(int i = 0; i < competitions.size(); i++) {
            Competition currentCompetition = competitions.get(i);
            // If there are competitions in which the member is registered
            // we show the competitions menu
            if(currentCompetition.hasMember(member)) {
                competitionsAvailable = true;
                break;
            }
        }

        resultTypeActionsMenu.put(new ScreenOption("Competition time" + (competitionsAvailable
                ? "." : " (the user hasn't registered to any competition)."),
                () -> {
                    Competition competition = showCoachCompetitionList(member);
                    member.registerLapTime(new LapTime(time, date,
                            Long.toString(System.currentTimeMillis()), competition.name));
                    screenManager.showInfoView("Lap time registered: " + time
                            + " on " + ScreenManager.parseDate(date));

                    // Back to member actions option
                    showCoachMemberActions(member);
                }), competitionsAvailable);

        resultTypeActionsMenu.put(new ScreenOption("Individual time.",
                () -> {
                    member.registerLapTime(new LapTime(time, date,
                            Long.toString(System.currentTimeMillis()), LapTime.INDIVIDUAL_TYPE));

                    screenManager.showInfoView("Lap time registered: " + time
                            + " on " + ScreenManager.parseDate(date));

                    // Back to member actions option
                    showCoachMemberActions(member);
                }), true);

        String viewLabel = " － [Coach] Lap time result type for: " + time + " " + date + " － ";
        screenManager.showCallbackOptionsView(viewLabel, resultTypeActionsMenu);
    }

    private static void showCoachDisciplineList() {
        List<Discipline> disciplines = app.getDisciplines();
        LinkedHashMap<ScreenTableOption, Boolean> options = new LinkedHashMap<ScreenTableOption, Boolean>();

        // Display the discipline list
        for(int i = 0; i < disciplines.size(); i++) {
            Discipline currentDiscipline = disciplines.get(i);
            LinkedHashMap<Member, LapTime> leaderboardResults = app.getLeaderboard(currentDiscipline).getResults();

            LinkedHashMap<String, String> row = new LinkedHashMap<String, String>();
            row.put("Name", ScreenManager.parseType(currentDiscipline.name));
            row.put("Note", leaderboardResults.isEmpty() ? "no results" : "");

            options.put(new ScreenTableOption(row, () -> {
                showCoachDisciplineLeaderboard(currentDiscipline);
            }), !leaderboardResults.isEmpty());
        }

        screenManager.showCallbackOptionsTableView(" － Discipline list － ", options);
    }

    private static void showCoachDisciplineLeaderboard(Discipline discipline) {
        LinkedHashMap<ScreenTableOption, Boolean> results = new LinkedHashMap<ScreenTableOption, Boolean>();
        LinkedHashMap<Member, LapTime> leaderboardResults = app.getLeaderboard(discipline).getResults();

        int i = 0;
        //  Display the top 5 results
        for (Map.Entry<Member, LapTime> entry : leaderboardResults.entrySet()) {
            LinkedHashMap<String, String> row = new LinkedHashMap<String, String>();

            row.put("First name", entry.getKey().firstName);
            row.put("Last name", entry.getKey().lastName);
            row.put("Lap time", entry.getValue().time.toString());

            results.put(new ScreenTableOption(row,
                    () -> {
                        // When an option is selected, show the details and return to the coach menu
                        screenManager.showInfoView(entry.getKey().firstName
                                + " " + entry.getKey().lastName + " － time: "
                                + entry.getValue().time + " on: " + ScreenManager.parseDate(entry.getValue().date));
                        showCoachMenu();
                    }), true);
            i++;
            if (i == 5) {
                break;
            }
        }

        screenManager.showCallbackOptionsTableView(" － [Coach] Leaderboard for: "
                + ScreenManager.parseType(discipline.name) + " － ", results);
    }

    /*
     *  Helpers
     */
    private static Discipline showDisciplineList(String labelPrefix) {
        List<Discipline> disciplines = app.getDisciplines();
        LinkedHashMap<String, Boolean> options = new LinkedHashMap<String, Boolean>();

        // Loop throw all the disciplines and create a string for the option label
        for(int i = 0; i < disciplines.size(); i++) {
            Discipline currentDiscipline = disciplines.get(i);
            options.put(ScreenManager.parseType(currentDiscipline.name), true);
        }

        int selectedDisciplineIndex = screenManager.showOptionsView(" － "
                + labelPrefix + "Discipline list － ", options);
        return disciplines.get(selectedDisciplineIndex);
    }

    private static Coach showCoachList(String labelPrefix) {
        List<Coach> coaches = app.getCoaches();
        LinkedHashMap<String, Boolean> options = new LinkedHashMap<String, Boolean>();

        // Loop throw all the coaches and create a string for the option label
        for(int i = 0; i < coaches.size(); i++) {
            Coach currentCoach = coaches.get(i);
            options.put(currentCoach.username, true);
        }

        int selectedCoachIndex = screenManager.showOptionsView(" － "
                + labelPrefix + "Coach list － ", options);
        return coaches.get(selectedCoachIndex);
    }
}
