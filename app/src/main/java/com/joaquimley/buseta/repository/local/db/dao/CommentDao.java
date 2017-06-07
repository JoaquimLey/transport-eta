package com.joaquimley.buseta.repository.local.db.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.joaquimley.buseta.repository.local.db.entity.CommentEntity;

import java.util.List;

/**
 * Created by joaquimley on 22/05/2017.
 */

@Dao
public interface CommentDao {

	@Query("SELECT * FROM comments where parent_id = :parentId")
	LiveData<List<CommentEntity>> getCommentsByParentId(long parentId);

	@Query("SELECT * FROM comments where id = :commentId")
	LiveData<List<CommentEntity>> loadById(int commentId);

	@Query("SELECT * FROM comments where id = :commentId")
	List<CommentEntity> loadByIdSync(int commentId);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(CommentEntity... commentEntities);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insertAll(List<CommentEntity> stationEntityList);

	@Update
	void update(CommentEntity... commentEntities);

	@Update
	void updateAll(List<CommentEntity> commentEntityList);

	@Delete
	void delete(CommentEntity... commentEntities);

	@Delete
	void deleteAll(List<CommentEntity> commentEntities);

	@Query("DELETE FROM comments")
	void deleteAll();
}
