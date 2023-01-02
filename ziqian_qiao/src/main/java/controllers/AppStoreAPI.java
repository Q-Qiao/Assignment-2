package controllers;

import app.App;
import app.EducationApp;
import app.GameApp;
import app.ProductivityApp;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import models.Developer;
import models.Rating;
import utils.ISerializer;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static utils.RatingUtility.generateRandomRating;

public class AppStoreAPI implements ISerializer {

    private List<App> apps = new ArrayList<>();

    // refer to the spec and add in the required methods here (make note of which methods are given to you first!)

    /**
     * add the app
     *
     * @param newApp the new app
     *
     * @return boolean
     */
    public boolean addApp(App newApp) {
        return this.apps.add(newApp);
    }

    /**
     * delete the application by index
     *
     * @param index index
     *
     * @return {@link App}
     */
    public App deleteAppByIndex(int index) {
        //Invalid index, null returned
        if (!this.isValidIndex(index)) {
            return null;
        }
        //Index valid, delete and return the object at the index location
        App result = this.apps.get(index);
        this.apps.remove(index);

        return result;
    }

    /**
     * get the app by index
     *
     * @param index the index of the app
     *
     * @return {@link App}
     */
    public App getAppByIndex(int index) {
        //Invalid index, null returned
        if (!this.isValidIndex(index)) {
            return null;
        }
        //Index valid, delete and return the object at the index location
        return this.apps.get(index);
    }

    /**
     * count the number of the apps
     *
     * @return int
     */
    public int numberOfApps() {
        return this.apps.size();
    }

    /**
     * Get the app by name
     *
     * @param name the name of the app
     *
     * @return {@link App}
     */
    public App getAppByName(String name) {
        //Returns if the specified object exists, ignoring case of the name
        for (App app : this.apps) {
            String appName = app.getAppName();
            if (appName.equalsIgnoreCase(name)) {
                return app;
            }
        }
        //Returns null if it does not exist
        return null;
    }

    /**
     * Get the app according to the name of the developer
     *
     * @param developerName the name of the developer
     *
     * @return {@link List}<{@link App}>
     */
    public List<App> getAppsByDeveloperName(String developerName) {

        return this.apps.stream()
                        .filter(r -> r.getDeveloper()
                                                         .getDeveloperName()
                                                         .equals(developerName))
                        .toList();
    }

