package com.feedbackmanagement.service;

import com.feedbackmanagement.entity.Feedback;
import java.util.List;

public interface FeedbackService {
    Feedback submitFeedback(Feedback feedback);
    List<Feedback> getAllFeedback();
}
