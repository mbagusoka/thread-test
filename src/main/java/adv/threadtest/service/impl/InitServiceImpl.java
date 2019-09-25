package adv.threadtest.service.impl;

import adv.threadtest.service.DatabaseService;
import adv.threadtest.service.EventProcessor;
import adv.threadtest.service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitServiceImpl implements InitService {

    private DatabaseService databaseService;
    private EventProcessor eventProcessor;

    @Autowired
    public InitServiceImpl(DatabaseService databaseService, EventProcessor eventProcessor) {
        this.databaseService = databaseService;
        this.eventProcessor = eventProcessor;
    }

    @Override
    public void init() {
        databaseService.initData();
        eventProcessor.processEvent();
    }
}
