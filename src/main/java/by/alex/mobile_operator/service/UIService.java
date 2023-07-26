package by.alex.mobile_operator.service;

import by.alex.mobile_operator.entity.plan.Plan;
import by.alex.mobile_operator.entity.plan.enums.PlanType;
import by.alex.mobile_operator.entity.planFilter.PlanFilter;
import by.alex.mobile_operator.entity.user.Info;
import by.alex.mobile_operator.entity.user.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class UIService {
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private final PlanService consoleService = PlanService.getInstance();
    private final UserService userService = UserService.getInstance();
    private boolean isActive = true;
    private boolean isLogged = false;
    private User user;

    public void start() throws IOException {
        System.out.println("Hi!");
        System.out.println("""
                You can :
                    1. Browse the catalogue of the plans;
                    2. Sort plans by price or type;
                    3. Select one plan and connect it to your account (only if logged-in);
                    4. Login and rule your profile (only if logged-in);
                    5. Browse your profile (only if logged-in);
                    6. Logout.
                """);
        while (isActive) {
            System.out.println("You're on the main page.");
            switch (bufferedReader.readLine()) {
                case "1" -> browseCatalogueOfThePlans().forEach(System.out::println);
                case "2" -> sortPlans();
                case "3" -> connectPlanToAccount();
                case "4" -> authenticate();
                case "6" -> logout();
                case "exit" -> isActive = false;
                default -> System.err.println("Wrong input! Check your data.");
            }

        }
    }

    private List<Plan> browseCatalogueOfThePlans() {
        return consoleService.browseCatalogue();
    }

    private void sortPlans() throws IOException {
        System.out.println("""
                Choose sorting type:
                    1. Sort by price ascending;
                    2. Sort by price descending (doesn't work);
                    3. Sort by price range;
                    4. Sort by type.
                """);

        switch (bufferedReader.readLine()) {
            case "1" -> sortByPriceAscending().forEach(System.out::println);
            case "2" -> sortByPriceDescending().forEach(System.out::println);
            case "3" -> sortByPriceRange().forEach(System.out::println);
            case "4" -> sortCatalogueByPlanType().forEach(System.out::println);
        }
    }

    private List<Plan> sortByPriceAscending() {
        return consoleService.sortPlansBySubscriptionFee();
    }

    private List<Plan> sortByPriceDescending() {
        return consoleService.sortByPriceDescending();
    }

    private List<Plan> sortByPriceRange() throws IOException {
        System.out.println("Choose the range: from ___ to ___.");
        PlanFilter planFilter = PlanFilter.builder()
                .subscriptionFeeFrom(Double.parseDouble(bufferedReader.readLine()))
                .subscriptionFeeTo(Double.parseDouble(bufferedReader.readLine()))
                .build();

        return consoleService.sortPlansByPriceRange(planFilter);
    }

    private List<Plan> sortCatalogueByPlanType() throws IOException {
        System.out.println("""
                Select interested plan:
                    1. Internet plans;
                    2. Phone plans;
                    3. TV plans.
                """);
        PlanFilter planFilter = PlanFilter.builder()
                .planType(selectPlanType())
                .build();

        return consoleService.sortPlansByType(planFilter);
    }

    private PlanType selectPlanType() throws IOException {
        PlanType planType = null;

        switch (bufferedReader.readLine()) {
            case "1" -> planType = PlanType.INTERNET;
            case "2" -> planType = PlanType.PHONE;
            case "3" -> planType = PlanType.TV;
        }

        return planType;
    }

    private void connectPlanToAccount() throws IOException {
        if (isLogged) {
            user.setPlan(selectPlan());
            userService.updateUser(user);
        } else {
            System.out.println("Login or register and try again.");
        }
    }

    private Plan selectPlan() throws IOException {
        System.out.println("Choose the name of the plan you like: ");

        return consoleService.getPlanByName(bufferedReader.readLine())
                .orElse(null);
    }

    private void authenticate() throws IOException {
        System.out.println("""
                Login or register:
                    1. Login;
                    2. Register.
                """);
        switch (bufferedReader.readLine()) {
            case "1" -> login();
            case "2" -> register();
        }
    }

    private void register() throws IOException {
        var isActiveRegistration = true;
        user = User.builder()
                .info(new Info())
                .build();
        System.out.println("""
                Input your data:
                    1. Name;
                    2. Surname;
                    3. Passport number;
                    4. Birthday (yyyy-mm-dd);
                    5. Password;
                    6. Finish registration;
                    7. Go back to main menu;
                """);

        while (isActiveRegistration) {
            switch (bufferedReader.readLine()) {
                case "1" -> user.getInfo().setName(bufferedReader.readLine());
                case "2" -> user.getInfo().setSurname(bufferedReader.readLine());
                case "3" -> user.getInfo().setPassportNo(bufferedReader.readLine());
                case "4" -> user.getInfo().setBirthday(
                        LocalDate.parse(
                                bufferedReader.readLine(),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        )
                );
                case "5" -> user.getInfo().setPassword(bufferedReader.readLine());
                case "6" -> finishRegistration();
                case "7" -> isActiveRegistration = false;
                default -> System.err.println("Wrong input! Check your data.");
            }
        }
    }

    private void finishRegistration() {
        if (user.getInfo().getName() != null
                && user.getInfo().getPassword() !=null) {
            userService.saveUser(user);
            isLogged = true;
        } else {
            System.err.println("If you want to finish registration, you should input username and password.");
        }
    }

    private void login() throws IOException {
        System.out.println("Input username and password:");

        var username = bufferedReader.readLine();
        var password = bufferedReader.readLine();
        Optional<User> activeUser = userService.login(username, password);
        activeUser.ifPresentOrElse(
                value -> user = value,
                () -> System.err.println("Wrong username or password! Try again or register."));
    }

    private void logout() {
        if (isLogged) {
            user = null;
            isLogged = false;
            System.out.println("You logged-out.");
        } else {
            System.out.println("You didn't login.");
        }
    }
}
