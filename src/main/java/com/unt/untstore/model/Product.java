package com.unt.untstore.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "UNT_PRODUCT")
@Getter
@Setter
@Builder(toBuilder=true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product extends BaseModel {

	
	@Column(name = "PRODUCT_CT")
	@Enumerated(EnumType.STRING)
	private ProductCategory productCategory;

	@Column(name = "CODE")
	private String code;

	@Column(name ="NAME")
	private String name;

	@Column(name = "DESC")
	private String description;

	@Column(name = "PRICE")
	private String price;

	@Column(name = "PICTURES")
	private String pictures;

	@ManyToOne
	@JoinColumn(name = "CREATED_BY")
	private User createdBy;

	@Column(name = "UPDATED_ON")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;

	@Column(name ="IS_DELETED")
	private Boolean isDeleted;

}
