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

import static by.alex.mobile_operator.util.Message.Error.*;
import static by.alex.mobile_operator.util.Message.Instruction.*;
import static by.alex.mobile_operator.util.Message.SystemMessage.LOGOUT;
import static by.alex.mobile_operator.util.Message.UserInput.BACK;
import static by.alex.mobile_operator.util.Message.printErrorMessage;
import static by.alex.mobile_operator.util.Message.printInstructionMessage;

public class UIService {
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private final PlanService consoleService = PlanService.getInstance();
    private final UserService userService = UserService.getInstance();
    private boolean isActive = true;
    private boolean isActiveAction = true;
    private boolean isLogged = false;
    private User user;

    public void start() throws IOException {
        printInstructionMessage(INTRODUCTION);

        while (isActive) {
            printInstructionMessage(MAIN_PAGE);

            switch (bufferedReader.readLine()) {
                case "1" -> browseCatalogueOfThePlans().forEach(System.out::println);
                case "2" -> sortPlans();
                case "3" -> connectPlanToAccount();
                case "4" -> authenticate();
                case "5" -> browseProfile();
                case "6" -> logout();
                case "exit" -> isActive = false;
                default -> printErrorMessage(WRONG_INPUT);
            }
        }
    }

    private List<Plan> browseCatalogueOfThePlans() {
        return consoleService.browseCatalogue();
    }

    private void sortPlans() throws IOException {
        printInstructionMessage(SORTING);
        isActiveAction = true;

        while (isActiveAction) {
            switch (bufferedReader.readLine()) {
                case "1" -> sortByPriceAscending().forEach(System.out::println);
                case "2" -> sortByPriceDescending().forEach(System.out::println);
                case "3" -> sortByPriceRange().forEach(System.out::println);
                case "4" -> sortCatalogueByPlanType().forEach(System.out::println);
                case "back" -> isActiveAction = false;
                default -> printErrorMessage(WRONG_INPUT);
            }
        }
    }

    private List<Plan> sortByPriceAscending() {
        return consoleService.sortPlansBySubscriptionFee();
    }

    private List<Plan> sortByPriceDescending() {
        return consoleService.sortByPriceDescending();
    }

    private List<Plan> sortByPriceRange() throws IOException {
        printInstructionMessage(SORTING_BY_PRICE);
        PlanFilter planFilter = PlanFilter.builder()
                .subscriptionFeeFrom(Double.parseDouble(bufferedReader.readLine()))
                .subscriptionFeeTo(Double.parseDouble(bufferedReader.readLine()))
                .build();

        return consoleService.sortPlansByPriceRange(planFilter);
    }

    private List<Plan> sortCatalogueByPlanType() throws IOException {
        printInstructionMessage(SORTING_BY_PLAN_TYPE);
        PlanFilter planFilter = PlanFilter.builder()
                .planType(selectPlanType())
                .build();

        return consoleService.sortPlansByType(planFilter);
    }

    private PlanType selectPlanType() throws IOException {
        PlanType planType = null;

        while (isActiveAction) {
            switch (bufferedReader.readLine()) {
                case "1" -> planType = PlanType.INTERNET;
                case "2" -> planType = PlanType.PHONE;
                case "3" -> planType = PlanType.TV;
                case "back" -> sortPlans();
                default -> printErrorMessage(WRONG_INPUT);
            }
        }

        return planType;
    }

    private void connectPlanToAccount() throws IOException {
        if (isLogged) {
            user.setPlan(selectPlan());
            userService.updateUser(user);
        } else {
            printErrorMessage(NOT_LOGGED_IN);
        }
    }

    private Plan selectPlan() throws IOException {
        printInstructionMessage(PLAN_SELECTION);
        Optional<Plan> plan = consoleService.getPlanByName(bufferedReader.readLine());

        while (plan.isEmpty()) {
            printErrorMessage(PLAN_NOT_EXISTS);
            plan = consoleService.getPlanByName(bufferedReader.readLine());
        }

        return plan.get();
    }

    private void authenticate() throws IOException {
        printInstructionMessage(AUTHENTICATION);
        isActiveAction = true;

        while (isActiveAction) {
            switch (bufferedReader.readLine()) {
                case "1" -> login();
                case "2" -> register();
                case BACK -> isActiveAction = false;
                default -> printErrorMessage(WRONG_INPUT);
            }
        }
    }

    private void register() throws IOException {
        user = User.builder()
                .info(new Info())
                .build();
        printInstructionMessage(REGISTRATION);

        while (isActiveAction) {
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
                case BACK -> authenticate();
                default -> printErrorMessage(WRONG_INPUT);
            }
        }
    }

    private void finishRegistration() {
        if (user.getInfo().getName() != null
                && user.getInfo().getPassword() != null) {
            userService.saveUser(user);
            isLogged = true;
        } else {
            printErrorMessage(FAIL_REGISTRATION);
        }
    }

    private void login() throws IOException {
        printInstructionMessage(LOGIN);

        var username = bufferedReader.readLine();
        var password = bufferedReader.readLine();
        Optional<User> activeUser = userService.login(username, password);

        activeUser.ifPresentOrElse(
                value -> {
                    user = value;
                    isLogged = true;
                },
                () -> {
                    printErrorMessage(FAIL_LOGIN);
                    printInstructionMessage(AUTHENTICATION);
                }
        );
    }

    private void browseProfile() {
        if (isLogged) {
            System.out.println(userService.showInfo(user));
        } else {
            printErrorMessage(BROWSE_ERROR);
        }
    }

    private void logout() {
        if (isLogged) {
            user = null;
            isLogged = false;
            printInstructionMessage(LOGOUT);
        } else {
            printErrorMessage(LOGIN_ERROR);
        }
    }
}
