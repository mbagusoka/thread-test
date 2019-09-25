package adv.threadtest.service.impl;

import adv.threadtest.model.EventStore;
import adv.threadtest.model.Vehicle;
import adv.threadtest.model.VehicleState;
import adv.threadtest.repository.EventStoreRepository;
import adv.threadtest.repository.VehicleRepository;
import adv.threadtest.service.DatabaseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseServiceImpl implements DatabaseService {

    private static final String VEHICLE_A = "A";
    private static final String VEHICLE_B = "B";
    private static final String VEHICLE_C = "C";

    private EventStoreRepository eventStoreRepository;
    private VehicleRepository vehicleRepository;

    public DatabaseServiceImpl(EventStoreRepository eventStoreRepository, VehicleRepository vehicleRepository) {
        this.eventStoreRepository = eventStoreRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void initData() {
        initEventStoreData();
        initVehicleData();
    }

    private void initVehicleData() {
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(new Vehicle(VEHICLE_A, VehicleState.STOP.desc));
        vehicleList.add(new Vehicle(VEHICLE_B, VehicleState.STOP.desc));
        vehicleList.add(new Vehicle(VEHICLE_C, VehicleState.STOP.desc));
        vehicleRepository.saveAll(vehicleList);
    }

    private void initEventStoreData() {
        List<EventStore> eventStoreList = new ArrayList<>();
        eventStoreList.add(new EventStore(1L, VEHICLE_A, VehicleState.MOVE.desc));
        eventStoreList.add(new EventStore(2L, VEHICLE_A, VehicleState.STOP.desc));
        eventStoreList.add(new EventStore(3L, VEHICLE_B, VehicleState.MOVE.desc));
        eventStoreList.add(new EventStore(4L, VEHICLE_B, VehicleState.STOP.desc));
        eventStoreList.add(new EventStore(5L, VEHICLE_C, VehicleState.MOVE.desc));
        eventStoreList.add(new EventStore(6L, VEHICLE_C, VehicleState.STOP.desc));
        eventStoreRepository.saveAll(eventStoreList);
    }
}
