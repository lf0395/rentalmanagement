package de.lfrauenrath.rentalmanagement.controller;

import de.lfrauenrath.rentalmanagement.entity.House;
import de.lfrauenrath.rentalmanagement.repository.HouseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/houses")
public class HouseController {

    private final HouseRepository houseRepository;
    
    public HouseController(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('user','admin')")
    public ResponseEntity<List<House>> getAllHouses() {
        return ResponseEntity.ok(houseRepository.findAll());
    }

    @GetMapping("/rentable")
    @PreAuthorize("hasAnyAuthority('user','admin')")
    public ResponseEntity<List<House>> getRentedHouses() {
        return ResponseEntity.ok(houseRepository.findByIsEntireHouseRented(true));
    }

    @GetMapping("/billable")
    @PreAuthorize("hasAnyAuthority('user','admin')")
    public ResponseEntity<List<House>> getBillableHouses() {
        return ResponseEntity.ok(houseRepository.findByIsEntireHouseRented(false));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<House> createHouse(@RequestBody House house) {
        House savedHouse = houseRepository.save(house);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedHouse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<House> updateHouse(@PathVariable Long id, @RequestBody House house) {
        House dbHouse = houseRepository.findById(id).orElseThrow();
        dbHouse.setHouseNumber(house.getHouseNumber());
        dbHouse.setStreet(house.getStreet());
        dbHouse.setZipCode(house.getZipCode());
        dbHouse.setCity(house.getCity());
        dbHouse.setRoomCount(house.getRoomCount());
        dbHouse.setLivingArea(house.getLivingArea());
        dbHouse.setLandSize(house.getLandSize());
        houseRepository.save(dbHouse);
        return ResponseEntity.ok(dbHouse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<Void> deleteHouse(@PathVariable Long id) {
        houseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
