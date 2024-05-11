package com.alibou.security.controller;

import com.alibou.security.dtos.FeedbackRequestDto;
import com.alibou.security.dtos.FeedbackReturnDto;
import com.alibou.security.service.FeedbackService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
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
    public ResponseEntity<FeedbackReturnDto> save(@RequestBody @Valid FeedbackRequestDto dto,
                                                  Principal connectedUser) {
        FeedbackReturnDto feedback = feedbackService.save(dto, connectedUser.getName());
        return new ResponseEntity<>(feedback, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FeedbackReturnDto>> findAllFeedbacks() {
        List<FeedbackReturnDto> feedbacks = feedbackService.findAll();
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackReturnDto> findFeedbackById(@PathVariable @NotNull Integer id) {
        FeedbackReturnDto feedback = feedbackService.findById(id);
        return ResponseEntity.ok(feedback);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<FeedbackReturnDto>> findAllFeedbacksPaged(@ParameterObject @Valid Pageable pageable) {
        Page<FeedbackReturnDto> feedbacks = feedbackService.findAllPaged(pageable);
        return ResponseEntity.ok(feedbacks);
    }

    @PutMapping
    public ResponseEntity<FeedbackReturnDto> update(@RequestBody @Valid FeedbackRequestDto dto,
                                                    Principal connectedUser) {
        FeedbackReturnDto feedback = feedbackService.update(dto, connectedUser.getName());
        return ResponseEntity.ok(feedback);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFeedback(@PathVariable @NotNull Integer id) {
        feedbackService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
