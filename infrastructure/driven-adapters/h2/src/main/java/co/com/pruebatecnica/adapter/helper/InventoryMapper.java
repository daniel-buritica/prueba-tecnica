package co.com.pruebatecnica.adapter.helper;

import co.com.pruebatecnica.InventoryModel;
import co.com.pruebatecnica.MapperGeneric;
import co.com.pruebatecnica.adapter.Inventory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InventoryMapper implements MapperGeneric<Inventory, InventoryModel> {
    @Override
    public Inventory toEntity(InventoryModel model) {
        return Inventory.builder()
                .numberSerial(model.getNumberSerial())
                .description(model.getDescription())
                .name(model.getName())
                .datePurchase(model.getDatePurchase())
                .purchaseValue(model.getPurchaseValue())
                .build();
    }

    @Override
    public InventoryModel toModel(Inventory entity) {
        return InventoryModel.builder()
                .id(entity.getId())
                .numberSerial(entity.getNumberSerial())
                .description(entity.getDescription())
                .name(entity.getName())
                .datePurchase(entity.getDatePurchase())
                .purchaseValue(entity.getPurchaseValue())
                .build();
    }

    public List<InventoryModel> toModelList(List<Inventory> entityList){
        return entityList.stream().map(this::toModel)
                .toList();
    }
}
