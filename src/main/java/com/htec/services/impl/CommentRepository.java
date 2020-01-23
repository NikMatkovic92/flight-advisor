package com.htec.services.impl;

import com.htec.services.entities.CommentEntity;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nikola Matkovic
 */
interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
