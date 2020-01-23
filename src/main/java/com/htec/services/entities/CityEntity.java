package com.htec.services.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Nikola Matkovic
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table
public class CityEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String description;

	@OneToMany(mappedBy = "cityEntity")
	private Set<CommentEntity> commentEntities;

	@ManyToOne
	private CountryEntity countryEntity;
}
