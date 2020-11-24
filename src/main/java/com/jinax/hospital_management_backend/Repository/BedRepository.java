package com.jinax.hospital_management_backend.Repository;

import com.jinax.hospital_management_backend.Entity.Bed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : chara
 */
@Repository
public interface BedRepository extends JpaRepository<Bed,Long> {

    @Query(value = "select bed.ID from bed left join ward on bed.ward_id = ward.ID left join patient on bed.ID = patient.bed_id where ward.district_id = ?1 and coalesce(bed_id,0) = 0",nativeQuery = true)
    public List<Long> findEmptyBedInGivenDistrict(long districtId);

}
