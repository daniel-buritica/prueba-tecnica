package co.com.pruebatecnica.gateway;

import co.com.pruebatecnica.InventoryModel;
import java.util.List;


public interface InventoryModelRepository {

    List<InventoryModel> findAll();
    InventoryModel findById(int id);
    InventoryModel create(InventoryModel inventoryModel);
    InventoryModel update(InventoryModel inventoryModel);
    Boolean delete(int id);

    Boolean exist(int id);

    double calculateDepreciation(int purchaseValue);




}
