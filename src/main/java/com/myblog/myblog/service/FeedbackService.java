package com.myblog.myblog.service;

import com.myblog.myblog.constant.Status;
import com.myblog.myblog.entity.Feedback;
import com.myblog.myblog.mapper.FeedbackMapper;
import com.myblog.myblog.response.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackMapper feedbackMapper;

    private final Logger logger = LoggerFactory.getLogger(FeedbackService.class);

    public JsonResponse sendFeedback(String email, String content) {
        if (!email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
            return new JsonResponse(Status.INVALID_FORMAT);
        }
        else {
            Feedback feedback = new Feedback();
            feedback.setEmail(email);
            feedback.setFeedbackContent(content);
            feedback.setFeedbackDate(LocalDate.now());
            try {
                feedbackMapper.saveFeedback(feedback);
                logger.info("Get new feedback");
                return new JsonResponse(Status.SUCCESS);
            } catch (Exception e) {
                logger.error(e.getMessage());
                return new JsonResponse(Status.SERVER_ERROR);
            }
        }
    }

    public JsonResponse getFeedbacks() {
        List<Feedback> feedbacks = feedbackMapper.getAllFeedback();
        return new JsonResponse(Status.SUCCESS, feedbacks);
    }

    public JsonResponse deleteFeedbackById(Integer feedbackId) {
        try {
            feedbackMapper.deleteFeedbackById(feedbackId);
            logger.info("Deleted a feedback: {}", feedbackId);
            return new JsonResponse(Status.SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new JsonResponse(Status.SERVER_ERROR);
        }
    }

    public JsonResponse deleteAllFeedback() {
        feedbackMapper.deleteAllFeedback();
        logger.info("All feedbacks are deleted");
        return new JsonResponse(Status.SUCCESS);
    }

    public JsonResponse deleteFeedbackByEmail (String email) {
        try {
            feedbackMapper.deleteFeedbackByEmail(email);
            logger.info("Feedbacks from {} has been deleted", email);
            return new JsonResponse(Status.SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new JsonResponse(Status.SERVER_ERROR);
        }
    }
}
