package by.alex.mobile_operator.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Message {

    public class Instruction {
        public static final String INTRODUCTION = """
                Hi!
                You can :
                    1. Browse the catalogue of the plans;
                    2. Sort plans by price or type;
                    3. Select one plan and connect it to your account (only if logged-in);
                    4. Login and rule your profile (only if logged-in);
                    5. Browse your profile (only if logged-in);
                    6. Logout.
                """;
        public static final String MAIN_PAGE = "You're on the main page.";
        public static final String SORTING = """
                Choose sorting type:
                    1. Sort by price ascending;
                    2. Sort by price descending (doesn't work);
                    3. Sort by price range;
                    4. Sort by type.
                """;
        public static final String SORTING_BY_PRICE = "Choose the range: from ___ to ___.";
        public static final String SORTING_BY_PLAN_TYPE = """
                Select interested plan:
                    1. Internet plans;
                    2. Phone plans;
                    3. TV plans.
                """;
        public static final String PLAN_SELECTION = "Choose the name of the plan you like: ";
        public static final String AUTHENTICATION = """
                Login or register:
                    1. Login;
                    2. Register.
                """;
        public static final String LOGIN = "Input username and password: ";
        public static final String REGISTRATION = """
                Input your data:
                    1. Name;
                    2. Surname;
                    3. Passport number;
                    4. Birthday (yyyy-mm-dd);
                    5. Password;
                    6. Finish registration;
                    7. Go back to main menu;
                """;
    }

    public class SystemMessage {
        public static final String LOGOUT = "You logged-out.";
    }

    public class Error {
        public static final String WRONG_INPUT = "Wrong input! Check your data.";
        public static final String NOT_LOGGED_IN = "Login or register and try again.";
        public static final String FAIL_LOGIN = "Wrong username or password! Try again or register.";
        public static final String FAIL_REGISTRATION = "If you want to finish registration, you should input username and password.";
        public static final String BROWSE_ERROR = "If you want to browse a profile, you should login.";
        public static final String LOGIN_ERROR = "You didn't login.";
    }

    public static void printInstructionMessage(String message) {
        System.out.println(message);
    }

    public static void printErrorMessage(String message) {
        System.err.println(message);
    }
}
