package com.jinax.hospital_management_backend.Repository;

import com.jinax.hospital_management_backend.Entity.District;
import com.jinax.hospital_management_backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : chara
 */
@Repository
public interface DistrictRepository extends JpaRepository<District,Long> {
    public List<District> findAllByType(District.Type type);
}
