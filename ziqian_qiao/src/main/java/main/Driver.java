package main;

import app.App;
import app.EducationApp;
import app.GameApp;
import app.ProductivityApp;
import cn.hutool.core.convert.Convert;
import controllers.AppStoreAPI;
import controllers.DeveloperAPI;
import models.Developer;
import utils.ScannerInput;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class Driver {

    //     Some skeleton code has been given to you.
    //     Familiarise yourself with the skeleton code...run the menu and then review the skeleton code.
    //     Then start working through the spec.


    private DeveloperAPI developerAPI = new DeveloperAPI();

    private AppStoreAPI appStoreAPI = new AppStoreAPI();

    public static void main(String[] args) throws Exception {
        new Driver().start();
    }

    public void start() throws Exception {
        this.loadAllData();
        this.runMainMenu();
    }

    private int mainMenu() {
        System.out.println("""
                 -------------App Store------------
                |  1) Developer - Management MENU  |
                |  2) App - Management MENU        |
                |  3) Reports MENU                 |
                |----------------------------------|
                |  4) Search                       |
                |  5) Sort                         |
                |----------------------------------|
                |  6) Recommended Apps             |
                |  7) Random App of the Day        |
                |  8) Simulate ratings             |
                |----------------------------------|
                |  20) Save all                    |
                |  21) Load all                    |
                |----------------------------------|
                |  0) Exit                         |
                 ----------------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runMainMenu() throws Exception {
        int option = this.mainMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> this.runDeveloperMenu();
                case 2 -> this.runAppStoreMenu();
                case 3 -> this.runReportMenu();
                case 4 -> this.searchAppsBySpecificCriteria();
                case 5 -> this.sortAndPrintApps();
                case 6 -> System.out.println(this.appStoreAPI.listAllRecommendedApps());
                case 7 -> System.out.println(this.appStoreAPI.randomApp());
                case 8 -> this.simulateRatings();
                case 20 -> this.saveAllData();
                case 21 -> this.loadAllData();
                default -> System.out.println("Invalid option entered: " + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = this.mainMenu();
        }
        this.exitApp();
    }

    private void sortAndPrintApps() {
        this.appStoreAPI.sortAppsByNameAscending();
        System.out.println(this.appStoreAPI.listAllApps());
        System.out.println("app sort finished");
    }


    private void exitApp() {
        this.saveAllData();
        System.out.println("Exiting....");
        System.exit(0);
    }

    //--------------------------------------------------
    //  Report - Menu Items
    //--------------------------------------------------
    private void runReportMenu() {
        int option = this.reportMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> System.out.println(this.developerAPI.listDevelopers());
                case 2 -> System.out.println(this.appStoreAPI.listAllApps());
                case 3 -> System.out.println(this.appStoreAPI.listAllEducationApps());
                case 4 -> System.out.println(this.appStoreAPI.listAllGameApps());
                case 5 -> System.out.println(this.appStoreAPI.listAllProductivityApps());
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = this.reportMenu();
        }
    }

    private int reportMenu() {
        System.out.println("""
                 -------Report Menu----------
                |   1) List all developers   |
                |   2) List all apps         |
                |   3) List Education apps   |
                |   4) List Game apps        |
                |   5) List Productivity apps|
                |   0) RETURN to main menu   |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    //--------------------------------------------------
    //  app.App Management - Menu Items
    //--------------------------------------------------

    private void runAppStoreMenu() throws Exception {
        int option = this.appStoreMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> this.addApp();
                case 2 -> System.out.println(this.appStoreAPI.listAllApps());
                case 3 -> this.updateApp();
                case 4 -> this.deleteApp();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = this.appStoreMenu();
        }
    }

    private void deleteApp() {
        System.out.println("Which app do you want to delete?");
        System.out.println(this.appStoreAPI.listAllApps());
        int index = ScannerInput.validNextInt("Please enter the app Index: ");
        if (this.appStoreAPI.deleteAppByIndex(index) != null) {
            System.out.println("Delete successful");
        } else {
            System.out.println("Delete not successful");
        }
    }


    private int appStoreMenu() {
        System.out.println("""
                 -------App Store Menu-------
                |   1) Add an app            |
                |   2) List all apps         |
                |   3) Update app            |
                |   4) Delete app            |
                |   0) RETURN to main menu   |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private int appTypeMenu() {
        System.out.println("""
                 -------App Type Menu-------
                |   1) Education app        |
                |   2) Game app             |
                |   3) Productivity app     |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void addApp() {

        //no developer exist
        if (this.developerAPI.getDevelopers()
                             .isEmpty()) {
            System.out.println("no developer exist,please add developer first");
        }
        boolean isAdded = false;

        //1. add developer
        Developer developer = this.getAppDeveloper();
        if (developer == null) {
            return;
        }

        //2. enter the necessary information
        String appName = ScannerInput.validNextLine("Enter the app name: ");
        if (!this.checkAppNameIsExist(appName)) {
            return;
        }
        double appSize = ScannerInput.validNextDouble("Enter the app appSize: ");
        double appVersion = ScannerInput.validNextDouble("Enter the app appVersion: ");
        double appCost = ScannerInput.validNextDouble("Enter the app appCost: ");

        //3.select app type
        System.out.println("select app type");
        int option = this.appTypeMenu();
        switch (option) {
            //education app
            case 1 -> {
                int level = ScannerInput.validNextInt("Enter the app level: ");
                isAdded = this.appStoreAPI.addApp(new EducationApp(developer, appName, appSize, appVersion, appCost, level));
            }
            //Game app
            case 2 -> {
                String multi = ScannerInput.validNextLine("Is Multiplayer?(Y:true/others:false) : ");
                boolean isMultiplayer = "Y".equalsIgnoreCase(multi);
                isAdded = this.appStoreAPI.addApp(new GameApp(developer, appName, appSize, appVersion, appCost, isMultiplayer));
            }
            //Productivity app
            case 3 -> {
                isAdded = this.appStoreAPI.addApp(new ProductivityApp(developer, appName, appSize, appVersion, appCost));
            }
            default -> System.out.println("Invalid option entered" + option);
        }


        if (isAdded) {
            System.out.println("App Added Successfully");
        } else {
            System.out.println("No App Added");
        }

    }

    private Developer getAppDeveloper() {
        System.out.println("select developer by name");
        System.out.println(this.developerAPI.listDevelopers());
        Developer developer = this.readValidDeveloperByName();
        if (developer == null) {
            System.err.println("Developer name is NOT valid");
            return null;
        }
        return developer;
    }

    private boolean checkAppNameIsExist(String newName) {
        List<App> allApps = this.appStoreAPI.getApps();

        List<String> names = allApps.stream()
                                    .map(App::getAppName)
                                    .toList();
        if (names.contains(newName)) {
            System.err.println("appName " + newName + " is exist!");
            return false;
        }
        return true;
    }

    private void updateApp() throws Exception {
        System.out.println("Which app do you want to update?");
        System.out.println(this.appStoreAPI.listAllApps());

        //1.Select the app you want to update
        App app = this.readValidAppByIndex();
        if (app == null) {
            System.err.println("App index is NOT valid");
            return;
        }
        //2.Gets all the fields that the app can update
        List<String> appFields = this.getAppFields(app);

        System.out.println(appFields);
        //3.Select the fields to update
        String updateFieldName = ScannerInput.validNextLine("Which field do you want to update?");
        if (!appFields.contains(updateFieldName)) {
            System.err.println("field not exist:" + updateFieldName);
            return;
        }
        //4-1.If update developer, select the new developer
        if ("developer".equals(updateFieldName)) {
            Developer developer = this.getAppDeveloper();
            if (developer == null) {
                return;
            }
            app.setDeveloper(developer);
        } else if ("ratings".equals(updateFieldName)) {
            //4-2.Don't update ratings
            System.err.println("Don't update ratings");
            return;
        } else {
            //4-3.Update other fields
            Field updateField;
            try {
                updateField = app.getClass()
                                 .getDeclaredField(updateFieldName);

            } catch (NoSuchFieldException e) {
                updateField = app.getClass()
                                 .getSuperclass()
                                 .getDeclaredField(updateFieldName);
            }

            //Fetch new value
            String inputNewValue = ScannerInput.validNextLine("Please enter your new " + updateFieldName + ":");
            //If you update the appName, you need to verify uniqueness
            if ("appName".equals(updateFieldName)) {
                if (!this.checkAppNameIsExist(inputNewValue)) {
                    return;
                }
            }
            //Converts the input to the specified type
            Class<?> type = updateField.getType();
            updateField.setAccessible(true);
            Object newValue;
            try {
                newValue = Convert.convert(type, inputNewValue);
            } catch (Exception e) {
                System.err.println(inputNewValue + " can not cast to target type!");
                return;
            }
            //updateField
            updateField.set(app, newValue);
        }


        //print out
        System.out.println(updateFieldName + " update success");
        System.out.println("updated app info: " + app);


    }

    private List<String> getAppFields(App app) {
        List<String> superFields = Arrays.stream(app.getClass()
                                                    .getSuperclass()
                                                    .getDeclaredFields())
                                         .map(Field::getName)
                                         .toList();
        List<String> childFields = Arrays.stream(app.getClass()
                                                    .getDeclaredFields())
                                         .map(Field::getName)
                                         .toList();
        return Stream.of(superFields, childFields)
                     .flatMap(Collection::stream)
                     .toList();
    }

    private App readValidAppByIndex() {
        int appIndex = ScannerInput.validNextInt("Please enter the App's index: ");
        if (this.appStoreAPI.isValidIndex(appIndex)) {
            return this.appStoreAPI.getAppByIndex(appIndex);
        } else {
            return null;
        }
    }

    //--------------------------------------------------
    //  models.Developer Management - Menu Items
    //--------------------------------------------------
    private int developerMenu() {
        System.out.println("""
                 -------Developer Menu-------
                |   1) Add a developer       |
                |   2) List developer        |
                |   3) Update developer      |
                |   4) Delete developer      |
                |   0) RETURN to main menu   |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runDeveloperMenu() {
        int option = this.developerMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> this.addDeveloper();
                case 2 -> System.out.println(this.developerAPI.listDevelopers());
                case 3 -> this.updateDeveloper();
                case 4 -> this.deleteDeveloper();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = this.developerMenu();
        }
    }


    private void addDeveloper() {
        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
        String developerWebsite = ScannerInput.validNextLine("Please enter the developer website: ");

        if (this.developerAPI.addDeveloper(new Developer(developerName, developerWebsite))) {
            System.out.println("Add successful");
        } else {
            System.out.println("Add not successful");
        }
    }

    private void updateDeveloper() {
        System.out.println(this.developerAPI.listDevelopers());
        Developer developer = this.readValidDeveloperByName();
        if (developer != null) {
            String developerWebsite = ScannerInput.validNextLine("Please enter new website: ");
            if (this.developerAPI.updateDeveloperWebsite(developer.getDeveloperName(), developerWebsite)) {
                System.out.println("Developer Website Updated");
            } else {
                System.out.println("Developer Website NOT Updated");
            }
        } else {
            System.out.println("Developer name is NOT valid");
        }
    }

    private void deleteDeveloper() {
        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
        if (this.developerAPI.removeDeveloper(developerName) != null) {
            System.out.println("Delete successful");
        } else {
            System.out.println("Delete not successful");
        }
    }

    private Developer readValidDeveloperByName() {
        String developerName = ScannerInput.validNextLine("Please enter the developer's name: ");
        if (this.developerAPI.isValidDeveloper(developerName)) {
            return this.developerAPI.getDeveloperByName(developerName);
        } else {
            return null;
        }
    }


    //--------------------------------------------------
    // UNCOMMENT THIS CODE as you start working through this class
    //--------------------------------------------------
    private void searchAppsBySpecificCriteria() {
        System.out.println("""
                What criteria would you like to search apps by:
                  1) App Name
                  2) Developer Name
                  3) Rating (all apps of that rating or above)""");
        int option = ScannerInput.validNextInt("==>> ");
        switch (option) {
            //  Search methods below
            case 1 -> this.searchAppsByName();
            case 2 -> this.searchAppsByDeveloper(this.readValidDeveloperByName());
            case 3 -> this.searchAppsEqualOrAboveAStarRating();
            default -> System.out.println("Invalid option");
        }
    }

    private void searchAppsEqualOrAboveAStarRating() {
        int rating = ScannerInput.validNextInt("Enter the app rating: ");
        System.out.println(this.appStoreAPI.listAllAppsAboveOrEqualAGivenStarRating(rating));

    }

    private void searchAppsByDeveloper(Developer developer) {
        if (developer == null) {
            System.err.println("developer not exist");
            return;
        }
        List<App> appsByDeveloperName = this.appStoreAPI.getAppsByDeveloperName(developer.getDeveloperName());
        if (appsByDeveloperName.isEmpty()) {
            System.out.println("developer has no apps");
            return;
        }
        for (App app : appsByDeveloperName) {
            System.out.println(app);
        }

    }

    private void searchAppsByName() {
        String appName = ScannerInput.validNextLine("Enter the app name: ");
        App appByName = this.appStoreAPI.getAppByName(appName);
        System.out.println(appByName);
    }

    //--------------------------------------------------
    //   UNCOMMENT THIS COMPLETED CODE as you start working through this class
    //--------------------------------------------------
    private void simulateRatings() {
//         simulate random ratings for all apps (to give data for recommended apps and reports etc).
        if (this.appStoreAPI.numberOfApps() > 0) {
            System.out.println("Simulating ratings...");
            this.appStoreAPI.simulateRatings();
            System.out.println(this.appStoreAPI.listSummaryOfAllApps());
        } else {
            System.out.println("No apps");
        }
    }

    //--------------------------------------------------
    //  Persistence Menu Items
    //--------------------------------------------------

    private void saveAllData() {
        //   try-catch to save the developers to XML file

        try {
            System.out.println("Saving to file: " + this.developerAPI.fileName());
            this.developerAPI.save();
        } catch (Exception e) {
            System.err.println("Error writing to file: " + e);
        }


        // try-catch to save the apps in the store to XML file

        try {
            System.out.println("Saving to file: " + this.appStoreAPI.fileName());
            this.appStoreAPI.save();
        } catch (Exception e) {
            System.err.println("Error writing to file: " + e);
        }
    }

    private void loadAllData() {
        //  try-catch to load the developers from XML file
        try {
            System.out.println("Loading from file: " + this.developerAPI.fileName());
            this.developerAPI.load();
        } catch (Exception e) {
            System.err.println("Error reading from file: " + e);
        }


        //  try-catch to load the apps in the store from XML file

        try {
            System.out.println("Loading from file: " + this.appStoreAPI.fileName());
            this.appStoreAPI.load();
        } catch (Exception e) {
            System.err.println("Error reading from file: " + e);
        }

    }

}
