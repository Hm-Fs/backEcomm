package com.BackEcom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="OrderItem")
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderItem_id;

	@Column(name="quantity")
	private int quantity;
	
	@Column(name="price")
	private Double price;

	@ManyToOne
	@JoinColumn(name="order_id")
	@JsonIgnore
	private Order order;

	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;

}
