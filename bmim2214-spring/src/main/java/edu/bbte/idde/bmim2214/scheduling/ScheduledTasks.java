package edu.bbte.idde.bmim2214.scheduling;

import edu.bbte.idde.bmim2214.dataaccess.repos.CarModelRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class ScheduledTasks {
    private final CarModelRepo carModelRepo;
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    public ScheduledTasks(CarModelRepo carModelRepo) {
        this.carModelRepo = carModelRepo;
    }

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.info("The time is now {}", LocalDateTime.now().format(dateFormat));
    }

    @Scheduled(fixedRate = 5000)
    public void countCarsBefore1950() {
        long count = carModelRepo.countCarsBefore1950();
        log.info("Number of cars before 1950: {}", count);
    }

    @Scheduled(fixedRate = 5000)
    public void countCarsLT5K() {
        long count = carModelRepo.countCarsPriceLT5K();
        log.info("Number of cars which prices are less than 5000: {}", count);
    }
}
