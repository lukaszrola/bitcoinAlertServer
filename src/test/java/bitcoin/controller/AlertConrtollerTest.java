package bitcoin.controller;

import bitcoin.model.Alert;
import bitcoin.service.AlertService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AlertConrtoller.class)
class AlertConrtollerTest {
    private static final String GET_ALERTS_URL = "/alerts";
    private static final String SOME_ALERT_NAME = "someAlert";
    private static final Alert SOME_ALERT = Alert.anAlert(SOME_ALERT_NAME, BigDecimal.valueOf(500L));
    private static final String SOME_ALERT_JSON = new Gson().toJson(SOME_ALERT);
    private static final Set<Alert> ALERTS = Set.of(SOME_ALERT);
    private static final String ALERTS_JSON = new Gson().toJson(ALERTS);
    private static final String ADD_ALERT_URL = "/alert?name=someAlert&limit=500";
    private static final String DELETE_ALERT_URL = "/alert?name=someAlert";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlertService alertService;


    @Test
    void getAlerts() throws Exception {
        Mockito.when(alertService.getAlerts()).thenReturn(ALERTS);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(GET_ALERTS_URL)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(ALERTS_JSON));
    }

    @Test
    void putAlert() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(ADD_ALERT_URL)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isAccepted())
                .andExpect(content().json(SOME_ALERT_JSON));
    }

    @Test
    void deleteAlert() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(DELETE_ALERT_URL)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isAccepted())
                .andExpect(content().string(SOME_ALERT_NAME));
    }
}