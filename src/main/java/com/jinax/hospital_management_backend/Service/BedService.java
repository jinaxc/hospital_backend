package com.jinax.hospital_management_backend.Service;

import com.jinax.hospital_management_backend.Repository.BedRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author : chara
 */
@Service
public class BedService {
    private final BedRepository bedRepository;

    public BedService(BedRepository bedRepository) {
        this.bedRepository = bedRepository;
    }

    public Map<Long,Long> findAllBedWithPatientIdInGivenDistrict(Long districtId){
        return bedRepository.findAllBedWithPatientIdInGivenDistrict(districtId);
    }
}
