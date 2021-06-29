package br.com.poaapi.taxibaseproject.integration;

import br.com.poaapi.taxibaseproject.model.Taxi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

@Service
public class TaxiIntegration {

    @Value("taxi.txt")
    private static String fileName;

    public static List<Taxi> readFile(){
        final LinkedList<Taxi> taxiStand = new LinkedList<>();
        final Resource resource = new ClassPathResource(fileName);
        try(InputStream inputStream = resource.getInputStream();
            Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)
        ) {
            while (scanner.hasNext()){
                final String[] items = scanner.nextLine().split("#");
                taxiStand.add(new Taxi(items[0], items[1], items[2], items[3]));
            }
            if(scanner.ioException() != null){
                throw scanner.ioException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return taxiStand;
    }

    public static void appendFile(Taxi taxi){
        final Resource resource = new ClassPathResource(fileName);
        try (FileWriter writer = new FileWriter(resource.getFile(), StandardCharsets.UTF_8)){
            final String convert = "%s#%s#%s#%s\n";
            final String line = String.format(convert, taxi.getName(), taxi.getLat(), taxi.getLng(), taxi.getTime());
            writer.append(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
