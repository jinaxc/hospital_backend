package com.jinax.hospital_management_backend.Service;

import com.jinax.hospital_management_backend.Entity.User;
import com.jinax.hospital_management_backend.Exception.UserNotExistedException;
import com.jinax.hospital_management_backend.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

}
