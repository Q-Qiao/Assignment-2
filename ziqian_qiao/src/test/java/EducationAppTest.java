import app.EducationApp;
import models.Developer;
import models.Rating;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EducationAppTest {

    private EducationApp edAppBelowBoundary, edAppOnBoundary, edAppAboveBoundary, edAppInvalidData;
    private Developer developerLego = new Developer("Lego", "www.lego.com");
    private Developer developerSphero = new Developer("Sphero", "www.sphero.com");

    // TODO Nothing!  This class is complete
    
    @BeforeEach
    void setUp() {
        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0), level(1-10).
        this.edAppBelowBoundary = new EducationApp(this.developerLego, "WeDo", 1, 1.0, 0, 1);
        this.edAppOnBoundary = new EducationApp(this.developerLego, "Spike", 1000, 2.0, 1.99, 10);
        this.edAppAboveBoundary = new EducationApp(this.developerLego, "EV3", 1001, 3.5, 2.99, 11);
        this.edAppInvalidData = new EducationApp(this.developerLego, "", -1, 0, -1.00,0);
    }

    @AfterEach
    void tearDown() {
        this.edAppBelowBoundary = this.edAppOnBoundary = this.edAppAboveBoundary = this.edAppInvalidData = null;
        this.developerLego = this.developerSphero = null;
    }

    EducationApp setupEducationAppWithRating(int rating1, int rating2) {
        //setting all conditions to true
        EducationApp edApp = new EducationApp(this.developerLego, "WeDo", 1,
                1.0, 1.00,  3);
        edApp.addRating(new Rating(rating1, "John Doe", "Very Good"));
        edApp.addRating(new Rating(rating2, "Jane Doe", "Excellent"));

        //verifying all conditions are true for a recommended educational app]
        assertEquals(2, edApp.getRatings().size());  //two ratings are added
        assertEquals(1.0, edApp.getAppCost(), 0.01);
        assertEquals(((rating1 + rating2) / 2.0), edApp.calculateRating(), 0.01);
        assertEquals(3, edApp.getLevel());

        return edApp;
    }

    @Nested
    class Getters {

        @Test
        void getDeveloper() {
            assertEquals(EducationAppTest.this.developerLego, EducationAppTest.this.edAppBelowBoundary.getDeveloper());
            assertEquals(EducationAppTest.this.developerLego, EducationAppTest.this.edAppOnBoundary.getDeveloper());
            assertEquals(EducationAppTest.this.developerLego, EducationAppTest.this.edAppAboveBoundary.getDeveloper());
            assertEquals(EducationAppTest.this.developerLego, EducationAppTest.this.edAppInvalidData.getDeveloper());
        }

        @Test
        void getAppName() {
            assertEquals("WeDo", EducationAppTest.this.edAppBelowBoundary.getAppName());
            assertEquals("Spike", EducationAppTest.this.edAppOnBoundary.getAppName());
            assertEquals("EV3", EducationAppTest.this.edAppAboveBoundary.getAppName());
            assertEquals("", EducationAppTest.this.edAppInvalidData.getAppName());
        }

        @Test
        void getAppSize() {
            assertEquals(1, EducationAppTest.this.edAppBelowBoundary.getAppSize());
            assertEquals(1000, EducationAppTest.this.edAppOnBoundary.getAppSize());
            assertEquals(0, EducationAppTest.this.edAppAboveBoundary.getAppSize());
            assertEquals(0, EducationAppTest.this.edAppInvalidData.getAppSize());
        }

        @Test
        void getAppVersion() {
            assertEquals(1.0, EducationAppTest.this.edAppBelowBoundary.getAppVersion());
            assertEquals(2.0, EducationAppTest.this.edAppOnBoundary.getAppVersion());
            assertEquals(3.5, EducationAppTest.this.edAppAboveBoundary.getAppVersion());
            assertEquals(1.0, EducationAppTest.this.edAppInvalidData.getAppVersion());
        }

        @Test
        void getAppCost() {
            assertEquals(0, EducationAppTest.this.edAppBelowBoundary.getAppCost());
            assertEquals(1.99, EducationAppTest.this.edAppOnBoundary.getAppCost());
            assertEquals(2.99, EducationAppTest.this.edAppAboveBoundary.getAppCost());
            assertEquals(0, EducationAppTest.this.edAppInvalidData.getAppCost());
        }

        @Test
        void getLevel() {
            assertEquals(1, EducationAppTest.this.edAppBelowBoundary.getLevel());
            assertEquals(10, EducationAppTest.this.edAppOnBoundary.getLevel());
            assertEquals(0, EducationAppTest.this.edAppAboveBoundary.getLevel());
            assertEquals(0, EducationAppTest.this.edAppInvalidData.getLevel());
        }

    }

    @Nested
    class Setters {

        @Test
        void setDeveloper() {
            //no validation in models
            assertEquals(EducationAppTest.this.developerLego, EducationAppTest.this.edAppBelowBoundary.getDeveloper());
            EducationAppTest.this.edAppBelowBoundary.setDeveloper(EducationAppTest.this.developerSphero);
            assertEquals(EducationAppTest.this.developerSphero, EducationAppTest.this.edAppBelowBoundary.getDeveloper());
        }

        @Test
        void setAppName() {
            //no validation in models
            assertEquals("WeDo", EducationAppTest.this.edAppBelowBoundary.getAppName());
            EducationAppTest.this.edAppBelowBoundary.setAppName("Mindstorms");
            assertEquals("Mindstorms", EducationAppTest.this.edAppBelowBoundary.getAppName());
        }

        @Test
        void setAppSize() {
            //Validation: appSize(1-1000)
            assertEquals(1, EducationAppTest.this.edAppBelowBoundary.getAppSize());

            EducationAppTest.this.edAppBelowBoundary.setAppSize(1000);
            assertEquals(1000, EducationAppTest.this.edAppBelowBoundary.getAppSize()); //update

            EducationAppTest.this.edAppBelowBoundary.setAppSize(1001);
            assertEquals(1000, EducationAppTest.this.edAppBelowBoundary.getAppSize()); //no update

            EducationAppTest.this.edAppBelowBoundary.setAppSize(2);
            assertEquals(2, EducationAppTest.this.edAppBelowBoundary.getAppSize()); //update

            EducationAppTest.this.edAppBelowBoundary.setAppSize(0);
            assertEquals(2, EducationAppTest.this.edAppBelowBoundary.getAppSize()); //no update
        }

        @Test
        void setAppVersion() {
            //Validation: appVersion(>=1.0)
            assertEquals(1.0, EducationAppTest.this.edAppBelowBoundary.getAppVersion());

            EducationAppTest.this.edAppBelowBoundary.setAppVersion(2.0);
            assertEquals(2.0, EducationAppTest.this.edAppBelowBoundary.getAppVersion()); //update

            EducationAppTest.this.edAppBelowBoundary.setAppVersion(0.0);
            assertEquals(2.0, EducationAppTest.this.edAppBelowBoundary.getAppVersion()); //no update

            EducationAppTest.this.edAppBelowBoundary.setAppVersion(1.0);
            assertEquals(1.0, EducationAppTest.this.edAppBelowBoundary.getAppVersion()); //update
        }

        @Test
        void setAppCost() {
            //Validation: appCost(>=0)
            assertEquals(0.0, EducationAppTest.this.edAppBelowBoundary.getAppCost());

            EducationAppTest.this.edAppBelowBoundary.setAppCost(1.0);
            assertEquals(1.0, EducationAppTest.this.edAppBelowBoundary.getAppCost()); //update

            EducationAppTest.this.edAppBelowBoundary.setAppCost(-1);
            assertEquals(1.0, EducationAppTest.this.edAppBelowBoundary.getAppCost()); //no update

            EducationAppTest.this.edAppBelowBoundary.setAppCost(0.0);
            assertEquals(0.0, EducationAppTest.this.edAppBelowBoundary.getAppCost()); //update
        }

        @Test
        void setLevel() {
            //Validation: level(1-10)
            assertEquals(1, EducationAppTest.this.edAppBelowBoundary.getLevel());

            EducationAppTest.this.edAppBelowBoundary.setLevel(10);
            assertEquals(10, EducationAppTest.this.edAppBelowBoundary.getLevel()); //update

            EducationAppTest.this.edAppBelowBoundary.setLevel(11);
            assertEquals(10, EducationAppTest.this.edAppBelowBoundary.getLevel()); //no update

            EducationAppTest.this.edAppBelowBoundary.setLevel(1);
            assertEquals(1, EducationAppTest.this.edAppBelowBoundary.getLevel()); //update
        }

    }

    @Nested
    class ObjectStateMethods {

        @Test
        void appSummaryReturnsCorrectString() {
            EducationApp edApp = EducationAppTest.this.setupEducationAppWithRating(3, 4);
            String stringContents = edApp.appSummary();

            assertTrue(stringContents.contains("level " + edApp.getLevel()));
            assertTrue(stringContents.contains(edApp.getAppName() + "(V" + edApp.getAppVersion()));
            assertTrue(stringContents.contains(edApp.getDeveloper().toString()));
            assertTrue(stringContents.contains("€" + edApp.getAppCost()));
            assertTrue(stringContents.contains("Rating: " + edApp.calculateRating()));
        }

        @Test
        void toStringReturnsCorrectString() {
            EducationApp edApp = EducationAppTest.this.setupEducationAppWithRating(3, 4);
            String stringContents = edApp.toString();

            assertTrue(stringContents.contains(edApp.getAppName()));
            assertTrue(stringContents.contains("(Version " + edApp.getAppVersion()));
            assertTrue(stringContents.contains(edApp.getDeveloper().toString()));
            assertTrue(stringContents.contains(edApp.getAppSize() + "MB"));
            assertTrue(stringContents.contains("Cost: " + edApp.getAppCost()));
            assertTrue(stringContents.contains("Level: " + edApp.getLevel()));
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
        void appIsNotRecommendedWhenInAppCostIs99c() {
            //setting all conditions to true with ratings of 3 and 4 (i.e. 3.5)
            EducationApp edApp = EducationAppTest.this.setupEducationAppWithRating(3, 4);

            //now setting appCost to 0.99 so app should not be recommended now
            edApp.setAppCost(0.99);
            assertFalse(edApp.isRecommendedApp());
        }

        @Test
        void appIsNotRecommendedWhenRatingIsLessThan3AndAHalf() {
            //setting all conditions to true with ratings of 3 and 3 (i.e. 3.0)
            EducationApp edApp = EducationAppTest.this.setupEducationAppWithRating(3, 3);
            //verifying recommended app returns false (rating not high enough
            assertFalse(edApp.isRecommendedApp());
        }

        @Test
        void appIsNotRecommendedWhenNoRatingsExist() {
            //setting all conditions to true with no ratings
            EducationApp edApp = new EducationApp(EducationAppTest.this.developerLego, "WeDo", 1,
                    1.0, 1.00,  3);
            //verifying recommended app returns true
            assertFalse(edApp.isRecommendedApp());
        }

        @Test
        void appIsRecommendedWhenAllOfTheConditionsAreTrue() {
            //setting all conditions to true with ratings of 3 and 4 (i.e. 3.5)
            EducationApp edApp = EducationAppTest.this.setupEducationAppWithRating(3, 4);

            //verifying recommended app returns true
            assertTrue(edApp.isRecommendedApp());
        }

    }
}
