package com.joaquimley.buseta.repository.local.model;

import java.util.Date;

public interface CommentModel extends IdentifiableModel {
	long getParentId();

	String getText();

	Date getPostedAt();
}
