package com.jinax.hospital_management_backend.Service;

import com.jinax.hospital_management_backend.Component.MyUserDetails;
import com.jinax.hospital_management_backend.Entity.Patient;
import com.jinax.hospital_management_backend.Entity.User;
import com.jinax.hospital_management_backend.Exception.UserNotExistedException;
import com.jinax.hospital_management_backend.Repository.PatientRepository;
import com.jinax.hospital_management_backend.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * @author : chara
 */
@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    public UserService(UserRepository userRepository, PatientRepository patientRepository) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
    }

    public List<User> findByName(String name){
        return userRepository.findByName(name);
    }
    public User findById(long id) throws UserNotExistedException {
        LOGGER.info("find byId, id is {}",id);
        Optional<User> byId = userRepository.findById(id);
        if(byId.isPresent()){
            return byId.get();
        }
        throw new UserNotExistedException("user not exist, id is " + id);
    }
    public User findByIdentification(String identification){
        return userRepository.findByIdentification(identification);
    }

    public User findChiefNurseByDistrictId(long districtId){
        List<User> byDistrictIdAndRole = userRepository.findByDistrictIdAndRole(districtId, User.roleType.CHIEF_NURSE);
        return byDistrictIdAndRole.isEmpty()? null : byDistrictIdAndRole.get(0);
    }
    public List<User> findWardNurseByDistrictId(long districtId){
        return userRepository.findByDistrictIdAndRole(districtId, User.roleType.WARD_NURSE);
    }

    public boolean setWardNurseToDistrict(long nurseId,long districtId){
        User user = findById(nurseId);
        if(user.getRole() != User.roleType.WARD_NURSE || !patientRepository.findAllByNurseId(nurseId).isEmpty()){
            return false;
        }else{
            user.setDistrictId(districtId);
            userRepository.save(user);
            return true;
        }
    }

    public boolean removeWardNurseFromDistrict(long nurseId){
        MyUserDetails details = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long districtId = details.getDistrictId();
        User user = findById(nurseId);
        if(user.getRole() != User.roleType.WARD_NURSE || districtId != user.getDistrictId() || !patientRepository.findAllByNurseId(nurseId).isEmpty()){
            return false;
        }else{
            user.setDistrictId(null);
            userRepository.save(user);
            return true;
        }
    }

}
