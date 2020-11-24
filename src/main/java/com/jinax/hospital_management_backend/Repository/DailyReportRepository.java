package com.jinax.hospital_management_backend.Repository;

import com.jinax.hospital_management_backend.Entity.DailyReport;
import com.jinax.hospital_management_backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : chara
 */
@Repository
public interface DailyReportRepository extends JpaRepository<DailyReport,Long> {
}
