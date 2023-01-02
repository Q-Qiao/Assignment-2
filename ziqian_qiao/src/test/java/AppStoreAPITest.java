import app.App;
import app.EducationApp;
import app.GameApp;
import app.ProductivityApp;
import controllers.AppStoreAPI;
import models.Developer;
import models.Rating;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppStoreAPITest {

    private EducationApp edAppBelowBoundary, edAppOnBoundary, edAppAboveBoundary, edAppInvalidData;

    private ProductivityApp prodAppBelowBoundary, prodAppOnBoundary, prodAppAboveBoundary, prodAppInvalidData;

    private GameApp gameAppBelowBoundary, gameAppOnBoundary, gameAppAboveBoundary, gameAppInvalidData;

    private Developer developerLego = new Developer("Lego", "www.lego.com");

    private Developer developerSphero = new Developer("Sphero", "www.sphero.com");

    private Developer developerEAGames = new Developer("EA Games", "www.eagames.com");

    private Developer developerKoolGames = new Developer("Kool Games", "www.koolgames.com");

    private Developer developerApple = new Developer("Apple", "www.apple.com");

    private Developer developerMicrosoft = new Developer("Microsoft", "www.microsoft.com");

    private AppStoreAPI appStore = new AppStoreAPI();

    private AppStoreAPI emptyAppStore = new AppStoreAPI();

    @BeforeEach
    void setUp() {

        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0), level(1-10).
        this.edAppBelowBoundary = new EducationApp(this.developerLego, "WeDo", 1, 1.0, 0, 1);

        this.edAppOnBoundary = new EducationApp(this.developerLego, "Spike", 1000, 2.0,
                1.99, 10);

        this.edAppAboveBoundary = new EducationApp(this.developerLego, "EV3", 1001, 3.5, 2.99, 11);

        this.edAppInvalidData = new EducationApp(this.developerLego, "", -1, 0, -1.00, 0);


        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0),
        this.prodAppBelowBoundary = new ProductivityApp(this.developerApple, "NoteKeeper", 1, 1.0, 0.0);

        this.prodAppOnBoundary = new ProductivityApp(this.developerMicrosoft, "Outlook", 1000, 2.0, 1.99);

        this.prodAppAboveBoundary = new ProductivityApp(this.developerApple, "Pages", 1001, 3.5, 2.99);

        this.prodAppInvalidData = new ProductivityApp(this.developerMicrosoft, "", -1, 0, -1.00);


        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0),
        this.gameAppBelowBoundary = new GameApp(this.developerEAGames, "Tetris", 1, 1.0, 0.0, false);

        this.gameAppOnBoundary = new GameApp(this.developerKoolGames, "CookOff", 1000, 2.0, 1.99, true);

        this.gameAppAboveBoundary = new GameApp(this.developerEAGames, "Empires", 1001, 3.5, 2.99, false);

        this.gameAppInvalidData = new GameApp(this.developerKoolGames, "", -1, 0, -1.00, true);

        //not included - edAppOnBoundary, edAppInvalidData, prodAppBelowBoundary, gameAppBelowBoundary, gameAppInvalidData.
        this.appStore.addApp(this.edAppBelowBoundary);
        this.appStore.addApp(this.prodAppOnBoundary);
        this.appStore.addApp(this.gameAppAboveBoundary);
        this.appStore.addApp(this.prodAppBelowBoundary);
        this.appStore.addApp(this.edAppAboveBoundary);
        this.appStore.addApp(this.prodAppInvalidData);
        this.appStore.addApp(this.gameAppOnBoundary);
    }

    @AfterEach
    void tearDown() {
        this.edAppBelowBoundary = this.edAppOnBoundary = this.edAppAboveBoundary = this.edAppInvalidData = null;
        this.gameAppBelowBoundary = this.gameAppOnBoundary = this.gameAppAboveBoundary = this.gameAppInvalidData = null;
        this.prodAppBelowBoundary = this.prodAppOnBoundary = this.prodAppAboveBoundary = this.prodAppInvalidData = null;
        this.developerApple = this.developerEAGames = this.developerKoolGames = this.developerLego = this.developerMicrosoft = null;
        this.appStore = this.emptyAppStore = null;
    }

    //--------------------------------------------
    // Helper Methods
    //--------------------------------------------
    EducationApp setupEducationAppWithRating(int rating1, int rating2) {
        //setting all conditions to true
        EducationApp edApp = new EducationApp(this.developerLego, "WeDo", 1,
                1.0, 1.00, 3);
        edApp.addRating(new Rating(rating1, "John Doe", "Very Good"));
        edApp.addRating(new Rating(rating2, "Jane Doe", "Excellent"));

        return edApp;
    }

    GameApp setupGameAppWithRating(int rating1, int rating2) {
        GameApp gameApp = new GameApp(this.developerEAGames, "MazeRunner", 1,
                1.0, 1.00, true);
        gameApp.addRating(new Rating(rating1, "John Soap", "Exciting Game"));
        gameApp.addRating(new Rating(rating2, "Jane Soap", "Nice Game"));
        return gameApp;
    }

    ProductivityApp setupProductivityAppWithRating(int rating1, int rating2) {
        ProductivityApp productivityApp = new ProductivityApp(this.developerApple, "Evernote", 1,
                1.0, 1.99);

        productivityApp.addRating(new Rating(rating1, "John101", "So easy to add a note"));
        productivityApp.addRating(new Rating(rating2, "Jane202", "So useful"));
        return productivityApp;
    }

    @Nested
    class GettersAndSetters {

        @Test
        void returnNullWhenGetAppByIndexOutOfIndex() {
            assertEquals(null, AppStoreAPITest.this.appStore.getAppByIndex(999));
        }

        @Test
        void testGetAppByName() {
            assertEquals(AppStoreAPITest.this.prodAppOnBoundary, AppStoreAPITest.this.appStore.getAppByName("Outlook"));
            assertEquals(null, AppStoreAPITest.this.appStore.getAppByName("notExistApp"));
        }

    }

    @Nested
    class CRUDMethods {
        @Test
        void returnNullWhenDeleteAppByIndexAppOutOfIndex() {
            assertEquals(null, AppStoreAPITest.this.appStore.deleteAppByIndex(999));
        }


        @Test
        void TestDeleteByIndex() {
            int numberOfApps = AppStoreAPITest.this.appStore.numberOfApps();
            assertEquals(AppStoreAPITest.this.prodAppOnBoundary, AppStoreAPITest.this.appStore.deleteAppByIndex(1));
            assertEquals(numberOfApps - 1, AppStoreAPITest.this.appStore.numberOfApps());
        }

    }

    @Nested
    class ListingMethods {

        @Test
        void listSummaryOfAllApps() {
            String emptyListSummaryOfAllApps = AppStoreAPITest.this.emptyAppStore.listSummaryOfAllApps();
            assertTrue(emptyListSummaryOfAllApps
                    .toLowerCase()
                    .contains("no apps"));


            String summaryOfAllApps = AppStoreAPITest.this.appStore.listSummaryOfAllApps();
            //checks for objects in the string
            assertTrue(summaryOfAllApps.contains("WeDo"));
            assertTrue(summaryOfAllApps.contains("Outlook"));
            assertTrue(summaryOfAllApps.contains("Empires"));
            assertTrue(summaryOfAllApps.contains("NoteKeeper"));
            assertTrue(summaryOfAllApps.contains("EV3"));
            assertTrue(summaryOfAllApps.contains("CookOff"));
        }


        @Test
        void listAllGameApps() {
            String emptyApps = AppStoreAPITest.this.emptyAppStore.listAllGameApps();
            assertTrue(emptyApps
                    .contains("No Game apps"));


            String gameApps = AppStoreAPITest.this.appStore.listAllGameApps();
            System.out.println(gameApps);
            //checks for objects in the string
            assertTrue(gameApps.contains("CookOff"));
            assertTrue(gameApps.contains("Empires"));
        }

        @Test
        void listAllEducationApps() {
            String emptyApps = AppStoreAPITest.this.emptyAppStore.listAllEducationApps();
            assertTrue(emptyApps
                    .contains("No Education apps"));


            String educationApps = AppStoreAPITest.this.appStore.listAllEducationApps();
            System.out.println(educationApps);
            //checks for objects in the string
            assertTrue(educationApps.contains("WeDo"));
            assertTrue(educationApps.contains("EV3"));
        }

        @Test
        void listAllProductivityApps() {
            String emptyApps = AppStoreAPITest.this.emptyAppStore.listAllProductivityApps();
            assertTrue(emptyApps
                    .contains("No Productivity apps"));


            String productivityApps = AppStoreAPITest.this.appStore.listAllProductivityApps();
            System.out.println(productivityApps);
            //checks for objects in the string
            assertTrue(productivityApps.contains("NoteKeeper"));
            assertTrue(productivityApps.contains("Outlook"));
        }


        @Test
        void listAllAppsByName() {
            String notExistName = "dfdfdfdvd";
            String existName = "Outlook";
            String emptyApps = AppStoreAPITest.this.appStore.listAllAppsByName(notExistName);
            assertTrue(emptyApps
                    .contains("No apps for name"));


            String exist = AppStoreAPITest.this.appStore.listAllAppsByName(existName);
            System.out.println(exist);
            //checks for objects in the string
            assertTrue(exist.contains("Outlook"));
        }

        @Test
        void listAllAppsAboveOrEqualAGivenStarRating() {

            String emptyApps = AppStoreAPITest.this.emptyAppStore.listAllAppsAboveOrEqualAGivenStarRating(4);
            assertTrue(emptyApps
                    .contains("No apps have a rating of"));

            AppStoreAPITest.this.appStore.addApp(AppStoreAPITest.this.setupGameAppWithRating(5, 5));
            String exist = AppStoreAPITest.this.appStore.listAllAppsAboveOrEqualAGivenStarRating(5);

            //checks for objects in the string
            assertTrue(exist.contains("MazeRunner"));
        }

        @Test
        void listAllAppsByChosenDeveloper() {

            String emptyApps = AppStoreAPITest.this.emptyAppStore.listAllAppsByChosenDeveloper(AppStoreAPITest.this.developerLego);
            assertTrue(emptyApps
                    .contains("No apps for developer"));

            String exist = AppStoreAPITest.this.appStore.listAllAppsByChosenDeveloper(AppStoreAPITest.this.developerLego);
            System.out.println(exist);

            //checks for objects in the string
            assertTrue(exist.contains("EV3"));
            assertTrue(exist.contains("WeDo"));
        }

        @Test
        void numberOfAppsByChosenDeveloper() {

            int number = AppStoreAPITest.this.emptyAppStore.numberOfAppsByChosenDeveloper(AppStoreAPITest.this.developerLego);
            assertEquals(0,number);

            int number2 = AppStoreAPITest.this.appStore.numberOfAppsByChosenDeveloper(AppStoreAPITest.this.developerLego);
            assertEquals(2,number2);

        }
        @Test
        void randomApp() {

            App app = AppStoreAPITest.this.emptyAppStore.randomApp();
            assertEquals(null,app);

            App randomApp = AppStoreAPITest.this.appStore.randomApp();
            assertNotEquals(null,randomApp);

        }

        @Test
        void simulateRatings() {
            AppStoreAPITest.this.appStore.simulateRatings();
        }

        @Test
        void listAllAppsReturnsNoAppssStoredWhenArrayListIsEmpty() {
            assertEquals(0, AppStoreAPITest.this.emptyAppStore.numberOfApps());
            assertTrue(AppStoreAPITest.this.emptyAppStore.listAllApps()
                                                         .toLowerCase()
                                                         .contains("no apps"));
        }


        @Test
        void listAllAppsReturnsAppsStoredWhenArrayListHasAppsStored() {
            assertEquals(7, AppStoreAPITest.this.appStore.numberOfApps());
            String apps = AppStoreAPITest.this.appStore.listAllApps();
            //checks for objects in the string
            assertTrue(apps.contains("WeDo"));
            assertTrue(apps.contains("Outlook"));
            assertTrue(apps.contains("Empires"));
            assertTrue(apps.contains("NoteKeeper"));
            assertTrue(apps.contains("EV3"));
            assertTrue(apps.contains("CookOff"));
        }

        @Test
        void listRecommendedAppsReturnsNoAppsWhenRecommendedAppsDoNotExist() {
            assertEquals(7, AppStoreAPITest.this.appStore.numberOfApps());

            String apps = AppStoreAPITest.this.appStore.listAllRecommendedApps();
            //checks for the three objects in the string
            assertTrue(apps.contains("No recommended apps"));
        }

        @Test
        void listRecommendedAppsReturnsRecommendedAppsWhenTheyExist() {
            assertEquals(7, AppStoreAPITest.this.appStore.numberOfApps());

            //adding recommended apps to the list
            AppStoreAPITest.this.appStore.addApp(AppStoreAPITest.this.setupGameAppWithRating(5, 4));
            AppStoreAPITest.this.appStore.addApp(AppStoreAPITest.this.setupEducationAppWithRating(3, 4));
            AppStoreAPITest.this.appStore.addApp(AppStoreAPITest.this.setupProductivityAppWithRating(3, 4));
            assertEquals(10, AppStoreAPITest.this.appStore.numberOfApps());

            String apps = AppStoreAPITest.this.appStore.listAllRecommendedApps();
            System.out.println(apps);
            //checks for the three objects in the string
            assertTrue(apps.contains("MazeRunner"));
            assertTrue(apps.contains("Evernote"));
            assertTrue(apps.contains("WeDo"));
        }


    }

    @Nested
    class ReportingMethods {

    }

    @Nested
    class SearchingMethods {

    }

    @Nested
    class SortingMethods {

        @Test
        void sortByNameAscendingReOrdersList() {
            assertEquals(7, AppStoreAPITest.this.appStore.numberOfApps());
            //checks the order of the objects in the list
            assertEquals(AppStoreAPITest.this.edAppBelowBoundary, AppStoreAPITest.this.appStore.getAppByIndex(0));
            assertEquals(AppStoreAPITest.this.prodAppOnBoundary, AppStoreAPITest.this.appStore.getAppByIndex(1));
            assertEquals(AppStoreAPITest.this.gameAppAboveBoundary, AppStoreAPITest.this.appStore.getAppByIndex(2));
            assertEquals(AppStoreAPITest.this.prodAppBelowBoundary, AppStoreAPITest.this.appStore.getAppByIndex(3));
            assertEquals(AppStoreAPITest.this.edAppAboveBoundary, AppStoreAPITest.this.appStore.getAppByIndex(4));
            assertEquals(AppStoreAPITest.this.prodAppInvalidData, AppStoreAPITest.this.appStore.getAppByIndex(5));
            assertEquals(AppStoreAPITest.this.gameAppOnBoundary, AppStoreAPITest.this.appStore.getAppByIndex(6));

            AppStoreAPITest.this.appStore.sortAppsByNameAscending();
            assertEquals(AppStoreAPITest.this.prodAppInvalidData, AppStoreAPITest.this.appStore.getAppByIndex(0));
            assertEquals(AppStoreAPITest.this.gameAppOnBoundary, AppStoreAPITest.this.appStore.getAppByIndex(1));
            assertEquals(AppStoreAPITest.this.edAppAboveBoundary, AppStoreAPITest.this.appStore.getAppByIndex(2));
            assertEquals(AppStoreAPITest.this.gameAppAboveBoundary, AppStoreAPITest.this.appStore.getAppByIndex(3));
            assertEquals(AppStoreAPITest.this.prodAppBelowBoundary, AppStoreAPITest.this.appStore.getAppByIndex(4));
            assertEquals(AppStoreAPITest.this.prodAppOnBoundary, AppStoreAPITest.this.appStore.getAppByIndex(5));
            assertEquals(AppStoreAPITest.this.edAppBelowBoundary, AppStoreAPITest.this.appStore.getAppByIndex(6));
        }

        @Test
        void sortByNameAscendingDoesntCrashWhenListIsEmpty() {
            assertEquals(0, AppStoreAPITest.this.emptyAppStore.numberOfApps());
            AppStoreAPITest.this.emptyAppStore.sortAppsByNameAscending();
        }

    }


}
