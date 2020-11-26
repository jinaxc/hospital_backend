package com.jinax.hospital_management_backend.Entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * @author : chara
 */
@Entity
@Table(name = "test")
public class Test {
    public static enum Result{
        NEGATIVE,
        POSITIVE

    }

    public static enum Level{
        MINOR,
        MAJOR,
        DANGER;
        public static Level getLevel(int i){
            switch (i){
                case 1 :return MINOR;
                case 2 : return MAJOR;
                case 3 : return DANGER;
            }
            return null;
        }
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "result")
    @Enumerated(EnumType.STRING)
    private Result result;

    @Column(name = "test_time")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date testTime;
    @Column(name = "level")
    @Enumerated(EnumType.STRING)
    private Level level;

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
}
