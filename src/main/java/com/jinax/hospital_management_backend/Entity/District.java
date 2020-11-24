package com.jinax.hospital_management_backend.Entity;

import javax.persistence.*;

/**
 * @author : chara
 */
@Entity
@Table(name="district")
public class District {

    public static enum Type{
        MINOR,
        MAJOR,
        DANGER,
        ISOLATION;
        public static int getNurseCareCount(Type type){
            switch (type){
                case MINOR:return 3;
                case MAJOR:return 2;
                case DANGER:return 1;
            }
            return 0;
        }
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;

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
}
