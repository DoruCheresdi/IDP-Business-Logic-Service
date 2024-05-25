package com.alibou.security.controller;

import com.alibou.security.dtos.FeedbackRequestDto;
import com.alibou.security.dtos.FeedbackReturnDto;
import com.alibou.security.entities.Feedback;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<FeedbackReturnDto> save(@RequestBody @Valid FeedbackRequestDto dto,
                                                  Principal connectedUser) {
        Feedback feedback = feedbackService.save(dto, connectedUser.getName());
        return new ResponseEntity<>(new FeedbackReturnDto(feedback), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FeedbackReturnDto>> findAllFeedbacks() {
        List<Feedback> feedbacks = feedbackService.findAll();
        List<FeedbackReturnDto> dtos = feedbacks.stream()
                .map(FeedbackReturnDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackReturnDto> findFeedbackById(@PathVariable @NotNull Integer id) {
        Feedback feedback = feedbackService.findById(id);
        return ResponseEntity.ok(new FeedbackReturnDto(feedback));
    }

    @GetMapping("/last")
    public ResponseEntity<FeedbackReturnDto> findLastFeedback(Principal connectedUser) {
        Feedback feedback = feedbackService.findLastUserFeedback(connectedUser.getName());
        return ResponseEntity.ok(new FeedbackReturnDto(feedback));
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<FeedbackReturnDto>> findAllFeedbacksPaged(@ParameterObject @Valid Pageable pageable) {
        Page<Feedback> feedbacks = feedbackService.findAllPaged(pageable);
        Page<FeedbackReturnDto> dtos = feedbacks.map(FeedbackReturnDto::new);
        return ResponseEntity.ok(dtos);
    }

    @PutMapping
    public ResponseEntity<FeedbackReturnDto> update(@RequestBody @Valid FeedbackRequestDto dto,
                                                    Principal connectedUser) {
        Feedback feedback = feedbackService.update(dto, connectedUser.getName());
        return ResponseEntity.ok(new FeedbackReturnDto(feedback));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFeedback(@PathVariable @NotNull Integer id) {
        feedbackService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
