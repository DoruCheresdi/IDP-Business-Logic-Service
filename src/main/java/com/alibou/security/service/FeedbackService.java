package com.alibou.security.service;

import com.alibou.security.dtos.FeedbackRequestDto;
import com.alibou.security.entities.Book;
import com.alibou.security.entities.Feedback;
import com.alibou.security.entities.User;
import com.alibou.security.repository.FeedbackRepository;
import com.alibou.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    private final UserRepository userRepository;

    public Feedback save(FeedbackRequestDto dto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find resource"));

        if (user.getFeedback() != null) {
            user.getFeedback().setComments(dto.getComments());
            user.getFeedback().setImprovementsCheckbox(dto.getImprovementsCheckbox());
            user.getFeedback().setExpectationRadioButton(dto.getExpectationRadioButton());
            user.getFeedback().setSatisfactionLevelSelect(dto.getSatisfactionLevelSelect());
            feedbackRepository.save(user.getFeedback());
            return user.getFeedback();
        }
        // new feedback:
        var feedback = Feedback.builder()
                .comments(dto.getComments())
                .improvementsCheckbox(dto.getImprovementsCheckbox())
                .expectationRadioButton(dto.getExpectationRadioButton())
                .satisfactionLevelSelect(dto.getSatisfactionLevelSelect())
                .user(user)
                .build();
        user.setFeedback(feedback);
        userRepository.save(user);
        return user.getFeedback();
    }

    public Feedback findById(Integer id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find resource"));
    }

    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }
}
