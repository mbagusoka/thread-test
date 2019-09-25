package adv.threadtest.core;

import adv.threadtest.dto.EventDTO;
import adv.threadtest.model.EventStore;
import adv.threadtest.model.Vehicle;
import adv.threadtest.repository.VehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class MultiThreadProcessor implements Callable<List<EventDTO>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiThreadProcessor.class);

    private VehicleRepository vehicleRepository;
    private List<EventStore> eventStoreList;

    public MultiThreadProcessor(VehicleRepository vehicleRepository,
                                List<EventStore> eventStoreList) {
        this.vehicleRepository = vehicleRepository;
        this.eventStoreList = eventStoreList;
    }

    @Override
    public List<EventDTO> call() {
        List<EventDTO> eventDTOList = new ArrayList<>();
        try {
            for (EventStore eventStore : eventStoreList) {
                Vehicle vehicle = vehicleRepository.getOne(eventStore.getAggregateId());
                vehicle.setState(eventStore.getState());
                vehicle.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
                vehicleRepository.save(vehicle);
                eventDTOList.add(new EventDTO(eventStore.getEventId(), new Timestamp(System.currentTimeMillis()), Thread.currentThread().getName()));
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            LOGGER.error("Callable Exception: ", e);
        }
        return eventDTOList;
    }
}
