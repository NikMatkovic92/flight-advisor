package com.htec.services.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
@Builder(toBuilder = true)
@Getter
@Table
public class CommentEntity {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String text;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column
	private LocalDateTime modifiedAt;

	@ManyToOne
	private CityEntity cityEntity;
}
