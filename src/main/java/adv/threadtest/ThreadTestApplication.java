package adv.threadtest;

import adv.threadtest.service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThreadTestApplication implements CommandLineRunner {

    private InitService initService;

    @Autowired
    public ThreadTestApplication(InitService initService) {
        this.initService = initService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ThreadTestApplication.class, args);
    }

    @Override
    public void run(String... args) {
        initService.init();
    }
}
