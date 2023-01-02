package utils;

import models.Rating;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.random;

public class RatingUtility {

    //TODO Nothing!  This utility is completed!

        private static List<String> listOfAuthors = new ArrayList<>(){{
            this.add("JohnD");
            this.add("Adam101");
            this.add("Eve101");
            this.add("Cary1987");
            this.add("RickyW");
            this.add("MarkD");
            this.add("Scotty");
            this.add("Mary3");
            this.add("Flynn121");
        }};

        private static List<String> listOfComments = new ArrayList<>(){{
            this.add("Loved the UX");
            this.add("Great App");
            this.add("Poor App");
            this.add("Couldn't stop using app");
            this.add("Used once, never again");
            this.add("Too expensive");
            this.add("Too slow");
            this.add("Really intuitive");
            this.add("Not for me");
        }};

        public static Rating generateRandomRating() {
            return new Rating( (int) (random() * (5)),
                    listOfAuthors.get ((int) (random() * (listOfAuthors.size()-1))),
                    listOfComments.get ((int) (random() * (listOfComments.size()-1)))
                 );
        }
}
