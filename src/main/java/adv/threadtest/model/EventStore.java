package adv.threadtest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "event_store")
public class EventStore {

    public EventStore() {}

    public EventStore(Long eventId, String aggregateId, String state) {
        this.eventId = eventId;
        this.aggregateId = aggregateId;
        this.state = state;
        creationTime = new Timestamp(System.currentTimeMillis());
    }

    @Id
    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "aggregate_id")
    private String aggregateId;

    @Column(name = "state")
    private String state;

    @Column(name = "creation_time")
    private Timestamp creationTime;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public void setAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
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
}
