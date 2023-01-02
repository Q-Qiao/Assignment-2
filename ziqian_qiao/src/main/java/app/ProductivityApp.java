package app;

import models.Developer;



public class ProductivityApp extends App {

    public ProductivityApp(Developer developer, String appName, double appSize, double appVersion, double appCost) {
        super(developer, appName, appSize, appVersion, appCost);
    }

    /**
     * whether recommend the app
     * <p>
     * if: appCost>=1.99 and calcRating>3.0
     * <p>
     *
     * @return boolean
     */
    @Override
    public boolean isRecommendedApp() {
        return this.getAppCost() >= 1.99 && this.calculateRating() > 3.0;
    }


    @Override
    public String toString() {
        return super.toString();
    }


}
