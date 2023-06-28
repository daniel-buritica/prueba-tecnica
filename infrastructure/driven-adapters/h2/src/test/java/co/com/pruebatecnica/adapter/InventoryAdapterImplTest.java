package co.com.pruebatecnica.adapter;

import co.com.pruebatecnica.InventoryModel;
import co.com.pruebatecnica.adapter.helper.InventoryMapper;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class InventoryAdapterImplTest {


    @Mock
    InventoryRepository inventoryRepository;
    @Mock
    InventoryMapper inventoryMapper;
    @InjectMocks
    InventoryAdapterImpl inventoryAdapter;
    private Inventory inventoryEntity;
    private InventoryModel inventoryModel;
    private static final int ID_INVENTORY = 1;
    private static final int PURCHASE_VALE = 500000;
    private static final double PERCENTAGE_VALE = 0.04;

    @BeforeEach
    void setUp(){

        inventoryEntity = Inventory.builder()
                .id(ID_INVENTORY)
                .numberSerial(12365)
                .description("Test Description")
                .name("Test name")
                .datePurchase("2021-01-01")
                .purchaseValue(5000000)
                .build();
        inventoryModel = InventoryModel.builder()
                .id(ID_INVENTORY)
                .numberSerial(12369)
                .description("Test description")
                .name("Test name")
                .datePurchase("2021-01-01")
                .purchaseValue(50000000)
                .build();
    }

    @Test
    void testFindAll() {
        var entityList = Arrays.asList(inventoryEntity, inventoryEntity);
        var modelList = Arrays.asList(inventoryModel, inventoryModel);
        when(inventoryRepository.findAll()).thenReturn(entityList);
        when(inventoryMapper.toModelList(entityList)).thenReturn(modelList);
        var resultData = inventoryAdapter.findAll();
        Assert.assertNotNull(resultData);
    }

    @Test
    void testFindById() {
        when(inventoryRepository.findInventoryById(ID_INVENTORY)).thenReturn(inventoryEntity);
        when(inventoryMapper.toModel(inventoryEntity)).thenReturn(inventoryModel);
        var resultData = inventoryAdapter.findById(ID_INVENTORY);
        Assert.assertNotNull(resultData);
        assertEquals(ID_INVENTORY,resultData.getId());

    }

    @Test
    void testCreate() {
        when(inventoryMapper.toEntity(inventoryModel)).thenReturn(inventoryEntity);
        when(inventoryRepository.save(inventoryEntity)).thenReturn(inventoryEntity);
        when(inventoryMapper.toModel(inventoryEntity)).thenReturn(inventoryModel);

        var resultData = inventoryAdapter.create(inventoryModel);
        Assert.assertNotNull(resultData);
        assertEquals(inventoryModel.getName(), resultData.getName());
    }


    @Test
    void testUpdate() {
        when(inventoryRepository.findInventoryById(ID_INVENTORY)).thenReturn(inventoryEntity);
        ArgumentCaptor<Inventory> captor = ArgumentCaptor.forClass(Inventory.class);
        when(inventoryRepository.save(captor.capture())).thenReturn(inventoryEntity);
        when(inventoryMapper.toModel(inventoryEntity)).thenReturn(inventoryModel);
        var resultData = inventoryAdapter.update(inventoryModel);
        Assert.assertNotNull(resultData);
        assertEquals(inventoryModel.getName(), resultData.getName());
    }

    @Test
    void testDelete() {
        when(inventoryRepository.deleteById(ID_INVENTORY)).thenReturn(Boolean.TRUE);
        var resultData = inventoryAdapter.delete(ID_INVENTORY);
        Assert.assertTrue(resultData);
    }

    @Test
    void testExists() {
        when(inventoryRepository.existsById(ID_INVENTORY)).thenReturn(Boolean.TRUE);
        var resultData = inventoryAdapter.exist(ID_INVENTORY);
        Assert.assertTrue(resultData);
    }

    @Test
    void testCalculateDepreciation() {
        double delta = 0.0001;
        var expectedData = PURCHASE_VALE - (PURCHASE_VALE *PERCENTAGE_VALE);
        var resultData = inventoryAdapter.calculateDepreciation(PURCHASE_VALE);

        assertEquals(expectedData, resultData,delta);

    }
}