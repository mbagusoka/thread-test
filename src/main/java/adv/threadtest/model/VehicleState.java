package adv.threadtest.model;

public enum  VehicleState {

    MOVE    ("MOVE"),
    STOP    ("STOP");

    public String desc;

    VehicleState (String desc) {
        this.desc = desc;
    }
}
