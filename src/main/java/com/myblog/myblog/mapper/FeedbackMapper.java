package com.myblog.myblog.mapper;

import com.myblog.myblog.entity.Feedback;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FeedbackMapper {
    /* Create */
    @Insert("INSERT INTO feedback (feedbackContent, email, feedbackDate) " +
            "VALUES (#{feedbackContent}, #{email}, #{feedbackDate})")
    int saveFeedback(Feedback feedback);

    /* Read */
    @Select("SELECT * FROM feedback")
    @Results({
            @Result(id = true, property = "feedbackId", column = "feedbackId"),
            @Result(property = "feedbackContent", column = "feedbackContent"),
            @Result(property = "email", column = "email"),
            @Result(property = "feedbackDate", column = "feedbackDate")
    })
    List<Feedback> getAllFeedback();

    /* Update */
    /* Sending feedback doesn't require registration. Once feedback is sent, there's no way to update it */

    /* Delete */
    @Delete("DELETE FROM feedback " +
            "WHERE feedbackId = #{feedbackId}")
    int deleteFeedback(Feedback feedback);

    @Delete("DELETE FROM feedback " +
            "WHERE feedbackId = #{feedbackId}")
    int deleteFeedbackById(int feedbackId);

    @Delete("DELETE FROM feedback " +
            "WHERE email = #{email}")
    int deleteFeedbackByEmail(String email);

    @Delete("DELETE FROM feedback")
    int deleteAllFeedback();
}
