package com.peerapon.data.db.dao

import androidx.room.*
import com.peerapon.data.db.entity.ArticleInfoEntity

@Dao
interface ArticleListDao {
    @Query("SELECT * FROM articleinfoentity WHERE period = :period")
    fun getArticleListByPeriod(period: Int): List<ArticleInfoEntity>

    @Query("SELECT * FROM articleinfoentity WHERE id = :id LIMIT 1")
    fun getArticleDetail(id: String): ArticleInfoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<ArticleInfoEntity>)

    @Delete
    fun delete(items: ArticleInfoEntity)
}