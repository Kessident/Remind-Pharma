package com.cdodge.remindPharm.Repository;

import com.cdodge.remindPharm.Models.Medication;
import org.springframework.data.repository.CrudRepository;

public interface MedicationRepo extends CrudRepository<Medication, Integer> {

}
