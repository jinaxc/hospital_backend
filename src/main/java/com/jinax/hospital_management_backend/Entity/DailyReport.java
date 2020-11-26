package com.jinax.hospital_management_backend.Entity;

import javax.persistence.*;

/**
 * @author : chara
 */
@Entity
@Table(name = "daily_report")
public class DailyReport {
    public static enum State{
        IN,
        OUT,
        DIE;

        public static State getState(int index){
            switch (index){
                case 0 :return IN;
                case 1 : return OUT;
                case 2:return DIE;
            }
            return null;
        }
    }

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(length = 100,name = "symptoms")
    private String symptoms;
    @Column(name = "state")
    private State state;
    @Column(name = "patient_id")
    private Long patientId;
    @Column(name = "test_id")
    private Long testId;

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
}
