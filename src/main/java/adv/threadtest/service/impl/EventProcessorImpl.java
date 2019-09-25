package adv.threadtest.service.impl;

import adv.threadtest.core.MultiThreadProcessor;
import adv.threadtest.dto.EventDTO;
import adv.threadtest.model.EventStore;
import adv.threadtest.model.Vehicle;
import adv.threadtest.repository.EventStoreRepository;
import adv.threadtest.repository.VehicleRepository;
import adv.threadtest.service.EventProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("squid:S106")
public class EventProcessorImpl implements EventProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventProcessorImpl.class);

    private EventStoreRepository eventStoreRepository;
    private VehicleRepository vehicleRepository;

    @Autowired
    public EventProcessorImpl(EventStoreRepository eventStoreRepository, VehicleRepository vehicleRepository) {
        this.eventStoreRepository = eventStoreRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void processEvent() {
        List<EventStore> eventStoreList = eventStoreRepository.findAll();
        Map<String, List<EventStore>>  aggregateEventMap = eventStoreList.stream().collect(Collectors.groupingBy(EventStore::getAggregateId));
        List<EventDTO> eventDTOList = doMultiThread(aggregateEventMap);
        showEvent(eventDTOList);
        showVehicle();
    }

    private void showVehicle() {
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        for (Vehicle vehicle : vehicleList) {
            System.out.println(vehicle.getVehicleId() + " " + vehicle.getState() + " " + vehicle.getCreationTime() + " " + vehicle.getUpdatedTime());
        }
    }

    private List<EventDTO> doMultiThread(Map<String, List<EventStore>> aggregateEventMap) {
        List<EventDTO> eventDTOList = new ArrayList<>();
        try {
            execute(eventDTOList, aggregateEventMap);
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            LOGGER.error("Multi Thread Exception: ", e);
        }
        return eventDTOList;
    }

    private void execute(List<EventDTO> eventDTOList, Map<String, List<EventStore>> aggregateEventMap) throws ExecutionException, InterruptedException {
        List<Future<List<EventDTO>>> futureList = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(aggregateEventMap.size());
        for (Map.Entry<String, List<EventStore>> entry : aggregateEventMap.entrySet()) {
            MultiThreadProcessor multiThreadProcessor = new MultiThreadProcessor(vehicleRepository, entry.getValue());
            Future<List<EventDTO>> future = executorService.submit(multiThreadProcessor);
            futureList.add(future);
        }
        for (Future<List<EventDTO>> future : futureList) {
            eventDTOList.addAll(future.get());
        }
        executorService.shutdown();
    }

    private void showEvent(List<EventDTO> eventDTOList) {
        for (EventDTO eventDTO : eventDTOList) {
            System.out.println(eventDTO.eventId + " " + eventDTO.threadName + " " + eventDTO.timestamp);
        }
    }
}
