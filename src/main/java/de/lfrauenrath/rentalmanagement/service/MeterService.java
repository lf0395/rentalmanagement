package de.lfrauenrath.rentalmanagement.service;

import de.lfrauenrath.rentalmanagement.entity.Meter;
import de.lfrauenrath.rentalmanagement.entity.MeterType;
import de.lfrauenrath.rentalmanagement.entity.MeterValue;
import de.lfrauenrath.rentalmanagement.entity.RentalProperty;
import de.lfrauenrath.rentalmanagement.repository.ApartmentRepository;
import de.lfrauenrath.rentalmanagement.repository.MeterRepository;
import de.lfrauenrath.rentalmanagement.repository.MeterValueRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MeterService {

    private ApartmentRepository apartmentRepository;
    private MeterRepository meterRepository;
    private MeterValueRepository meterValueRepository;

    public MeterService(ApartmentRepository apartmentRepository, MeterRepository meterRepository,
                        MeterValueRepository meterValueRepository) {
        this.apartmentRepository = apartmentRepository;
        this.meterRepository = meterRepository;
        this.meterValueRepository = meterValueRepository;
    }

    public double calculateConsumption(RentalProperty rentalProperty, LocalDate startDate,
                                       LocalDate endDate, MeterType meterType) {
        List<Meter> meterList = this.meterRepository.findByRentalPropertyAndType(rentalProperty, meterType);
        double consumption = 0.0;
        for (Meter meter : meterList) {
            List<MeterValue> meterValueList = meterValueRepository.findAllByMeterAndMeasureDateBetweenOrderByMeasureDateAsc(meter, startDate, endDate);
            consumption += meterValueList.get(meterValueList.size() - 1).getValue() - meterValueList.get(0).getValue();
        }
        return consumption;
    }
}
