package com.BackEcom.model;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Category")
public class Category {


	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private long category_id;

	@NotEmpty(message = "Le nom est obligatoire")
	@Column(name= "name")
	private String name;

	@NotEmpty(message = "La description est obligatoire")
	@Column(name= "description")
	private String description;

	@OneToMany
	@JoinColumn(name ="product_id")
    private List<Product> products = new ArrayList<>();



}
