package app;

import models.Developer;
import utils.Utilities;


public class EducationApp extends App {


    /**
     * the level of the education app
     */
    private int level = 0;

    public EducationApp(Developer developer, String appName, double appSize, double appVersion, double appCost,
                        int level) {
        super(developer, appName, appSize, appVersion, appCost);
        //check and set level
        this.setLevel(level);
    }


    /**
     * whether recommend the app
     * <p>
     * if :appCost>0.99 and calcRating>=3.5 and level>=3
     * <p>
     *
     * @return boolean
     */
    @Override
    public boolean isRecommendedApp() {

        return this.getAppCost() > 0.99 && this.calculateRating() >= 3.5 && this.getLevel() >= 3;
    }


    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        if (Utilities.validRange(level, 1, 10)) {
            this.level = level;
        }
    }

    @Override
    public String appSummary() {
        return super.appSummary() + ",level " + this.getLevel();
    }

    @Override
    public String toString() {
        return super.toString() + " Level: " + this.getLevel();
    }


}
