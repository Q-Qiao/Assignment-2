package app;

import models.Developer;


public class GameApp extends App {

    /**
     * multi players
     */
    private Boolean isMultiplayer = false;

    public GameApp(Developer developer, String appName, double appSize, double appVersion, double appCost,
                   Boolean isMultiplayer) {
        super(developer, appName, appSize, appVersion, appCost);
        this.isMultiplayer = isMultiplayer;
    }

    /**
     * recommend the app
     * <p>
     * if :isMultiplayer=true and calcRating>=4.0
     *
     * @return boolean
     */
    @Override
    public boolean isRecommendedApp() {
        return this.getMultiplayer() && this.calculateRating() >= 4.0;
    }

    public Boolean getMultiplayer() {
        return this.isMultiplayer;
    }

    public void setMultiplayer(Boolean multiplayer) {
        this.isMultiplayer = multiplayer;
    }


    @Override
    public String appSummary() {
        return super.appSummary() + ",IsMultiplayer: " + this.getMultiplayer();
    }

    @Override
    public String toString() {
        return super.toString() + "IsMultiplayer: " + this.getMultiplayer();
    }


}
