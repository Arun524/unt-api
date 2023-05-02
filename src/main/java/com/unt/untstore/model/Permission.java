package com.unt.untstore.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PERMISSION")
public class Permission extends BaseModel{
	
	@Column(name = "NAME")
	@Getter
	@Setter
	private String name;

}
