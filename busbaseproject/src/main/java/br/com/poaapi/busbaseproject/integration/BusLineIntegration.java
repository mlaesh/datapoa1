package br.com.poaapi.busbaseproject.integration;

import br.com.poaapi.busbaseproject.models.BusLine;
import br.com.poaapi.busbaseproject.models.Coordinates;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Service
public class BusLineIntegration {

    @Value("${app.apiURI}")
    private static String URI;

    public List<BusLine> allLines(RestTemplate restTemplate, ObjectMapper objectMapper) {
        String lines = restTemplate.getForObject(URI + "?a=nc&p=%&t=o", String.class);
        try{
            return Arrays.asList(objectMapper.readValue(lines, BusLine[].class));
        } catch (IOException e){
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public void addCoordinatesToLine(RestTemplate restTemplate, BusLine busLine) throws JsonProcessingException {
        Map<Integer, Coordinates> lineItinerary = getListOfCoordinates(busLine.getId(), restTemplate);
        List<Double[]> points = new ArrayList<>();
        lineItinerary.forEach((i, c) -> points.add(new Double[]{c.getLat(), c.getLng()}));
        busLine.setCoordinates(points);
    }

    private Map<Integer, Coordinates> getListOfCoordinates(Integer id, RestTemplate restTemplate) throws JsonProcessingException {
        String convert = readJustNameLine(Objects.requireNonNull(restTemplate
                .getForObject(URI + "?a=il&p=" + id, String.class)));
        return new ObjectMapper().readValue(convert, new TypeReference<>() {
        });
    }

    private String readJustNameLine(String coordinates){
        StringBuilder stringBuilder = new StringBuilder();
        String[] split = coordinates.split(",");
        stringBuilder.append("{");
        for(int i = 3; i<= split.length-1; i++){
            stringBuilder.append(split[i]);
            stringBuilder.append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        return stringBuilder.toString();
    }

}
