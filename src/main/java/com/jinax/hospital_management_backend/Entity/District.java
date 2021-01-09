package com.jinax.hospital_management_backend.Entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jinax.hospital_management_backend.Component.BaseEnumToNumberSerializer;

import javax.persistence.*;

/**
 * @author : chara
 */
@Entity
@Table(name="district")
public class District {

    public static enum Type implements BaseEnum {
        MINOR(1),
        MAJOR(2),
        DANGER(3),
        ISOLATION(4);

        private Integer code;
        Type(int i) {
            this.code = i;
        }

        @Override
        public Integer getCode() {
            return code;
        }
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = BaseEnumToNumberSerializer.class)
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
