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
import com.aptech.sem4eprojectbe.entity.FaqEntity;
import com.aptech.sem4eprojectbe.repository.FaqRepository;

@Service
public class FaqService {
    @Autowired
    private FaqRepository faqRepository;

    @CacheEvict(value = "faqs", allEntries = true)
    public FaqEntity insertFaq(FaqEntity faq) {
        return faqRepository.save(faq);
    }

    @Cacheable("faqs")
    public List<FaqEntity> getAllFaqs() {
        return faqRepository.findByDeletedIsFalse();
    }

    @Cacheable(value = "faq", key = "#idModel.getId()")
    public Optional<FaqEntity> getFaqById(IdModel idModel) {
        return faqRepository.findById(idModel.getId());
    }

    @Caching(evict = { @CacheEvict(value = "faqs", allEntries = true) }, put = {
            @CachePut(value = "faq", key = "#faq.getId()") })
    public FaqEntity updateFaq(FaqEntity faq) {
        return faqRepository.findById(faq.getId())
                .map(faqItem -> {
                    faqItem.setQuestion(faq.getQuestion());
                    faqItem.setAnswer(faq.getAnswer());
                    faqItem.setDeleted(faq.getDeleted());
                    return faqRepository.save(faqItem);
                })
                .orElseGet(() -> {
                    FaqEntity newFaq = new FaqEntity();
                    newFaq.setQuestion(faq.getQuestion());
                    newFaq.setAnswer(faq.getAnswer());
                    newFaq.setDeleted(false);
                    return faqRepository.save(newFaq);
                });
    }

}
