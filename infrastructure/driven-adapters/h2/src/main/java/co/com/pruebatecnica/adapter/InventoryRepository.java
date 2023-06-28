package co.com.pruebatecnica.adapter;

import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    Inventory findInventoryById(int id);

    Boolean deleteById(int id);

}
