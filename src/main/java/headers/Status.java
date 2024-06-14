package headers;

public class Status {
    private String name;
    private int pointsInStatus;

    public Status(String name, int pointsInStatus) {
        this.name = name;
        this.pointsInStatus = pointsInStatus;
    }
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
