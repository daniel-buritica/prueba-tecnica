package co.com.pruebatecnica.adapter.helper;

import co.com.pruebatecnica.InventoryModel;
import co.com.pruebatecnica.adapter.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(MockitoJUnitRunner.class)
class InventoryMapperTest {

    Inventory inventoryEntity;
    InventoryModel inventoryModel;

    InventoryMapper inventoryMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        inventoryMapper = new InventoryMapper();

        inventoryEntity = Inventory.builder()
                .numberSerial(12365)
                .description("Test Description")
                .name("Test name")
                .datePurchase("2021-01-01")
                .purchaseValue(5000000)
                .build();
        inventoryModel = InventoryModel.builder()
                .numberSerial(12369)
                .description("Test description")
                .name("Test name")
                .datePurchase("2021-01-01")
                .purchaseValue(50000000)
                .build();
    }

    @Test
    void toEntityShouldMapModelToEntity() {
        InventoryMapper mapper = new InventoryMapper();
        var model = inventoryModel;
        Inventory entity = mapper.toEntity(model);
        assertEquals(model.getNumberSerial(), entity.getNumberSerial());
        assertEquals(model.getDescription(), entity.getDescription());
        assertEquals(model.getName(), entity.getName());
        assertEquals(model.getDatePurchase(), entity.getDatePurchase());
        assertEquals(model.getPurchaseValue(), entity.getPurchaseValue());
    }

    @Test
    void toModelShouldMapEntityToModel() {
        InventoryMapper mapper = new InventoryMapper();
        InventoryModel model = mapper.toModel(inventoryEntity);
        assertEquals(model.getNumberSerial(), inventoryEntity.getNumberSerial());
        assertEquals(model.getDescription(), inventoryEntity.getDescription());
        assertEquals(model.getName(), inventoryEntity.getName());
        assertEquals(model.getDatePurchase(), inventoryEntity.getDatePurchase());
        assertEquals(model.getPurchaseValue(), inventoryEntity.getPurchaseValue());
    }

    @Test
    void ToModelListShouldConvertEntityListToModelList() {
        List<Inventory> entityList = Arrays.asList(inventoryEntity, inventoryEntity);

        List<InventoryModel> modelList = inventoryMapper.toModelList(entityList);

        // Verify the results
        assertEquals(2, modelList.size());
        assertEquals(inventoryModel.getId(), modelList.get(0).getId());
        assertEquals(inventoryModel.getName(), modelList.get(0).getName());
    }



}