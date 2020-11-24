package com.jinax.hospital_management_backend.Entity;

import javax.persistence.*;

/**
 * @author : chara
 */
@Entity
@Table(name = "bed")
public class Bed {
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name = "ward_id")
    private Long wardId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWardId() {
        return wardId;
    }

    public void setWardId(Long wardId) {
        this.wardId = wardId;
    }
}
