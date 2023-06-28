package co.com.pruebatecnica;

import co.com.pruebatecnica.usecase.InventoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/inventory")
@RestController
public class InventoryController {

    private final InventoryUseCase inventoryUseCase;


    @PostMapping("/create")
    public ResponseEntity<InventoryModel> create(@RequestBody InventoryModel inventoryModel){
        if (inventoryModel.getName() == null){
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(inventoryUseCase.create(inventoryModel), HttpStatus.CREATED);
    }

    @GetMapping("/health")
    public ResponseEntity<String> checkHealth(){
        return new ResponseEntity<>("status: running",HttpStatus.ACCEPTED);
    }



    @PutMapping("/update")
    public ResponseEntity<InventoryModel> update(@RequestBody InventoryModel inventoryModel){
        if (!inventoryUseCase.validateExists(inventoryModel.getId())){
            return ResponseEntity.notFound().build();
        }
        return  new ResponseEntity<>(inventoryUseCase.update(inventoryModel),HttpStatus.CREATED);
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<InventoryModel> findById(@PathVariable int id){
        if (!inventoryUseCase.validateExists(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(inventoryUseCase.findById(id));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<InventoryModel>> findAll (){
        return new ResponseEntity<>(inventoryUseCase.findAll(),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        if (!inventoryUseCase.validateExists(id)){
            return ResponseEntity.notFound().build();
        }
        inventoryUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

}
