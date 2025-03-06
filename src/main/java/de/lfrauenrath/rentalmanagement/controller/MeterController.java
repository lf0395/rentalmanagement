package de.lfrauenrath.rentalmanagement.controller;

import de.lfrauenrath.rentalmanagement.entity.Meter;
import de.lfrauenrath.rentalmanagement.entity.MeterValue;
import de.lfrauenrath.rentalmanagement.repository.MeterRepository;
import de.lfrauenrath.rentalmanagement.repository.MeterValueRepository;
import de.lfrauenrath.rentalmanagement.repository.RentalPropertyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("meters")
public class MeterController {

    private final MeterRepository meterRepository;
    private final MeterValueRepository meterValueRepository;
    private final RentalPropertyRepository rentalPropertyRepository;

    public MeterController(MeterRepository meterRepository, MeterValueRepository meterValueRepository,
                           RentalPropertyRepository rentalPropertyRepository) {
        this.meterRepository = meterRepository;
        this.meterValueRepository = meterValueRepository;
        this.rentalPropertyRepository = rentalPropertyRepository;
    }

    /** 📌 Alle Zähler abrufen **/
    @GetMapping
    public List<Meter> getAllMeters() {
        return meterRepository.findAll();
    }

    /** 📌 Einzelnen Zähler abrufen **/
    @GetMapping("/{id}")
    public ResponseEntity<Meter> getMeterById(@PathVariable Long id) {
        return meterRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** 📌 Neuen Zähler erstellen **/
    @PostMapping
    public ResponseEntity<Meter> createMeter(@RequestBody Meter meter) {
        meter.setRentalProperty(rentalPropertyRepository.findById(meter.getRentalProperty().getId())
                .orElseThrow(() -> new RuntimeException("Rental Property not found")));
        return ResponseEntity.status(HttpStatus.CREATED).body(meterRepository.save(meter));
    }

    /** 📌 Zähler aktualisieren **/
    @PutMapping("/{id}")
    public ResponseEntity<Meter> updateMeter(@PathVariable Long id, @RequestBody Meter updatedMeter) {
        return meterRepository.findById(id).map(existingMeter -> {
            existingMeter.setType(updatedMeter.getType());
            existingMeter.setRentalProperty(updatedMeter.getRentalProperty());
            return ResponseEntity.ok(meterRepository.save(existingMeter));
        }).orElse(ResponseEntity.notFound().build());
    }

    /** 📌 Zähler löschen **/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeter(@PathVariable Long id) {
        if (!meterRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        meterRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /** 📌 Alle Werte eines Zählers abrufen **/
    @GetMapping("/{id}/values")
    public ResponseEntity<List<MeterValue>> getMeterValues(@PathVariable Long id) {
        Optional<Meter> meter = meterRepository.findById(id);
        return meter.map(value -> ResponseEntity.ok(value.getMeterValueList()))
                .orElse(ResponseEntity.notFound().build());
    }

    /** 📌 Neuen Messwert hinzufügen **/
    @PostMapping("/{id}/values")
    public ResponseEntity<MeterValue> addMeterValue(@PathVariable Long id, @RequestBody MeterValue meterValue) {
        return meterRepository.findById(id).map(meter -> {
            meterValue.setMeter(meter);
            return ResponseEntity.status(HttpStatus.CREATED).body(meterValueRepository.save(meterValue));
        }).orElse(ResponseEntity.notFound().build());
    }
}