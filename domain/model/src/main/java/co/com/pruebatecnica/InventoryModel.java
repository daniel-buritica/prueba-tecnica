package co.com.pruebatecnica;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryModel {


    private int id;
    private int numberSerial;
    private String description;
    private String name;
    private String datePurchase;
    private int purchaseValue;
    private double depreciationValue;

}
