package headers;

import java.io.Serializable;

// Class representing a status for the player
public class Status implements Serializable {
    private String name;
    private int pointsInStatus;

    public Status(String name, int pointsInStatus) {
        this.name = name;
        this.pointsInStatus = pointsInStatus;
    }

    // Getters and setters
    public String getName() {
        return this.name;
    }
    public int getPoints() {
        return pointsInStatus;
    }
    public void addPoint() { ++pointsInStatus;}

    public void setPointsInStatus(int pointsInStatus) {
        this.pointsInStatus = pointsInStatus;
    }
}
