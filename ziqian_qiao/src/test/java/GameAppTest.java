import app.GameApp;
import models.Developer;
import models.Rating;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameAppTest {

    private GameApp edAppBelowBoundary, edAppOnBoundary, edAppAboveBoundary, edAppInvalidData;
    private Developer developerLego = new Developer("Lego", "www.lego.com");
    private Developer developerSphero = new Developer("Sphero", "www.sphero.com");

    
    @BeforeEach
    void setUp() {
        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0), level(1-10).
        this.edAppBelowBoundary = new GameApp(this.developerLego, "WeDo", 1, 1.0, 0, true);
        this.edAppOnBoundary = new GameApp(this.developerLego, "Spike", 1000, 2.0, 1.99, true);
        this.edAppAboveBoundary = new GameApp(this.developerLego, "EV3", 1001, 3.5, 2.99, false);
        this.edAppInvalidData = new GameApp(this.developerLego, "", -1, 0, -1.00,false);
    }

    @AfterEach
    void tearDown() {
        this.edAppBelowBoundary = this.edAppOnBoundary = this.edAppAboveBoundary = this.edAppInvalidData = null;
        this.developerLego = this.developerSphero = null;
    }

    GameApp setupGameAppWithRating(int rating1, int rating2) {
        //setting all conditions to true
        GameApp edApp = new GameApp(this.developerLego, "WeDo", 1,
                1.0, 1.00,  true);
        edApp.addRating(new Rating(rating1, "John Doe", "Very Good"));
        edApp.addRating(new Rating(rating2, "Jane Doe", "Excellent"));

        //verifying all conditions are true for a recommended educational app]
        assertEquals(2, edApp.getRatings().size());  //two ratings are added
        assertEquals(1.0, edApp.getAppCost(), 0.01);
        assertEquals(((rating1 + rating2) / 2.0), edApp.calculateRating(), 0.01);
        assertEquals(true, edApp.getMultiplayer());

        return edApp;
    }

    @Nested
    class Getters {

        @Test
        void getDeveloper() {
            assertEquals(GameAppTest.this.developerLego, GameAppTest.this.edAppBelowBoundary.getDeveloper());
            assertEquals(GameAppTest.this.developerLego, GameAppTest.this.edAppOnBoundary.getDeveloper());
            assertEquals(GameAppTest.this.developerLego, GameAppTest.this.edAppAboveBoundary.getDeveloper());
            assertEquals(GameAppTest.this.developerLego, GameAppTest.this.edAppInvalidData.getDeveloper());
        }

        @Test
        void getAppName() {
            assertEquals("WeDo", GameAppTest.this.edAppBelowBoundary.getAppName());
            assertEquals("Spike", GameAppTest.this.edAppOnBoundary.getAppName());
            assertEquals("EV3", GameAppTest.this.edAppAboveBoundary.getAppName());
            assertEquals("", GameAppTest.this.edAppInvalidData.getAppName());
        }

        @Test
        void getAppSize() {
            assertEquals(1, GameAppTest.this.edAppBelowBoundary.getAppSize());
            assertEquals(1000, GameAppTest.this.edAppOnBoundary.getAppSize());
            assertEquals(0, GameAppTest.this.edAppAboveBoundary.getAppSize());
            assertEquals(0, GameAppTest.this.edAppInvalidData.getAppSize());
        }

        @Test
        void getAppVersion() {
            assertEquals(1.0, GameAppTest.this.edAppBelowBoundary.getAppVersion());
            assertEquals(2.0, GameAppTest.this.edAppOnBoundary.getAppVersion());
            assertEquals(3.5, GameAppTest.this.edAppAboveBoundary.getAppVersion());
            assertEquals(1.0, GameAppTest.this.edAppInvalidData.getAppVersion());
        }

        @Test
        void getAppCost() {
            assertEquals(0, GameAppTest.this.edAppBelowBoundary.getAppCost());
            assertEquals(1.99, GameAppTest.this.edAppOnBoundary.getAppCost());
            assertEquals(2.99, GameAppTest.this.edAppAboveBoundary.getAppCost());
            assertEquals(0, GameAppTest.this.edAppInvalidData.getAppCost());
        }

        @Test
        void getIsMultiplayer() {
            assertEquals(true, GameAppTest.this.edAppBelowBoundary.getMultiplayer());
            assertEquals(true, GameAppTest.this.edAppOnBoundary.getMultiplayer());
            assertEquals(false, GameAppTest.this.edAppAboveBoundary.getMultiplayer());
            assertEquals(false, GameAppTest.this.edAppInvalidData.getMultiplayer());
        }

    }

    @Nested
    class Setters {

        @Test
        void setDeveloper() {
            //no validation in models
            assertEquals(GameAppTest.this.developerLego, GameAppTest.this.edAppBelowBoundary.getDeveloper());
            GameAppTest.this.edAppBelowBoundary.setDeveloper(GameAppTest.this.developerSphero);
            assertEquals(GameAppTest.this.developerSphero, GameAppTest.this.edAppBelowBoundary.getDeveloper());
        }

        @Test
        void setAppName() {
            //no validation in models
            assertEquals("WeDo", GameAppTest.this.edAppBelowBoundary.getAppName());
            GameAppTest.this.edAppBelowBoundary.setAppName("Mindstorms");
            assertEquals("Mindstorms", GameAppTest.this.edAppBelowBoundary.getAppName());
        }

        @Test
        void setAppSize() {
            //Validation: appSize(1-1000)
            assertEquals(1, GameAppTest.this.edAppBelowBoundary.getAppSize());

            GameAppTest.this.edAppBelowBoundary.setAppSize(1000);
            assertEquals(1000, GameAppTest.this.edAppBelowBoundary.getAppSize()); //update

            GameAppTest.this.edAppBelowBoundary.setAppSize(1001);
            assertEquals(1000, GameAppTest.this.edAppBelowBoundary.getAppSize()); //no update

            GameAppTest.this.edAppBelowBoundary.setAppSize(2);
            assertEquals(2, GameAppTest.this.edAppBelowBoundary.getAppSize()); //update

            GameAppTest.this.edAppBelowBoundary.setAppSize(0);
            assertEquals(2, GameAppTest.this.edAppBelowBoundary.getAppSize()); //no update
        }

        @Test
        void setAppVersion() {
            //Validation: appVersion(>=1.0)
            assertEquals(1.0, GameAppTest.this.edAppBelowBoundary.getAppVersion());

            GameAppTest.this.edAppBelowBoundary.setAppVersion(2.0);
            assertEquals(2.0, GameAppTest.this.edAppBelowBoundary.getAppVersion()); //update

            GameAppTest.this.edAppBelowBoundary.setAppVersion(0.0);
            assertEquals(2.0, GameAppTest.this.edAppBelowBoundary.getAppVersion()); //no update

            GameAppTest.this.edAppBelowBoundary.setAppVersion(1.0);
            assertEquals(1.0, GameAppTest.this.edAppBelowBoundary.getAppVersion()); //update
        }

        @Test
        void setAppCost() {
            //Validation: appCost(>=0)
            assertEquals(0.0, GameAppTest.this.edAppBelowBoundary.getAppCost());

            GameAppTest.this.edAppBelowBoundary.setAppCost(1.0);
            assertEquals(1.0, GameAppTest.this.edAppBelowBoundary.getAppCost()); //update

            GameAppTest.this.edAppBelowBoundary.setAppCost(-1);
            assertEquals(1.0, GameAppTest.this.edAppBelowBoundary.getAppCost()); //no update

            GameAppTest.this.edAppBelowBoundary.setAppCost(0.0);
            assertEquals(0.0, GameAppTest.this.edAppBelowBoundary.getAppCost()); //update
        }

        @Test
        void setIsMultiplayer() {

            assertEquals(true, GameAppTest.this.edAppBelowBoundary.getMultiplayer());

            GameAppTest.this.edAppBelowBoundary.setMultiplayer(false);
            assertEquals(false, GameAppTest.this.edAppBelowBoundary.getMultiplayer()); //update

            GameAppTest.this.edAppBelowBoundary.setMultiplayer(true);
            assertEquals(true, GameAppTest.this.edAppBelowBoundary.getMultiplayer()); //no update

            GameAppTest.this.edAppBelowBoundary.setMultiplayer(false);
            assertEquals(false, GameAppTest.this.edAppBelowBoundary.getMultiplayer()); //update
        }

    }

    @Nested
    class ObjectStateMethods {

        @Test
        void appSummaryReturnsCorrectString() {
            GameApp edApp = GameAppTest.this.setupGameAppWithRating(3, 4);
            String stringContents = edApp.appSummary();

            assertTrue(stringContents.contains("IsMultiplayer: " + edApp.getMultiplayer()));
            assertTrue(stringContents.contains(edApp.getAppName() + "(V" + edApp.getAppVersion()));
            assertTrue(stringContents.contains(edApp.getDeveloper().toString()));
            assertTrue(stringContents.contains("€" + edApp.getAppCost()));
            assertTrue(stringContents.contains("Rating: " + edApp.calculateRating()));
        }

        @Test
        void toStringReturnsCorrectString() {
            GameApp edApp = GameAppTest.this.setupGameAppWithRating(3, 4);
            String stringContents = edApp.toString();

            assertTrue(stringContents.contains(edApp.getAppName()));
            assertTrue(stringContents.contains("(Version " + edApp.getAppVersion()));
            assertTrue(stringContents.contains(edApp.getDeveloper().toString()));
            assertTrue(stringContents.contains(edApp.getAppSize() + "MB"));
            assertTrue(stringContents.contains("Cost: " + edApp.getAppCost()));
            assertTrue(stringContents.contains("IsMultiplayer: " + edApp.getMultiplayer()));
            assertTrue(stringContents.contains("Ratings (" + edApp.calculateRating()));

            //contains list of ratings too
            assertTrue(stringContents.contains("John Doe"));
            assertTrue(stringContents.contains("Very Good"));
            assertTrue(stringContents.contains("Jane Doe"));
            assertTrue(stringContents.contains("Excellent"));
        }

    }

    @Nested
    class RecommendedApp {

        @Test
        void appIsNotRecommendedWhenIsMultiplayerIsFalse() {
//            isMultiplayer=true and calcRating>=4.0
            GameApp edApp = GameAppTest.this.setupGameAppWithRating(5, 4);

            //now setting appCost to 0.99 so app should not be recommended now
            edApp.setMultiplayer(false);
            assertFalse(edApp.isRecommendedApp());
        }

        @Test
        void appIsNotRecommendedWhenCalcRatingLessThan4() {

            GameApp edApp = GameAppTest.this.setupGameAppWithRating(3, 3);
            edApp.setMultiplayer(true);
            assertFalse(edApp.isRecommendedApp());
        }


        @Test
        void appIsRecommendedWhenAllOfTheConditionsAreTrue() {
            //setting all conditions to true with ratings of 3 and 4 (i.e. 3.5)
            GameApp edApp = GameAppTest.this.setupGameAppWithRating(5, 4);
            edApp.setMultiplayer(true);
            //verifying recommended app returns true
            assertTrue(edApp.isRecommendedApp());
        }

    }
}
