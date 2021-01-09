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
@Table(name = "daily_report")
public class DailyReport {
    public static enum State implements BaseEnum {
        IN(0),
        OUT(1),
        DIE(2);
        private Integer code;

        State(Integer code) {
            this.code = code;
        }

        public static State getState(Integer level) {
            if(level == null){
                return null;
            }
            switch (level){
                case 0:return IN;
                case 1:return OUT;
                case 2:return DIE;
            }
            return null;
        }

        @Override
        public Integer getCode() {
            return this.code;
        }
    }

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(length = 100,name = "symptoms")
    private String symptoms;
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = BaseEnumToNumberSerializer.class)
    private State state;
    @Column(name = "patient_id")
    private Long patientId;
    @Column(name = "test_id")
    private Long testId;

    public Long getTemperature() {
        return temperature;
    }

    public void setTemperature(Long temperature) {
        this.temperature = temperature;
    }

    @Column(name = "temperature")
    private Long temperature;
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

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
