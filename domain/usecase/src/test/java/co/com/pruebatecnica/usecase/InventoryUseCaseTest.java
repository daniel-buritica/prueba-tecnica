package co.com.pruebatecnica.usecase;

import co.com.pruebatecnica.InventoryModel;
import co.com.pruebatecnica.gateway.InventoryModelRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class InventoryUseCaseTest {

    @Mock
    InventoryModelRepository inventoryModelRepository;

    @InjectMocks
    InventoryUseCase inventoryUseCase;

    private InventoryModel inventoryModel;
    private InventoryModel inventoryModelUpdate;
    private static final int ID_INVENTORY = 1;
    private static final double DELTA= 0.0001;


    @BeforeEach
    void setUp() {
        inventoryModel = InventoryModel.builder()
                .id(ID_INVENTORY)
                .numberSerial(12369)
                .description("Test description")
                .name("Test name")
                .datePurchase("2021-01-01")
                .purchaseValue(50000000)
                .build();
        inventoryModelUpdate = InventoryModel.builder()
                .id(ID_INVENTORY)
                .numberSerial(12369)
                .description("Test description")
                .name("Test name")
                .datePurchase("2021-01-01")
                .purchaseValue(50000000)
                .depreciationValue(500.0)
                .build();
    }

    @Test
    void testCreate() {

        when(inventoryModelRepository.create(any(InventoryModel.class))).thenReturn(inventoryModel);
        when(inventoryModelRepository.calculateDepreciation(50000000)).thenReturn(500.0);
        InventoryModel result = inventoryUseCase.create(inventoryModel);
        Assert.assertEquals(inventoryModelUpdate.getDepreciationValue(), result.getDepreciationValue(),DELTA);
        Assert.assertEquals(inventoryModelUpdate.getName(), result.getName());
    }

    @Test
    void testUpdate() {
        when(inventoryModelRepository.update(any(InventoryModel.class))).thenReturn(inventoryModel);
        when(inventoryModelRepository.calculateDepreciation(50000000)).thenReturn(500.0);
        InventoryModel result = inventoryUseCase.update(inventoryModel);
        Assert.assertEquals(inventoryModelUpdate.getDepreciationValue(), result.getDepreciationValue(),DELTA);
        Assert.assertEquals(inventoryModelUpdate.getName(), result.getName());
    }

    @Test
    void testFindAll() {
        var modelList = Arrays.asList(inventoryModel,inventoryModel);
        when(inventoryModelRepository.findAll()).thenReturn(modelList);
        when(inventoryModelRepository.calculateDepreciation(50000000)).thenReturn(500.0);
        var result= inventoryUseCase.findAll();
        Assert.assertEquals(modelList.size(), result.size());
        Assert.assertEquals(modelList.get(0).getName(), result.get(0).getName());
    }

    @Test
    void testFindById() {
        when(inventoryModelRepository.findById(ID_INVENTORY)).thenReturn(inventoryModel);
        when(inventoryModelRepository.calculateDepreciation(50000000)).thenReturn(500.0);

        InventoryModel result = inventoryUseCase.findById(ID_INVENTORY);
        Assert.assertEquals(inventoryModelUpdate.getDepreciationValue(), result.getDepreciationValue(),DELTA);
        Assert.assertEquals(inventoryModelUpdate.getName(), result.getName());

    }

    @Test
    void testDelete() {
        when(inventoryModelRepository.delete(ID_INVENTORY)).thenReturn(Boolean.TRUE);
        var result = inventoryUseCase.delete(ID_INVENTORY);
        Assert.assertTrue(result);
    }

    @Test
    void testValidateExists() {
        when(inventoryModelRepository.exist(ID_INVENTORY)).thenReturn(Boolean.TRUE);
        var result = inventoryUseCase.validateExists(ID_INVENTORY);
        Assert.assertTrue(result);
    }

    @Test
    void testUpdateObjectWithPrediction() {
        when(inventoryModelRepository.calculateDepreciation(50000000)).thenReturn(500.0);
        var result = inventoryUseCase.updateObjectWithPrediction(inventoryModel);
        Assert.assertEquals(inventoryModelUpdate.getDepreciationValue(), result.getDepreciationValue(),DELTA);
        Assert.assertEquals(inventoryModelUpdate.getName(), result.getName());

    }
}