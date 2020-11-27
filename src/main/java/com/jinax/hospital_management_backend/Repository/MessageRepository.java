package com.jinax.hospital_management_backend.Repository;


import com.jinax.hospital_management_backend.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : chara
 */
@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> findByTargetIdEquals(long targetId);

}