    /**
     * list all apps
     *
     * @return {@link String}
     */
    public String listAllApps() {

        //if no app, return errMsg
        if (this.apps.isEmpty()) {
            return "No apps";
        }

        //if there are apps, return the details
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < this.apps.size(); i++) {
            sb.append("Index: ")
              .append(i)
              .append(" Details: ")
              .append(this.apps.get(i)
                                  .toString())
              .append(System.lineSeparator());
        }
        return sb.toString();

    }

    /**
     * list summary of all apps
     *
     * @return {@link String}
     */
    public String listSummaryOfAllApps() {
        //if there is no app, return "No apps"
        if (this.apps.isEmpty()) {
            return "No apps";
        }

        //if there are apps return the details
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < this.apps.size(); i++) {
            sb.append("Index: ")
              .append(i)
              .append(" Summary: ")
              .append(this.apps.get(i)
                               .appSummary())
              .append(System.lineSeparator());
        }
        return sb.toString();
    }

    /**
     * list all the game apps
     *
     * @return {@link String}
     */
    public String listAllGameApps() {
        return this.listAppsByType(GameApp.class, "No Game apps");
    }

    /**
     * list all the education apps
     *
     * @return {@link String}
     */
    public String listAllEducationApps() {
        return this.listAppsByType(EducationApp.class, "No Education apps");
    }

    /**
     * list all the productivity apps
     *
     * @return {@link String}
     */
    public String listAllProductivityApps() {
        return this.listAppsByType(ProductivityApp.class, "No Productivity apps");
    }

    /**
     * list apps by type
     *
     * @param destType the type of the app
     * @param errMsg   Error message when the specified type application does not exist
     *
     * @return {@link String}
     */
    private <T extends App> String listAppsByType(Class<T> destType, String errMsg) {

        List<App> destTypeApps = this.apps.stream()
                                          .filter(r -> r.getClass()
                                                        .equals(destType))
                                          .toList();

        //if there is no app, return errMsg
        if (destTypeApps.isEmpty()) {
            return errMsg;
        }

        //if there are apps return the details
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < destTypeApps.size(); i++) {
            sb.append("Index: ")
              .append(i)
              .append(" Details: ")
              .append(destTypeApps.get(i)
                                  .toString())
              .append(System.lineSeparator());
        }
        return sb.toString();

    }


    /**
     * list all apps that * contain * the given name, ignoring case
     *
     * @param name the name of the app
     *
     * @return {@link String}  <p>
     * "No apps for name appName exists" --no apps  </p>
     * appSummary --if exist
     */
    public String listAllAppsByName(String name) {
        List<String> appSummaryByContainsName = this.apps.stream()

                                                         .filter(r -> r.getAppName()
                                                                       .toUpperCase()
                                                                       .contains(name.toUpperCase()))
                                                         .map(App::appSummary)
                                                         .toList();

        if (appSummaryByContainsName.isEmpty()) {
            return "No apps for name " + name + " exists";
        }


        return this.stringListFormat(appSummaryByContainsName);
    }


    /**
     * List all apps with a rating higher than or equal to the given rating
     *
     * @param rating the rating
     *
     * @return {@link String} <p>
     * "No apps have a rating of given rating or above should be returned" --no apps exist </p>
     * app details -- if exist
     */
    public String listAllAppsAboveOrEqualAGivenStarRating(int rating) {
        List<String> appDetails = this.apps.stream()
                                           .filter(r -> r.calculateRating() >= rating)
                                           .map(App::toString)
                                           .toList();

        if (appDetails.isEmpty()) {
            return "No apps have a rating of " + rating + " or above should be returned";
        }


        return this.stringListFormat(appDetails);
    }

    /**
     * List all recommended apps
     *
     * @return {@link String} <p>"No recommended app" --There are no recommendation apps </p>
     * <p>
     * app summary --if exist
     * </p>
     */
    public String listAllRecommendedApps() {
        List<String> recommendedAppsSummary = this.apps.stream()
                                                       .filter(App::isRecommendedApp)
                                                       .map(App::appSummary)
                                                       .toList();

        if (recommendedAppsSummary.isEmpty()) {
            return "No recommended apps";
        }

        return this.stringListFormat(recommendedAppsSummary);
    }

    /**
     * List all applications developed by the specified person
     *
     * @param developer the developer of the app
     *
     * @return {@link String}<p>No apps for developer:" + developerName --no match apps</p>
     * <p>app summary --if exist</p>
     */
    public String listAllAppsByChosenDeveloper(Developer developer) {
        String developerName = developer.getDeveloperName();
        List<String> appsSummaryByDeveloperName = this.apps.stream()
                                                           .filter(r -> r.getDeveloper()
                                                                         .getDeveloperName()
                                                                         .equals(developerName))
                                                           .map(App::appSummary)
                                                           .toList();

        if (appsSummaryByDeveloperName.isEmpty()) {
            return "No apps for developer:" + developerName;
        }
        return this.stringListFormat(appsSummaryByDeveloperName);
    }

    /**
     * Format a list of strings
     *
     * @param stringList the list of the string
     *
     * @return {@link String}
     */
    private String stringListFormat(List<String> stringList) {
        StringBuilder result = new StringBuilder();
        for (String s : stringList) {
            result.append(s)
                  .append(System.lineSeparator());
        }
        return result.toString();
    }


    /**
     * Count the number of developers by the specified developer
     *
     * @param developer the developer of the app
     *
     * @return int
     */
    public int numberOfAppsByChosenDeveloper(Developer developer) {
        String developerName = developer.getDeveloperName();
        List<App> collect = this.apps.stream()
                                     .filter(r -> r.getDeveloper()
                                                   .getDeveloperName()
                                                   .equals(developerName))
                                     .toList();

        return collect.size();
    }

    /**
     * the random apps
     *
     * @return {@link App} <p>null -- no apps</p>
     */
    public App randomApp() {
        if (this.apps.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return this.apps.get(random.nextInt(this.apps.size()));
    }


    /**
     * valid app names
     *
     * @param appName the name of the app
     *
     * @return boolean
     */
    public boolean isValidAppName(String appName) {
        for (App app : this.apps) {
            if (app.getAppName()
                   .equals(appName)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Apps are sorted by name in ascending order
     */
    public void sortAppsByNameAscending() {



        for (int i = 0; i < this.apps.size() - 1; i++) {
            for (int j = i + 1; j < this.apps.size(); j++) {
                String appName1 = this.apps.get(i)
                                           .getAppName();
                String appName2 = this.apps.get(j)
                                           .getAppName();
                if (appName1.compareTo(appName2) > 0) {
                    this.swapApps(this.apps, i, j);
                }
            }
        }
    }

    /**
     * change the location of the app
     *
     * @param apps the apps
     * @param i    i
     * @param j    j
     */
    private void swapApps(List<App> apps, int i, int j) {
        Collections.swap(apps, i, j);
    }

    //---------------------
    // Method to simulate ratings (using the RatingUtility).
    // This will be called from the Driver (see skeleton code)
    //---------------------
    //  UNCOMMENT THIS COMPLETED method as you start working through this class
    //---------------------

    public void simulateRatings() {
        for (App app : this.apps) {
            app.addRating(generateRandomRating());
        }
    }

    //---------------------
    // Validation methods
    //---------------------
    //  UNCOMMENT THIS COMPlETED method as you start working through this class
    //---------------------
    public boolean isValidIndex(int index) {
        return (index >= 0) && (index < this.apps.size());
    }

    //---------------------
    // Persistence methods
    //---------------------
    // UNCOMMENT THIS COMPLETED CODE block as you start working through this class
    //---------------------

    @Override
    @SuppressWarnings("unchecked")
    public void load() throws Exception {
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[]{App.class, EducationApp.class, GameApp.class, ProductivityApp.class, Rating.class};

        //setting up the xstream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream in = xstream.createObjectInputStream(new FileReader(this.fileName()));
        this.apps = (List<App>) in.readObject();
        in.close();
    }

    @Override
    public void save() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter(this.fileName()));
        out.writeObject(this.apps);
        out.close();
    }

    @Override
    public String fileName(){
        return "apps.xml";
    }


    public List<App> getApps() {
        return this.apps;
    }
}
