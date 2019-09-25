package adv.threadtest.dto;

import java.sql.Timestamp;

public class EventDTO {

    public EventDTO(Long eventId, Timestamp timestamp, String threadName) {
        this.eventId = eventId;
        this.timestamp = timestamp;
        this.threadName = threadName;
    }

    public Long eventId;
    public Timestamp timestamp;
    public String threadName;
}
