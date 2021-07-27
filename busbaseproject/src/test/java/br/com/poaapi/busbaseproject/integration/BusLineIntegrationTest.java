package br.com.poaapi.busbaseproject.integration;


import br.com.poaapi.busbaseproject.models.BusLine;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BusLineIntegrationTest {

    private BusLineIntegration integration;
    @Mock
    private RestTemplate restTemplate;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private BusLine busLineTest;

    @Before
    public void set(){
        integration = new BusLineIntegration();
        ReflectionTestUtils.setField(integration, "URI",
                "http://www.poatransporte.com.br/php/facades/process.php");
        busLineTest = BusLine.builder()
                .id(1234)
                .code("Test")
                .name("Tested")
                .build();
    }

    @Test
    public void shouldReturnElements(){
        List<BusLine> lines = integration.allLines(new RestTemplate(), new ObjectMapper());
        assertEquals(989, lines.size());
    }

}
