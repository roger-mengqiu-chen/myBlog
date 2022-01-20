package com.myblog.myblog.controller;

import com.myblog.myblog.request.FeedbackRequest;
import com.myblog.myblog.response.JsonResponse;
import com.myblog.myblog.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @PostMapping()
    public JsonResponse sendFeedback (@RequestBody FeedbackRequest feedbackRequest) {
        String email = feedbackRequest.getEmail();
        String content = feedbackRequest.getContent();

        return feedbackService.sendFeedback(email, content);
    }
}
