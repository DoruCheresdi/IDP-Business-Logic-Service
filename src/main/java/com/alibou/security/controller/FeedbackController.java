package com.alibou.security.controller;

import com.alibou.security.dtos.FeedbackRequestDto;
import com.alibou.security.entities.Feedback;
import com.alibou.security.service.FeedbackService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<Feedback> save(@RequestBody @Valid FeedbackRequestDto dto,
                                         Principal connectedUser) {
        Feedback feedback = feedbackService.save(dto, connectedUser.getName());
        return new ResponseEntity<>(feedback, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Feedback>> findAllFeedbacks() {
        return ResponseEntity.ok(feedbackService.findAll());
    }

    @GetMapping()
    public ResponseEntity<Feedback> findFeedbackById(@NotNull Integer id) {
        return ResponseEntity.ok(feedbackService.findById(id));
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<Feedback>> findAllFeedbacksPaged(@Valid Pageable pageable) {
        return ResponseEntity.ok(feedbackService.findAllPaged(pageable));
    }

    @PutMapping
    public ResponseEntity<Feedback> update(@RequestBody @Valid FeedbackRequestDto dto,
                                         Principal connectedUser) {
        Feedback feedback = feedbackService.update(dto, connectedUser.getName());
        return ResponseEntity.ok(feedback);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteFeedback(@NotNull Integer id) {
        feedbackService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
