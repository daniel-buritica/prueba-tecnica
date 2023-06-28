package co.com.pruebatecnica;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventoryModelTest {

    double delta ;
    int id = 1;
    int numberSerial ;
    String description ;
    String name ;
    String datePurchase ;
    int purchaseValue ;
    double depreciationValue ;

    @BeforeEach
    void setUp(){
        delta = 0.00001;
        id = 1;
        numberSerial = 12345;
        description = "Sample description";
        name = "Sample name";
        datePurchase = "2023-06-27";
        purchaseValue = 1000;
        depreciationValue = 900.0;
    }


    @Test
    void testGettersAndSetters() {
        InventoryModel inventoryModel = new InventoryModel();

        inventoryModel.setId(id);
        inventoryModel.setNumberSerial(numberSerial);
        inventoryModel.setDescription(description);
        inventoryModel.setName(name);
        inventoryModel.setDatePurchase(datePurchase);
        inventoryModel.setPurchaseValue(purchaseValue);
        inventoryModel.setDepreciationValue(depreciationValue);

        Assertions.assertEquals(inventoryModel.getId(), id);
        Assertions.assertEquals(inventoryModel.getNumberSerial(), numberSerial);
        Assertions.assertEquals(inventoryModel.getDescription(), description);
        Assertions.assertEquals(inventoryModel.getName(), name);
        Assertions.assertEquals(inventoryModel.getDatePurchase(), datePurchase);
        Assertions.assertEquals(inventoryModel.getPurchaseValue(), purchaseValue);
        Assertions.assertEquals(inventoryModel.getDepreciationValue(), depreciationValue, delta);

    }

    @Test
    void testAllArgsConstructor() {
        InventoryModel inventoryModel = new InventoryModel(id,numberSerial,description,name,datePurchase,purchaseValue
                ,depreciationValue);

        Assertions.assertEquals(inventoryModel.getId(), id);
        Assertions.assertEquals(inventoryModel.getNumberSerial(), numberSerial);
        Assertions.assertEquals(inventoryModel.getDescription(), description);
        Assertions.assertEquals(inventoryModel.getName(), name);
        Assertions.assertEquals(inventoryModel.getDatePurchase(), datePurchase);
        Assertions.assertEquals(inventoryModel.getPurchaseValue(), purchaseValue);
        Assertions.assertEquals(inventoryModel.getDepreciationValue(), depreciationValue, delta);

    }

    @Test
    void testBuilder() {
        var data = InventoryModel.builder()
                .id(id)
                .numberSerial(numberSerial)
                .description(description)
                .name(name)
                .datePurchase(datePurchase)
                .purchaseValue(purchaseValue)
                .depreciationValue(depreciationValue)
                .build();

        Assertions.assertEquals(data.getId(), id);
        Assertions.assertEquals(data.getNumberSerial(), numberSerial);
        Assertions.assertEquals(data.getDescription(), description);
        Assertions.assertEquals(data.getName(), name);
        Assertions.assertEquals(data.getDatePurchase(), datePurchase);
        Assertions.assertEquals(data.getPurchaseValue(), purchaseValue);
        Assertions.assertEquals(data.getDepreciationValue(), depreciationValue, delta);
    }
}