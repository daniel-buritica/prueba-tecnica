package co.com.pruebatecnica.adapter;


import co.com.pruebatecnica.InventoryModel;
import co.com.pruebatecnica.adapter.helper.InventoryMapper;
import co.com.pruebatecnica.gateway.InventoryModelRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;


@Component
public class InventoryAdapterImpl implements InventoryModelRepository {


    private final String DEPRECIATION_PERCENTAGE;


    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    public InventoryAdapterImpl(@Value("${inventory.depreciation}") String DEPRECIATION_PERCENTAGE,
                                InventoryRepository inventoryRepository, InventoryMapper inventoryMapper) {
        this.DEPRECIATION_PERCENTAGE = DEPRECIATION_PERCENTAGE;
        this.inventoryRepository = inventoryRepository;
        this.inventoryMapper = inventoryMapper;
    }

    @Override
    public List<InventoryModel> findAll() {
        var entityList = inventoryRepository.findAll();
        return inventoryMapper.toModelList(entityList);
    }

    @Override
    public InventoryModel findById(int id) {
        var inventoryEntity = inventoryRepository.findInventoryById(id);
        return inventoryMapper.toModel(inventoryEntity);
    }

    @Override
    public InventoryModel create(InventoryModel inventoryModel) {
        var inventoryEntity = inventoryMapper.toEntity(inventoryModel);
        var inventoryData = inventoryRepository.save(inventoryEntity);
        return inventoryMapper.toModel(inventoryData);
    }

    @Override
    public InventoryModel update(InventoryModel inventoryModel) {
        var inventoryEntity = inventoryRepository.findInventoryById(inventoryModel.getId());
        var inventoryUpdate = Inventory.builder()
                .id(inventoryEntity.getId())
                .numberSerial(inventoryModel.getNumberSerial())
                .description(inventoryModel.getDescription())
                .name(inventoryModel.getName())
                .datePurchase(inventoryModel.getDatePurchase())
                .purchaseValue(inventoryModel.getPurchaseValue())
                .build();
        var inventorUpdateEntity = inventoryRepository.save(inventoryUpdate);
        return inventoryMapper.toModel(inventorUpdateEntity);
    }

    @Override
    public Boolean delete(int id) {
       return inventoryRepository.deleteById(id);
    }

    @Override
    public Boolean exist(int id) {
        return inventoryRepository.existsById(id);
    }

    @Override
    public double calculateDepreciation(int purchaseValue) {
        String DEFAULT_VALUE_PERCENTAGE = "4";
        var n = Objects.requireNonNullElse(this.DEPRECIATION_PERCENTAGE, DEFAULT_VALUE_PERCENTAGE);
        double d2 = Double.parseDouble(n) / 100;
        return purchaseValue - (purchaseValue * d2);
    }
}
