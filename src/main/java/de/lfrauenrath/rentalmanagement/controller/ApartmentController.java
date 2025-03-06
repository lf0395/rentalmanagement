package de.lfrauenrath.rentalmanagement.controller;

import de.lfrauenrath.rentalmanagement.entity.Apartment;
import de.lfrauenrath.rentalmanagement.entity.House;
import de.lfrauenrath.rentalmanagement.repository.ApartmentRepository;
import de.lfrauenrath.rentalmanagement.repository.HouseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/apartments")
public class ApartmentController {

    private final ApartmentRepository apartmentRepository;
    private final HouseRepository houseRepository;

    public ApartmentController(ApartmentRepository apartmentRepository, HouseRepository houseRepository) {
        this.apartmentRepository = apartmentRepository;
        this.houseRepository = houseRepository;
    }

    @GetMapping
    public ResponseEntity<List<Apartment>> getAllApartments() {
        return ResponseEntity.ok(apartmentRepository.findAll());
    }

    @GetMapping("/distinct")
    public ResponseEntity<List<Apartment>> getDistinctApartments() {
        return ResponseEntity.ok(apartmentRepository.findByHouseIsNull());
    }

    @PostMapping
    public ResponseEntity<Apartment> createApartment(@RequestBody Apartment apartment) {
        House house = apartment.getHouse();
        if (house == null && apartment.getHouseId() != null) {
            house = this.houseRepository.findById(apartment.getHouseId()).orElseThrow();
        }
        apartment.setHouse(house);
        Apartment savedApartment = apartmentRepository.save(apartment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedApartment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Apartment> updateApartment(@PathVariable Long id, @RequestBody Apartment apartment) {
        Apartment dbApartment = apartmentRepository.findById(id).orElseThrow();
        dbApartment.setLivingArea(apartment.getLivingArea());
        dbApartment.setRoomCount(apartment.getRoomCount());
        dbApartment.setName(apartment.getName());
        dbApartment.setCity(apartment.getCity());
        dbApartment.setStreet(apartment.getStreet());
        dbApartment.setZipCode(apartment.getZipCode());
        dbApartment.setHouseNumber(apartment.getHouseNumber());
        this.apartmentRepository.save(dbApartment);
        return ResponseEntity.ok(dbApartment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApartment(@PathVariable Long id) {
        apartmentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
