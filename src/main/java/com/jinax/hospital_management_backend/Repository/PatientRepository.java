package com.jinax.hospital_management_backend.Repository;

import com.jinax.hospital_management_backend.Entity.Patient;
import com.jinax.hospital_management_backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : chara
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {
    public List<Patient> findAllByNurseId(long nurseId);
}
