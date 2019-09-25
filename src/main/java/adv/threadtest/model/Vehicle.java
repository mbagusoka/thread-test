package adv.threadtest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "vehicle")
public class Vehicle {

    public Vehicle() {}

    public Vehicle(String vehicleId, String state) {
        this.vehicleId = vehicleId;
        this.state = state;
        creationTime = new Timestamp(System.currentTimeMillis());
    }

    @Id
    @Column(name = "vehicle_id")
    private String vehicleId;

    @Column(name = "state")
    private String state;

    @Column(name = "creation_time")
    private Timestamp creationTime;

    @Column(name = "updated_time")
    private Timestamp updatedTime;

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }
}
