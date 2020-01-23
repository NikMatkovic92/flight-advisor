package com.htec.web;

import com.htec.services.CommentService;
import com.htec.services.dto.Comment;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

/**
 * @author Nikola Matkovic
 */
@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {

	private final CommentService commentService;

	@GetMapping
	@PreAuthorize("hasRole('USER')")
	public List<Comment> getAllComments() {
		return commentService.findAllComments();
	}

	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public void addComment(@Valid  @RequestBody Comment comment) {
		commentService.addComment(comment);
	}

	@PutMapping
	@PreAuthorize("hasRole('USER')")
	public void updateComment(@Valid  @RequestBody Comment comment) {
		commentService.updateComment(comment);
	}

	@DeleteMapping("/{commentId}")
	@PreAuthorize("hasRole('USER')")
	public void deleteComment(@PathVariable Long commentId) {
		commentService.deleteComment(commentId);
	}

}
