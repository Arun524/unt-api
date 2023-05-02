package com.unt.untstore.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ROLE")
@Getter
@Setter
public class Role extends BaseModel {

    @Column(name = "NAME")
    private String name;
    
    @JoinTable(name = "ROLE_PERMISSION", joinColumns = {@JoinColumn(name = "ROLE_ID")}, inverseJoinColumns = {
            @JoinColumn(name = "PERMISSION_ID")})
    @ManyToMany
    private List<Permission> permissions;

}
