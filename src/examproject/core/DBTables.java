package examproject.core;

import examproject.db.Database;
import examproject.db.TableHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DBTables {

    /*
     *  DB Tables
     */
    static void createChairmenTable() {
        // 1. Create the table
        try {
            List<String> columns = new ArrayList<String>();
            columns.add("username");
            columns.add("password");
            Database.createTable("chairmen", columns);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Add the hardcoded chairman entry
        try {
            Database.getTable("chairmen")
                    .insert(new Chairman("chairman", "chairman").deconstruct());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    static void createTreasurersTable() {
        // 1. Create the table
        try {
            List<String> columns = new ArrayList<String>();
            columns.add("username");
            columns.add("password");
            Database.createTable("treasurers", columns);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Add the hardcoded treasurer entry
        try {
            Database.getTable("treasurers")
                    .insert(new Treasurer("treasurer", "treasurer").deconstruct());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    static void createCoachesTable() {
        // 1. Create the table
        try {
            List<String> columns = new ArrayList<String>();
            columns.add("username");
            columns.add("password");
            Database.createTable("coaches", columns);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Add the hardcoded coach entries
        try {
            TableHandler table = Database.getTable("coaches");
            table.insert(new Coach("coach1", "coach1").deconstruct());
            table.insert(new Coach("coach2", "coach2").deconstruct());
            table.insert(new Coach("coach3", "coach3").deconstruct());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    static void createDisciplinesTable() {
        // 1. Create the table
        try {
            List<String> columns = new ArrayList<String>();
            columns.add("name");
            Database.createTable("disciplines", columns);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Add the hardcoded discipline entries
        try {
            TableHandler table = Database.getTable("disciplines");
            table.insert(new Discipline("FREESTYLE_100").deconstruct());
            table.insert(new Discipline("FREESTYLE_200").deconstruct());
            table.insert(new Discipline("BACKSTROKE_100").deconstruct());
            table.insert(new Discipline("BACKSTROKE_200").deconstruct());
            table.insert(new Discipline("BUTTERFLY_100").deconstruct());
            table.insert(new Discipline("BUTTERFLY_200").deconstruct());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    static void createCompetitionsTable(List<Discipline> disciplines) {
        // 1. Create the table
        try {
            List<String> columns = new ArrayList<String>();
            columns.add("name");
            columns.add("discipline_name");
            Database.createTable("competitions", columns);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Add the hardcoded competition entries
        try {
            TableHandler table = Database.getTable("competitions");

            table.insert(new Competition("WORLD_AQUATICS",
                    disciplines.get(getRandom(disciplines.size() - 1))).deconstruct());
            table.insert(new Competition("DUEL_IN_THE_POOL",
                    disciplines.get(getRandom(disciplines.size() - 1))).deconstruct());
            table.insert(new Competition("BACK_IT_UP",
                    disciplines.get(getRandom(disciplines.size() - 1))).deconstruct());
            table.insert(new Competition("LAST_ONE_ALIVE_WINS",
                    disciplines.get(getRandom(disciplines.size() - 1))).deconstruct());

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    static void createMembersTable() {
        // Create the table
        try {
            List<String> columns = new ArrayList<String>();
            columns.add("first_name");
            columns.add("last_name");
            columns.add("cpr_number");
            columns.add("date_of_birth");
            columns.add("date_of_registration");
            columns.add("is_active");
            columns.add("is_elite");
            columns.add("discipline_name");
            Database.createTable("members", columns);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    static void createDiscountMemberTable() {
        // Create the table
        try {
            List<String> columns = new ArrayList<String>();
            columns.add("member_cpr_number");
            columns.add("discount_type");
            Database.createTable("member_discount", columns);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createDiscountsTable() {
        // 1. Create the table
        try {
            List<String> columns = new ArrayList<String>();
            columns.add("modifier");
            columns.add("type");
            Database.createTable("discounts", columns);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Add the hardcoded discount entry
        try {
            Database.getTable("discounts")
                    .insert(new SeniorDiscount(0.25).deconstruct());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    static void createPaymentMemberTable() {
        // Create the table
        try {
            List<String> columns = new ArrayList<String>();
            columns.add("member_cpr_number");
            columns.add("payment_date");
            Database.createTable("member_payment", columns);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createPaymentsTable() {
        // Create the table
        try {
            List<String> columns = new ArrayList<String>();
            columns.add("amount");
            columns.add("details");
            columns.add("date");
            Database.createTable("payments", columns);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createCoachMemberTable () {
        // Create the table
        try {
            List<String> columns = new ArrayList<String>();
            columns.add("member_cpr_number");
            columns.add("coach_username");
            Database.createTable("member_coach", columns);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createCompetitionMemberTable () {
        // Create the table
        try {
            List<String> columns = new ArrayList<String>();
            columns.add("member_cpr_number");
            columns.add("competition_name");
            Database.createTable("member_competition", columns);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     *  Helpers
     */
    private static int getRandom(int max) {
        return ThreadLocalRandom.current().nextInt(0, max + 1);
    }
}
