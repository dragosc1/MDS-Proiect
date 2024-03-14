package headers;

import java.util.ArrayList;

public class Player {
    private String name;
    private Trait trait;
    private ArrayList<Status> statuses;

    public Player(String name, Trait trait, ArrayList<Status> statuses) {
        this.name = name;
        this.trait = trait;
        this.statuses = statuses;
    }

    public String getName() {
        return name;
    }

    public Trait getTrait() {
        return trait;
    }

    public ArrayList<Status> getStatuses() {
        return statuses;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTraits(Trait trait) {
        this.trait = trait;
    }

    public void setStatuses(ArrayList<Status> statuses) {
        this.statuses = statuses;
    }
}

