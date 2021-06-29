package br.com.poaapi.busbaseproject.service;

import br.com.poaapi.busbaseproject.integration.BusLineIntegration;
import br.com.poaapi.busbaseproject.models.BusLine;
import br.com.poaapi.busbaseproject.repository.BusLineRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Semaphore;

public class ThreadNotification {

    private void save(BusLineIntegration integration, BusLineRepository repository, BusLine line) throws JsonProcessingException {
        integration.addCoordinatesToLine(new RestTemplate(), line);
        line.setName(line.getName().toUpperCase());
        repository.save(line);
    }

    public void saveThread(BusLineIntegration integration, BusLineRepository repository, Semaphore semaphore, BusLine line){
        new Thread(() -> {
            try {
                save(integration, repository, line);
                Thread.sleep(500);
            } catch (JsonProcessingException | InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            semaphore.release();
        }, "Save" + line.getId()).start();
    }

}
