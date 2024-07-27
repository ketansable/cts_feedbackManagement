package com.feedbackmanagement.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feedbackmanagement.entity.Feedback;
import com.feedbackmanagement.repository.FeedbackRepository;

@Service
public class FeedbackAnalysisServiceImpl implements FeedbackAnalysisService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public Map<String, Object> analyzeFeedback() {
        List<Feedback> feedbackList = feedbackRepository.findAll();
        
        if (feedbackList.isEmpty()) {
            return Collections.emptyMap();
        }

        int positiveCount = 0;
        int negativeCount = 0;
        int neutralCount = 0;
        Set<String> improvementAreas = new HashSet<>();
        
        for (Feedback feedback : feedbackList) {
            String text = feedback.getFeedback().toLowerCase();
            
            if (text.contains("good") || text.contains("great") || text.contains("excellent")) {
                positiveCount++;
            } else if (text.contains("bad") || text.contains("poor") || text.contains("terrible")) {
                negativeCount++;
            } else {
                neutralCount++;
            }
            
            if (text.contains("user interface") || text.contains("ui")) {
                improvementAreas.add("User Interface");
            }
            if (text.contains("customer support") || text.contains("support")) {
                improvementAreas.add("Customer Support");
            }
            if (text.contains("checkout")) {
                improvementAreas.add("Checkout Process");
            }
        }

        String trend = "Neutral";
        if (positiveCount > negativeCount && positiveCount > neutralCount) {
            trend = "Positive";
        } else if (negativeCount > positiveCount && negativeCount > neutralCount) {
            trend = "Negative";
        }

        String sentiment = "Mixed";
        if (positiveCount > (negativeCount + neutralCount)) {
            sentiment = "Good";
        } else if (negativeCount > (positiveCount + neutralCount)) {
            sentiment = "Bad";
        }

        Map<String, Object> analysis = new HashMap<>();
        analysis.put("trends", trend);
        analysis.put("sentiment", sentiment);
        analysis.put("improvementAreas", improvementAreas);

        return analysis;
    }
}

