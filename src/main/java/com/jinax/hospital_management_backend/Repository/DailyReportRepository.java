package com.jinax.hospital_management_backend.Repository;

import com.jinax.hospital_management_backend.Entity.DailyReport;
import com.jinax.hospital_management_backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author : chara
 */
@Repository
public interface DailyReportRepository extends JpaRepository<DailyReport,Long> {
    Optional<DailyReport> findByTestIdEquals(long testId);
    @Query(value = "select ID from daily_report where patient_id = ?1",nativeQuery = true)
    List<Long> findAllByPatientIdIs(long patientID);
}
