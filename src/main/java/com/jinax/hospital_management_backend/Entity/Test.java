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
@Table(name = "test")
public class Test {
    public static enum Result implements BaseEnum{
        NEGATIVE(0),
        POSITIVE(1);

        private Integer code;
        Result(int i) {
            code = i;
        }

        @Override
        public Integer getCode() {
            return code;
        }
    }

    public static enum Level implements BaseEnum{
        MINOR(0),
        MAJOR(1),
        DANGER(2);

        private Integer code;
        Level(int i) {
            code = i;
        }

        public static Level getLevel(int level) {
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
    @Column(name = "result")
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = BaseEnumToNumberSerializer.class)
    private Result result;
    @Column(name = "patient_id")
    private long patientId;
    @Column(name = "test_time")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date testTime;
    @Column(name = "level")
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = BaseEnumToNumberSerializer.class)
    private Level level;

    public Test(Long id, Result result, Date testTime, Level level) {
        this.id = id;
        this.result = result;
        this.testTime = testTime;
        this.level = level;
    }

    public Test() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Date getTestTime() {
        return testTime;
    }

    public void setTestTime(Date testTime) {
        this.testTime = testTime;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }
}
