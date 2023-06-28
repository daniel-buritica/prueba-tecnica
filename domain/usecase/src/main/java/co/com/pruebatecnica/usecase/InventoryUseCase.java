package co.com.pruebatecnica.usecase;

import co.com.pruebatecnica.InventoryModel;
import co.com.pruebatecnica.gateway.InventoryModelRepository;
import lombok.AllArgsConstructor;

import java.util.List;


@AllArgsConstructor
public class InventoryUseCase {

    private InventoryModelRepository inventoryModelRepository;

    public InventoryModel create(InventoryModel inventoryModel){
        var modelData = inventoryModelRepository.create(inventoryModel);
        return updateObjectWithPrediction(modelData);
    }
    public InventoryModel update(InventoryModel inventoryModel){
        var modelData = inventoryModelRepository.update(inventoryModel);
        return updateObjectWithPrediction(modelData);
    }
    public List<InventoryModel> findAll(){
        var listData = inventoryModelRepository.findAll();
        return listData.stream().map(this::updateObjectWithPrediction)
                .toList();
    }
    public InventoryModel findById(int id){
        var modelData = inventoryModelRepository.findById(id);
        return updateObjectWithPrediction(modelData);
    }
    public Boolean delete(int id){
        return inventoryModelRepository.delete(id);
    }

    public boolean validateExists(int id){
        return inventoryModelRepository.exist(id);
    }

    public InventoryModel updateObjectWithPrediction(InventoryModel inventoryModel){
        double depreciationValue = inventoryModelRepository.calculateDepreciation(inventoryModel.getPurchaseValue());
        return InventoryModel.builder()
                .id(inventoryModel.getId())
                .numberSerial(inventoryModel.getNumberSerial())
                .description(inventoryModel.getDescription())
                .name(inventoryModel.getName())
                .datePurchase(inventoryModel.getDatePurchase())
                .purchaseValue(inventoryModel.getPurchaseValue())
                .depreciationValue(depreciationValue)
                .build();
    }

}
