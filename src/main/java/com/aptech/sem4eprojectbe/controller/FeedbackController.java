package com.aptech.sem4eprojectbe.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aptech.sem4eprojectbe.common.model.IdModel;
import com.aptech.sem4eprojectbe.common.model.ResponseModel;
import com.aptech.sem4eprojectbe.entity.FeedbackEntity;
import com.aptech.sem4eprojectbe.service.FeedbackService;
import com.stripe.model.PaymentLink.CustomField.Dropdown.Option;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/v1")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/insert-feedback")
    public ResponseModel insertFeedback(@Valid   @RequestBody FeedbackEntity feedback) {
        return new ResponseModel("ok", "success", feedbackService.insertFeedback(feedback));
    }

    @GetMapping("/feedbacks")
    public ResponseModel getAllFeedbacks() {
        return new ResponseModel("ok", "success", feedbackService.getAllFeedbacks());
    }

    @GetMapping("/feedback")
    public ResponseModel getFeedbackById(@Valid @RequestBody IdModel idModel) {
        Optional<FeedbackEntity> feedback = feedbackService.getFeedbackById(idModel);
        if (feedback.isPresent() && !feedback.get().getDeleted()) {
            return new ResponseModel("ok", "success", feedback.get());
        } else {
            return new ResponseModel("fail", "Cannot find feedback id: " + idModel.getId(), null);
        }
    }

    @PutMapping("/update-feedback")
    public ResponseModel updateFaq(@Valid @RequestBody FeedbackEntity feedback) {
        return new ResponseModel("ok", "success", feedbackService.updateFeedback(feedback));
    }

    @DeleteMapping("/delete-feedback")
    public ResponseModel removeFeedbackById(@Valid @RequestBody IdModel idModel) {
        Optional<FeedbackEntity> feedback = feedbackService.getFeedbackById(idModel);
        if (feedback.isPresent()) {
            FeedbackEntity deletedFeedback = feedback.get();
            deletedFeedback.setDeleted(true);
            feedbackService.updateFeedback(deletedFeedback);
            return new ResponseModel("ok", "success", null);
        } else {
            return new ResponseModel("fail", "Cannot find feedback id: " + idModel.getId(), null);
        }
    }

    @PostMapping("/feedbacks/by-product-id")
    public ResponseModel findByProductId(@Valid @RequestBody IdModel idModel) {
        Optional<List<FeedbackEntity>> feedbacks = feedbackService.findByProductId(idModel);
        if (feedbacks.isPresent()) {
            return new ResponseModel("ok", "success", feedbacks);
        } else {
            return new ResponseModel("fail", "Cannot find feedbacks of product id: " + idModel.getId(), null);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseModel handleValidationException(MethodArgumentNotValidException exception) {
        return new ResponseModel("fail", "Validation Error", exception.getMessage());
    }
}
