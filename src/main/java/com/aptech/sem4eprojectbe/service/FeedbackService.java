package com.aptech.sem4eprojectbe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.aptech.sem4eprojectbe.common.model.IdModel;
import com.aptech.sem4eprojectbe.entity.FeedbackEntity;
import com.aptech.sem4eprojectbe.repository.FeedbackRepository;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    @CacheEvict(value = "feedbacks", allEntries = true)
    public FeedbackEntity insertFeedback(FeedbackEntity feedback) {
        return feedbackRepository.save(feedback);
    }

    @Cacheable("feedbacks")
    public List<FeedbackEntity> getAllFeedbacks() {
        System.out.println(">>> Call database all feedbacks");
        return feedbackRepository.findByDeletedIsFalse();
    }

    @Cacheable(value = "feedback", key = "#idModel.getId()")
    public Optional<FeedbackEntity> getFeedbackById(IdModel idModel) {
        System.out.println(">>> Call database feedback id " + idModel.getId());
        return feedbackRepository.findById(idModel.getId());
    }

    @Caching(evict = { @CacheEvict(value = "feedbacks", allEntries = true) }, put = {
            @CachePut(value = "feedback", key = "#feedback.getId()") })
    public FeedbackEntity updateFeedback(FeedbackEntity feedback) {
        return feedbackRepository.findById(feedback.getId())
                .map(feedbackItem -> {
                    feedbackItem.setUserId(feedback.getUserId());
                    feedbackItem.setContent(feedback.getContent());
                    feedbackItem.setProductId(feedback.getProductId());
                    feedbackItem.setFirstName(feedback.getFirstName());
                    feedbackItem.setLastName(feedback.getLastName());
                    feedbackItem.setDeleted(feedback.getDeleted());
                    return feedbackRepository.save(feedbackItem);
                })
                .orElseGet(() -> {
                    FeedbackEntity newFeedback = new FeedbackEntity();
                    newFeedback.setUserId(feedback.getUserId());
                    newFeedback.setContent(feedback.getContent());
                    newFeedback.setCreateat(feedback.getCreateat());
                    newFeedback.setProductId(feedback.getProductId());
                    newFeedback.setFirstName(feedback.getFirstName());
                    newFeedback.setLastName(feedback.getLastName());
                    newFeedback.setDeleted(false);
                    return feedbackRepository.save(newFeedback);
                });
    }

    public Optional<List<FeedbackEntity>> findByProductId(IdModel id) {
        return feedbackRepository.findByProductIdAndDeletedIsFalse(id.getId());
    }

}
