package com.jinax.hospital_management_backend.Entity;

import javax.persistence.*;

/**
 * @author : chara
 */
@Entity
@Table(name = "ward")
public class Ward {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name = "district_id")
    private Long districtId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }
}
