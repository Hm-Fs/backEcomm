package com.BackEcom.model;
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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Address")
public class Address {


	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	@Column(name="street")
	private String street;
	@Column(name="city")
	private String city;
	@Column(name="state")
	private String state;
	@Column(name="postalCode")
	private String postalCode;
	@Column(name="country")
	private String country;

	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

}
