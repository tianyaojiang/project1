package com.langlang.health.news.mapper;

import com.langlang.health.news.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by tyj on 2019/01/08.
 */
@Mapper
public interface ArticleMapper {

    long countByExample(Article article);

    int deleteByExample(Article article);

    int deleteByPrimaryKey(Integer cid);

    int insert(Article record);

    int insertSelective(Article record);

    List<Article> getArticleList();

    Article getArticlesById(Integer id);

    List<Article> selectByExample(Article article);

    Article selectByPrimaryKey(Integer cid);

    int updateByExampleSelective(@Param("record") Article record, @Param("article") Article article);

    int updateByExampleWithBLOBs(@Param("record") Article record, @Param("article") Article article);

    int updateByExample(@Param("record") Article record, @Param("article") Article article);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);

    List<Article> findReturnArchiveBo();

    List<Article> findByCatalog(Integer mid);


}
