package co.com.pruebatecnica.adapter;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "number_serial")
    private int numberSerial;
    @Column(name = "description")
    private String description;
    @Column(name = "name")
    private String name;
    @Column(name = "date_purchase")
    private String datePurchase;
    @Column(name = "purchase_value")
    private int purchaseValue;


}
