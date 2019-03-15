package com.langlang.health.news.mapper;

import com.langlang.health.news.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by tyj on 2019/01/08.
 */
@Mapper
public interface CommentMapper {

    long countByExample(Comment comment);

    int deleteByExample(Comment comment);

    int deleteByPrimaryKey(Integer coid);

    int insert(Comment record);

    int insertSelective(Comment record);

    List<Comment> selectByExampleWithBLOBs(Comment comment);

    List<Comment> selectByExample(Comment example);

    List<Comment> getCommentsByArticleId(Integer cid);

    List<Comment> getCommentsByUserId(Integer userId);


    Comment selectByPrimaryKey(Integer coid);

    int updateByExampleSelective(@Param("record") Comment record, @Param("comment") Comment comment);

    int updateByExampleWithBLOBs(@Param("record") Comment record, @Param("comment") Comment comment);

    int updateByExample(@Param("record") Comment record, @Param("comment") Comment comment);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKeyWithBLOBs(Comment record);

    int updateByPrimaryKey(Comment record);
}
