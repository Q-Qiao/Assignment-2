package app;

import models.Developer;
import models.Rating;
import utils.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

public abstract class App {
    private Developer developer;

    private String appName = "No app name";

    private double appSize = 0;


    private double appVersion = 1.0;

    private double appCost = 0;

    private List<Rating> ratings = new ArrayList<>();


    public App(Developer developer, String appName, double appSize, double appVersion, double appCost) {
        this.developer = developer;
        this.appName = appName;
        //use setter validation
        this.setAppSize(appSize);
        this.setAppVersion(appVersion);
        this.setAppCost(appCost);
    }

    /**
     * whether recommend the app
     *
     * @return boolean
     */
    public abstract boolean isRecommendedApp();


    /**
     * the summary of the app
     *
     * @return {@link String}
     */
    public String appSummary() {
        String messageFormat = "%s(V%f) by %s, €%f. Rating: %f";
        String appName = this.getAppName();
        double appVersion = this.getAppVersion();
        String developerName = this.getDeveloper()
                                   .toString();
        double appCost = this.getAppCost();
        double rating = this.calculateRating();

        return String.format(messageFormat, appName, appVersion, developerName, appCost, rating);
    }

    /**
     * add rating to ArrayList
     *
     * @param newRating new rating
     */
    public void addRating(Rating newRating) {
        this.ratings.add(newRating);
    }


    /**
     * Build and return a string containing all the ratings
     *
     * @return {@link String} "No Ratings added yet" -- no rating
     */
    public String listRating() {
        if (this.ratings.isEmpty()) {
            return "No Ratings added yet";
        }

        return this.ratings.toString();
    }


    /**
     * count and return the average of numberOfStars
     * <p>
     * except numberOfStars = 0
     *
     * @return double
     */
    public double calculateRating() {
        if (this.ratings.isEmpty()) {
            return 0;
        }

        OptionalDouble average = this.ratings.stream()
                                             .map(Rating::getNumberOfStars)
                                             .filter(numberOfStars -> numberOfStars != 0)
                                             .mapToInt(Integer::intValue)
                                             .average();

        if (average.isPresent()) {
            return average.getAsDouble();
        } else {
            return 0;
        }

    }


    //-----getter and setters, including setter validation---//

    public Developer getDeveloper() {
        return this.developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public double getAppSize() {
        return this.appSize;
    }

    public void setAppSize(double appSize) {
        //check app size
        if (Utilities.validRange(appSize, 1, 1000)) {
            this.appSize = appSize;
        }

    }

    public double getAppVersion() {
        return this.appVersion;
    }

    public void setAppVersion(double appVersion) {
        //check app version
        if (Utilities.greaterThanOrEqualTo(appVersion, 1.0)) {
            this.appVersion = appVersion;
        }

    }

    public double getAppCost() {
        return this.appCost;
    }

    public void setAppCost(double appCost) {
        //check app cost
        if (appCost >= 0) {
            this.appCost = appCost;
        }
    }

    public List<Rating> getRatings() {
        return this.ratings;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getAppName())
                .append(" Developer: ")
          .append(this.getDeveloper()
                      .toString())
          .append("(Version ")
          .append(this.getAppVersion())
          .append(")")
          .append("Size:")
          .append(this.getAppSize())
          .append("MB ")
          .append("Cost: ")
          .append(this.getAppCost())
          .append("CalculateRating: ")
          .append(this.calculateRating())
          .append(" Ratings (")
          .append(this.getRatings())
          .append(")");


        return sb.toString();
    }
}
