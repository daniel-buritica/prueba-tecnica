package co.com.pruebatecnica;

import co.com.pruebatecnica.usecase.InventoryUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
class InventoryControllerTest {

    @Mock
    private InventoryUseCase inventoryUseCase;

    @InjectMocks
    private InventoryController inventoryController;
    private MockMvc mockMvc;
    private InventoryModel inventoryModel;
    private static final int ID_INVENTORY = 1;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(inventoryController).build();
        inventoryModel = InventoryModel.builder()
                .id(1)
                .numberSerial(12369)
                .description("Test description")
                .name("Test name")
                .datePurchase("2021-01-01")
                .purchaseValue(50000000)
                .build();
    }

    @Test
    void testCheckHealth() throws Exception {

        mockMvc.perform(get("/api/v1/inventory/health"))
                .andExpect(status().isAccepted())
                .andExpect(content().string("status: running"));
    }

    @Test
    void testCreateSuccessful() throws Exception {
        when(inventoryUseCase.create(inventoryModel)).thenReturn(inventoryModel);
        var response = mockMvc.perform(post("/api/v1/inventory/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inventoryModel)))
                .andExpect(status().isCreated());
        verify(inventoryUseCase, times(1)).create(any());
    }

    @Test
    void testCreateFail() throws Exception{
        var data = mockMvc.perform(post("/api/v1/inventory/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateSuccessful() throws Exception {
        when(inventoryUseCase.validateExists(ID_INVENTORY)).thenReturn(Boolean.TRUE);
        when(inventoryUseCase.update(inventoryModel)).thenReturn(inventoryModel);
        var response = mockMvc.perform(put("/api/v1/inventory/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inventoryModel)))
                .andExpect(status().isCreated());
        verify(inventoryUseCase, times(1)).update(any());
    }

    @Test
    void testUpdateSuccessfulFail() throws Exception {
        when(inventoryUseCase.validateExists(ID_INVENTORY)).thenReturn(Boolean.FALSE);
        when(inventoryUseCase.update(inventoryModel)).thenReturn(inventoryModel);
        var response = mockMvc.perform(put("/api/v1/inventory/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inventoryModel)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindByIdSuccessful() throws Exception {
        when(inventoryUseCase.validateExists(ID_INVENTORY)).thenReturn(Boolean.TRUE);
        when(inventoryUseCase.findById(ID_INVENTORY)).thenReturn(inventoryModel);
        var response = mockMvc.perform(get("/api/v1/inventory/findbyid/" + ID_INVENTORY))
                        .andExpect(status().isOk());
        verify(inventoryUseCase, times(1)).findById(ID_INVENTORY);
    }

    @Test
    void testFindByIdFail() throws Exception {
        when(inventoryUseCase.validateExists(ID_INVENTORY)).thenReturn(Boolean.FALSE);
        var response = mockMvc.perform(get("/api/v1/inventory/findbyid/" + ID_INVENTORY))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindAllSuccessful() throws Exception {
        var modelList = Arrays.asList(inventoryModel,inventoryModel,inventoryModel);
        when(inventoryUseCase.findAll()).thenReturn(modelList);

        var response = mockMvc.perform(get("/api/v1/inventory/findall"))
                .andExpect(status().isAccepted());
        verify(inventoryUseCase, times(1)).findAll();
    }

    @Test
    void testDeleteSuccessful() throws Exception {
        when(inventoryUseCase.validateExists(ID_INVENTORY)).thenReturn(Boolean.TRUE);
        when(inventoryUseCase.delete(ID_INVENTORY)).thenReturn(Boolean.TRUE);
        var response = mockMvc.perform(delete("/api/v1/inventory/delete/" + ID_INVENTORY))
                .andExpect(status().isNoContent());
        verify(inventoryUseCase, times(1)).delete(ID_INVENTORY);
    }

    @Test
    void testDeleteFail() throws Exception {
        when(inventoryUseCase.validateExists(ID_INVENTORY)).thenReturn(Boolean.FALSE);
        var response = mockMvc.perform(delete("/api/v1/inventory/delete/" + ID_INVENTORY))
                .andExpect(status().isNotFound());
    }

    public static String asJsonString(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}