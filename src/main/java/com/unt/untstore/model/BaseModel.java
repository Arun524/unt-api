package com.unt.untstore.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@MappedSuperclass
@Setter
@Getter
public class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "UID")
    private String uid;

    @PrePersist
    void setDefaultUID() {
        this.uid = UUID.randomUUID().toString();
    }

}
