package com.jinax.hospital_management_backend.Entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jinax.hospital_management_backend.Component.BaseEnumToNumberSerializer;

import javax.persistence.*;

/**
 * @author : chara
 */
@Entity
@Table(name = "patient")
public class Patient {
    public static enum Level implements BaseEnum{
        MINOR(0),
        MAJOR(1),
        DANGER(2);

        private Integer code;

        Level(int i) {
            this.code = i;
        }

        public static Level getLevel(Integer level) {
            if(level == null){
                return null;
            }
            switch (level){
                case 0:return MINOR;
                case 1:return MAJOR;
                case 2:return DANGER;
            }
            return null;
        }

        @Override
        public Integer getCode() {
            return code;
        }
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30,name = "name")
    private String name;
    @Column(length = 30,name = "identification")
    private String identification;
    @Column(length = 100,name = "location")
    private String location;
    @Column(name = "level")
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = BaseEnumToNumberSerializer.class)
    private Level level;
    @Column(name = "district_id")
    private Long districtId;
    @Column(name = "nurse_id")
    private Long nurseId;
    @Column(name = "bed_id")
    private Long bedId;
    @Column(name = "initial_test_id")
    private Long initialTestId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Long getNurseId() {
        return nurseId;
    }

    public void setNurseId(Long nurseId) {
        this.nurseId = nurseId;
    }

    public Long getBedId() {
        return bedId;
    }

    public void setBedId(Long bedId) {
        this.bedId = bedId;
    }

    public Long getInitialTestId() {
        return initialTestId;
    }

    public void setInitialTestId(Long initialTestId) {
        this.initialTestId = initialTestId;
    }
}
