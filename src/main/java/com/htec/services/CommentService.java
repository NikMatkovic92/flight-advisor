package com.htec.services;

import com.htec.services.dto.Comment;

import java.util.List;

/**
 * @author Nikola Matkovic
 */
public interface CommentService {

	/**
	 * Finds all comments added
	 *
	 * @return comments for cities
	 */
	List<Comment> findAllComments();

	/**
	 * Adds new comment
	 *
	 * @param comment to add
	 */
	void addComment(Comment comment);

	/**
	 * Updates the comment
	 *
	 * @param comment to update
	 */
	void updateComment(Comment comment);

	/**
	 * deletes specific comment
	 *
	 * @param id of the comment to be deleted
	 */
	void deleteComment(Long id);
}
