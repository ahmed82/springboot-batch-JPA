package com.rbs.licensemanagmentvendorbatch.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE )
	private Long id;
	private String firstName;
	private String lastName;
	private String birthdate;
}