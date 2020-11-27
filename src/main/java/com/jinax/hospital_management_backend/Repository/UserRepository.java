package com.jinax.hospital_management_backend.Repository;

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
public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findByName(String name);
    User findByIdentification(String identification);
    List<User> findByDistrictIdAndRoleEquals(long districtId,User.roleType role);
    @Query(value = "select id from(select user.ID id,count(nurse_id) c from user left join patient on user.ID = patient.nurse_id where user.district_id = ?1 and role = 'WARD_NURSE' group by user.ID)as temp where c < ?2",nativeQuery = true)
    public List<Long> findFreeWardNurseInGivenDistrict(long districtId,int max);

    @Query(value ="select * from user where role = 'CHIEF_NURSE' and district_id = ?1",nativeQuery = true)
    Optional<User> findByDistrictIdEqualsAndRoleChiefNurse(long districtId);
}
