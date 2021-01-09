package com.jinax.hospital_management_backend.Repository;

import com.jinax.hospital_management_backend.Entity.Bed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author : chara
 */
@Repository
public interface BedRepository extends JpaRepository<Bed,Long> {

    @Query(value = "select bed.ID from bed left join ward on bed.ward_id = ward.ID left join patient on bed.ID = patient.bed_id where ward.district_id = ?1 and coalesce(bed_id,0) = 0",nativeQuery = true)
    public List<Long> findEmptyBedInGivenDistrict(long districtId);

    @Query(value = "select bed_id,patient.ID from bed left join patient on bed.ID = patient.bed_id where district_id = ?1",nativeQuery = true)
    public List<Map<Long,Long>> findAllBedWithPatientIdInGivenDistrict(long districtId);

    @Query(value = "select bed_id,patient.name from bed left join patient on bed.ID = patient.bed_id where district_id = ?1",nativeQuery = true)
    public List<Map<Long,String>> findAllBedWithPatientNameInGivenDistrict(long districtId);
}
