package com.jinax.hospital_management_backend.Entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jinax.hospital_management_backend.Component.BaseEnumToNumberSerializer;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @author : chara
 */
@Entity
@Table(name = "message")
public class Message {
    public static enum Type implements BaseEnum{
        PATIENT_COME(0),
        PATIENT_CAN_LEAVE(1),;

        private Integer code;

        Type(Integer code) {
            this.code = code;
        }

        public static Type type(Integer type) {
            switch (type){
                case 0:return PATIENT_COME;
                case 1:return PATIENT_CAN_LEAVE;
            }
            return null;
        }

        @Override
        public Integer getCode() {
            return code;
        }
    }

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    @JsonSerialize(using = BaseEnumToNumberSerializer.class)
    private Type type;

    private Long targetId;
    private String data;
    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
