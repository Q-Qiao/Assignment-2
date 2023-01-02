import app.ProductivityApp;
import models.Developer;
import models.Rating;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductivityAppTest {

    private ProductivityApp edAppBelowBoundary, edAppOnBoundary, edAppAboveBoundary, edAppInvalidData;
    private Developer developerLego = new Developer("tom", "www.tom.com");
    private Developer developerSphero = new Developer("jerry", "www.jerry.com");


    
    @BeforeEach
    void setUp() {
        //ProductivityApp has no level
        
        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0), level(1-10).
        this.edAppBelowBoundary = new ProductivityApp(this.developerLego, "WeDo", 1, 1.0, 0);
        this.edAppOnBoundary = new ProductivityApp(this.developerLego, "Spike", 1000, 2.0, 1.99);
        this.edAppAboveBoundary = new ProductivityApp(this.developerLego, "EV3", 1001, 3.5, 2.99);
        this.edAppInvalidData = new ProductivityApp(this.developerLego, "", -1, 0, -1.00);
    }

    @AfterEach
    void tearDown() {
        this.edAppBelowBoundary = this.edAppOnBoundary = this.edAppAboveBoundary = this.edAppInvalidData = null;
        this.developerLego = this.developerSphero = null;
    }

    ProductivityApp setupProductivityAppWithRating(int rating1, int rating2) {
        //setting all conditions to true
        ProductivityApp edApp = new ProductivityApp(this.developerLego, "WeDo", 1,
                1.0, 1.00 );
        edApp.addRating(new Rating(rating1, "John Doe", "Very Good"));
        edApp.addRating(new Rating(rating2, "Jane Doe", "Excellent"));

        //verifying all conditions are true for a recommended educational app]
        assertEquals(2, edApp.getRatings().size());  //two ratings are added
        assertEquals(1.0, edApp.getAppCost(), 0.01);
        assertEquals(((rating1 + rating2) / 2.0), edApp.calculateRating(), 0.01);


        return edApp;
    }

    @Nested
    class Getters {

        @Test
        void getDeveloper() {
            assertEquals(ProductivityAppTest.this.developerLego, ProductivityAppTest.this.edAppBelowBoundary.getDeveloper());
            assertEquals(ProductivityAppTest.this.developerLego, ProductivityAppTest.this.edAppOnBoundary.getDeveloper());
            assertEquals(ProductivityAppTest.this.developerLego, ProductivityAppTest.this.edAppAboveBoundary.getDeveloper());
            assertEquals(ProductivityAppTest.this.developerLego, ProductivityAppTest.this.edAppInvalidData.getDeveloper());
        }

        @Test
        void getAppName() {
            assertEquals("WeDo", ProductivityAppTest.this.edAppBelowBoundary.getAppName());
            assertEquals("Spike", ProductivityAppTest.this.edAppOnBoundary.getAppName());
            assertEquals("EV3", ProductivityAppTest.this.edAppAboveBoundary.getAppName());
            assertEquals("", ProductivityAppTest.this.edAppInvalidData.getAppName());
        }

        @Test
        void getAppSize() {
            assertEquals(1, ProductivityAppTest.this.edAppBelowBoundary.getAppSize());
            assertEquals(1000, ProductivityAppTest.this.edAppOnBoundary.getAppSize());
            assertEquals(0, ProductivityAppTest.this.edAppAboveBoundary.getAppSize());
            assertEquals(0, ProductivityAppTest.this.edAppInvalidData.getAppSize());
        }

        @Test
        void getAppVersion() {
            assertEquals(1.0, ProductivityAppTest.this.edAppBelowBoundary.getAppVersion());
            assertEquals(2.0, ProductivityAppTest.this.edAppOnBoundary.getAppVersion());
            assertEquals(3.5, ProductivityAppTest.this.edAppAboveBoundary.getAppVersion());
            assertEquals(1.0, ProductivityAppTest.this.edAppInvalidData.getAppVersion());
        }

        @Test
        void getAppCost() {
            assertEquals(0, ProductivityAppTest.this.edAppBelowBoundary.getAppCost());
            assertEquals(1.99, ProductivityAppTest.this.edAppOnBoundary.getAppCost());
            assertEquals(2.99, ProductivityAppTest.this.edAppAboveBoundary.getAppCost());
            assertEquals(0, ProductivityAppTest.this.edAppInvalidData.getAppCost());
        }



    }

    @Nested
    class Setters {

        @Test
        void setDeveloper() {
            //no validation in models
            assertEquals(ProductivityAppTest.this.developerLego, ProductivityAppTest.this.edAppBelowBoundary.getDeveloper());
            ProductivityAppTest.this.edAppBelowBoundary.setDeveloper(ProductivityAppTest.this.developerSphero);
            assertEquals(ProductivityAppTest.this.developerSphero, ProductivityAppTest.this.edAppBelowBoundary.getDeveloper());
        }

        @Test
        void setAppName() {
            //no validation in models
            assertEquals("WeDo", ProductivityAppTest.this.edAppBelowBoundary.getAppName());
            ProductivityAppTest.this.edAppBelowBoundary.setAppName("Mindstorms");
            assertEquals("Mindstorms", ProductivityAppTest.this.edAppBelowBoundary.getAppName());
        }

        @Test
        void setAppSize() {
            //Validation: appSize(1-1000)
            assertEquals(1, ProductivityAppTest.this.edAppBelowBoundary.getAppSize());

            ProductivityAppTest.this.edAppBelowBoundary.setAppSize(1000);
            assertEquals(1000, ProductivityAppTest.this.edAppBelowBoundary.getAppSize()); //update

            ProductivityAppTest.this.edAppBelowBoundary.setAppSize(1001);
            assertEquals(1000, ProductivityAppTest.this.edAppBelowBoundary.getAppSize()); //no update

            ProductivityAppTest.this.edAppBelowBoundary.setAppSize(2);
            assertEquals(2, ProductivityAppTest.this.edAppBelowBoundary.getAppSize()); //update

            ProductivityAppTest.this.edAppBelowBoundary.setAppSize(0);
            assertEquals(2, ProductivityAppTest.this.edAppBelowBoundary.getAppSize()); //no update
        }

        @Test
        void setAppVersion() {
            //Validation: appVersion(>=1.0)
            assertEquals(1.0, ProductivityAppTest.this.edAppBelowBoundary.getAppVersion());

            ProductivityAppTest.this.edAppBelowBoundary.setAppVersion(2.0);
            assertEquals(2.0, ProductivityAppTest.this.edAppBelowBoundary.getAppVersion()); //update

            ProductivityAppTest.this.edAppBelowBoundary.setAppVersion(0.0);
            assertEquals(2.0, ProductivityAppTest.this.edAppBelowBoundary.getAppVersion()); //no update

            ProductivityAppTest.this.edAppBelowBoundary.setAppVersion(1.0);
            assertEquals(1.0, ProductivityAppTest.this.edAppBelowBoundary.getAppVersion()); //update
        }

        @Test
        void setAppCost() {
            //Validation: appCost(>=0)
            assertEquals(0.0, ProductivityAppTest.this.edAppBelowBoundary.getAppCost());

            ProductivityAppTest.this.edAppBelowBoundary.setAppCost(1.0);
            assertEquals(1.0, ProductivityAppTest.this.edAppBelowBoundary.getAppCost()); //update

            ProductivityAppTest.this.edAppBelowBoundary.setAppCost(-1);
            assertEquals(1.0, ProductivityAppTest.this.edAppBelowBoundary.getAppCost()); //no update

            ProductivityAppTest.this.edAppBelowBoundary.setAppCost(0.0);
            assertEquals(0.0, ProductivityAppTest.this.edAppBelowBoundary.getAppCost()); //update
        }



    }

    @Nested
    class ObjectStateMethods {

        @Test
        void appSummaryReturnsCorrectString() {
            ProductivityApp edApp = ProductivityAppTest.this.setupProductivityAppWithRating(3, 4);
            String stringContents = edApp.appSummary();

            assertTrue(stringContents.contains(edApp.getAppName() + "(V" + edApp.getAppVersion()));
            assertTrue(stringContents.contains(edApp.getDeveloper().toString()));
            assertTrue(stringContents.contains("€" + edApp.getAppCost()));
            assertTrue(stringContents.contains("Rating: " + edApp.calculateRating()));
        }

        @Test
        void toStringReturnsCorrectString() {
            ProductivityApp edApp = ProductivityAppTest.this.setupProductivityAppWithRating(3, 4);
            String stringContents = edApp.toString();

            assertTrue(stringContents.contains(edApp.getAppName()));
            assertTrue(stringContents.contains("(Version " + edApp.getAppVersion()));
            assertTrue(stringContents.contains(edApp.getDeveloper().toString()));
            assertTrue(stringContents.contains(edApp.getAppSize() + "MB"));
            assertTrue(stringContents.contains("Cost: " + edApp.getAppCost()));
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

        /**
         * 不推荐应用:当评分小于等于3
         */
        @Test
        void appIsNotRecommendedWhenCalcRatingIsLessThanOrEquals3() {

            //appCost>=1.99 and calcRating>3.0

            ProductivityApp edApp = ProductivityAppTest.this.setupProductivityAppWithRating(3, 3);

            edApp.setAppCost(4);
            assertFalse(edApp.isRecommendedApp());
        }

        /**
         * 不推荐:应用成本等于1.99
         */
        @Test
        void appIsNotRecommendedWhenAppCostIsLessThanOrEquals199() {

            ProductivityApp edApp = ProductivityAppTest.this.setupProductivityAppWithRating(4, 5);


            edApp.setAppCost(0.11);
            assertFalse(edApp.isRecommendedApp());
        }

        /**
         * 不推荐:应用无评分
         */
        @Test
        void appIsNotRecommendedWhenNoRatingsExist() {
            //setting all conditions to true with no ratings
            ProductivityApp edApp = new ProductivityApp(ProductivityAppTest.this.developerLego, "WeDo", 1,
                    1.0, 1.00 );
            //verifying recommended app returns true
            assertFalse(edApp.isRecommendedApp());
        }

        @Test
        void appIsRecommendedWhenAllOfTheConditionsAreTrue() {

            ProductivityApp edApp = ProductivityAppTest.this.setupProductivityAppWithRating(3, 4);

            edApp.setAppCost(4);
            assertTrue(edApp.isRecommendedApp());
        }

    }
}
