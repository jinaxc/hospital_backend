package com.jinax.hospital_management_backend.Repository;

import com.jinax.hospital_management_backend.Entity.Test;
import com.jinax.hospital_management_backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : chara
 */
@Repository
public interface TestRepository extends JpaRepository<Test,Long> {
}
