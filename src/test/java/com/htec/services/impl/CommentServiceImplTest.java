package com.htec.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.htec.services.dto.Comment;
import com.htec.services.entities.CityEntity;
import com.htec.services.entities.CommentEntity;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Nikola Matkovic
 */
@SpringBootTest
class CommentServiceImplTest {

	@Mock
	private CommentRepository commentRepository;

	@Mock
	private CityRepository cityRepository;

	@InjectMocks
	private CommentServiceImpl commentService;

	@Test
	void findAllComments() {

		var commentEntity = mock(CommentEntity.class);
		var cityEntity = mock(CityEntity.class);

		when(commentRepository.findAll()).thenReturn(Collections.singletonList(commentEntity));
		when(commentEntity.getId()).thenReturn(1L);
		when(commentEntity.getText()).thenReturn("test text");
		when(commentEntity.getCityEntity()).thenReturn(cityEntity);
		when(cityEntity.getId()).thenReturn(2L);

		var result = commentService.findAllComments();

		assertNotEquals(true, result.isEmpty());
	}

	@Test
	void addComment() {
		var comment = Comment.builder().text("some text").cityId(1L).build();
		var cityEntity = mock(CityEntity.class);

		when(cityRepository.findById(anyLong())).thenReturn(Optional.of(cityEntity));

		commentService.addComment(comment);

		verify(commentRepository).save(any());
	}

	@Test
	void updateComment() {
		var comment = Comment.builder().text("some text").id(1L).build();
		var commentEntity = mock(CommentEntity.class);
		var commentEntityBuilder = CommentEntity.builder();

		when(commentRepository.findById(anyLong())).thenReturn(Optional.of(commentEntity));
		when(commentEntity.toBuilder()).thenReturn(commentEntityBuilder);

		commentService.updateComment(comment);

		verify(commentRepository).save(any());
	}

	@Test
	void deleteComment() {

		commentService.deleteComment(1L);

		verify(commentRepository).deleteById(any());
	}
}