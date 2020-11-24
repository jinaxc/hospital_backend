package com.jinax.hospital_management_backend.Repository;

import com.jinax.hospital_management_backend.Entity.User;
import com.jinax.hospital_management_backend.Entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : chara
 */
@Repository
public interface WardRepository extends JpaRepository<Ward,Long> {
    public List<Ward> findAllByDistrictId(long districtId);
}
