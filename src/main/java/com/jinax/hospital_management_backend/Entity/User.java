package com.jinax.hospital_management_backend.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jinax.hospital_management_backend.Component.BaseEnumToNumberSerializer;

import javax.persistence.*;

/**
 * @author : chara
 */
@Entity
@Table(name = "user")
public class User {

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    public static enum roleType implements BaseEnum{
        DOCTOR(0),
        CHIEF_NURSE(1),
        WARD_NURSE(2),
        EMERGENCY_NURSE(3);
        private Integer code;

        roleType(Integer code) {
            this.code = code;
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
    @Column(length = 30,name = "password")
    private String password;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = BaseEnumToNumberSerializer.class)
    private roleType role;
    @Column(name = "district_id")
    private Long districtId;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public roleType getRole() {
        return role;
    }

    public void setRole(roleType role) {
        this.role = role;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }
}
