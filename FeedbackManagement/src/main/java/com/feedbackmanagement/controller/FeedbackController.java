package com.feedbackmanagement.controller;

import com.feedbackmanagement.entity.Feedback;
import com.feedbackmanagement.service.FeedbackAnalysisService;
import com.feedbackmanagement.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;
    
    @Autowired
    private FeedbackAnalysisService feedbackAnalysisService;

    @PostMapping
    public ResponseEntity<?> submitFeedback(@RequestBody Feedback feedback) {
        Feedback savedFeedback = feedbackService.submitFeedback(feedback);
        return ResponseEntity.status(201).body(Map.of("feedbackId", savedFeedback.getId()));
    }

    @GetMapping
    public ResponseEntity<?> getAllFeedback() {
        List<Feedback> feedbackList = feedbackService.getAllFeedback();
        if (feedbackList.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "No feedback found"));
        }
        return ResponseEntity.ok(feedbackList);
    }
    
    @GetMapping("/analysis")
    public ResponseEntity<?> analyzeFeedback() {
        Map<String, Object> analysis = feedbackAnalysisService.analyzeFeedback();
        if (analysis.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "No feedback available for analysis"));
        }
        return ResponseEntity.ok(analysis);
    }
}

