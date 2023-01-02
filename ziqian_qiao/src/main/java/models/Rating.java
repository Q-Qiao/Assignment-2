package models;

import utils.Utilities;

public class Rating {

    private int numberOfStars = 0;
    private String raterName = "<rater name>";
    private String ratingComment = "<no comment>";

    // TODO Nothing!  This class is complete
    
    public Rating(int numberOfStars, String raterName, String ratingComment) {
        this.setNumberOfStars(numberOfStars);
        this.setRaterName(raterName);
        this.setRatingComment(ratingComment);
    }

    public int getNumberOfStars() {
        return this.numberOfStars;
    }

    public void setNumberOfStars(int numberOfStars) {
        if (Utilities.validRange(numberOfStars, 1, 5)) {
            this.numberOfStars = numberOfStars;
        }
    }

    public String getRaterName() {
        return this.raterName;
    }

    public void setRaterName(String raterName) {
        if (!raterName.equals("")) {
            this.raterName = raterName;
        }
    }

    public String getRatingComment() {
        return this.ratingComment;
    }

    public void setRatingComment(String ratingComment) {
        if (!ratingComment.equals("")) {
            this.ratingComment = ratingComment;
        }
    }

    @Override
    public String toString() {
        String ratingHeader = this.numberOfStars + " stars (by " + this.raterName + ").";
        if (this.ratingComment.isEmpty()) {
            return ratingHeader;
        } else {
            return ratingHeader + "\"" + this.ratingComment + '\"';
        }
    }

}
