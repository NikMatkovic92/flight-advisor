package com.htec.services.impl;

import com.htec.services.CommentService;
import com.htec.services.dto.Comment;
import com.htec.services.entities.CommentEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

/**
 * @author Nikola Matkovic
 */
@Service
@AllArgsConstructor
@Transactional
class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;
	private final CityRepository cityRepository;

	@Override
	public List<Comment> findAllComments() {
		return commentRepository.findAll().stream().map(
			commentEntity -> Comment.builder().id(commentEntity.getId()).cityId(commentEntity.getCityEntity().getId()).text(commentEntity.getText()).build())
			.collect(Collectors.toList());
	}

	@Override
	public void addComment(final Comment comment) {
		var cityEntity = cityRepository.findById(comment.getCityId());

		if (cityEntity.isEmpty()) {
			throw new BadRequestException("the supplied city does not exist");
		}

		commentRepository.save(CommentEntity.builder()
			.cityEntity(cityEntity.get())
			.createdAt(LocalDateTime.now())
			.text(comment.getText())
			.build());

	}

	@Override
	public void updateComment(final Comment comment) {
		final var commentEntity = commentRepository.findById(comment.getId());

		if (commentEntity.isEmpty()) {
			throw new ResourceNotFoundException("Comment not found");
		}

		commentRepository.save(commentEntity.get().toBuilder().text(comment.getText()).modifiedAt(LocalDateTime.now()).build());
	}

	@Override
	public void deleteComment(final Long id) {
		commentRepository.deleteById(id);
	}
}
