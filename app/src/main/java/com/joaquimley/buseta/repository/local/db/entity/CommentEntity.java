package com.joaquimley.buseta.repository.local.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.TypeConverters;

import com.joaquimley.buseta.repository.local.db.converter.DateConverter;
import com.joaquimley.buseta.repository.local.model.CommentModel;

import java.util.Date;

/**
 * Created by joaquimley on 23/05/2017.
 */
@Entity(tableName = "comments", foreignKeys = {
		@ForeignKey(entity = BusEntity.class,
				parentColumns = "id",
				childColumns = "parent_id",
				onDelete = ForeignKey.CASCADE)}, indices = {
		@Index(value = "parent_id")
})
public class CommentEntity extends IdentifiableEntity implements CommentModel {

	@ColumnInfo(name = "parent_id")
	private long parentId;
	@ColumnInfo(name = "text")
	private String text;
	@ColumnInfo(name = "posted_at")
	@TypeConverters({DateConverter.class})
	private Date postedAt;

	public CommentEntity() {
	}

	public CommentEntity(CommentModel comment) {
		id = comment.getId();
		parentId = comment.getParentId();
		text = comment.getText();
		postedAt = comment.getPostedAt();
	}

	@Override
	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	@Override
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public Date getPostedAt() {
		return postedAt;
	}

	public void setPostedAt(Date postedAt) {
		this.postedAt = postedAt;
	}
}