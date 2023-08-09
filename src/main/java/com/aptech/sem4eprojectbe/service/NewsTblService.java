package com.aptech.sem4eprojectbe.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.aptech.sem4eprojectbe.common.model.IdModel;
import com.aptech.sem4eprojectbe.entity.NewsTblEntity;
import com.aptech.sem4eprojectbe.repository.NewsTblRepository;

@Service
public class NewsTblService {
    @Autowired
    private NewsTblRepository newsTblRepository;

    @CacheEvict(value = "all-news", allEntries = true)
    public NewsTblEntity insertNew(NewsTblEntity news) {
        news.setCreateat(LocalDateTime.now());
        return newsTblRepository.save(news);
    }

    @Cacheable("all-news")
    public List<NewsTblEntity> getAllNews() {
        System.out.println(">>> Call database all news");
        return newsTblRepository.findByDeletedIsFalse();
    }

    @Cacheable(value = "news", key = "#idModel.getId()")
    public Optional<NewsTblEntity> getNewById(IdModel idModel) {
        System.out.println(">>> Call database new id " + idModel.getId());
        return newsTblRepository.findById(idModel.getId());
    }

    @Caching(evict = { @CacheEvict(value = "all-news", allEntries = true) }, put = {
            @CachePut(value = "news", key = "#news.getId()") })
    public NewsTblEntity updateNew(NewsTblEntity news) {
        return newsTblRepository.findById(news.getId())
                .map(newItem -> {
                    if (!news.getImage().isEmpty()) {
                        newItem.setImage(news.getImage());
                    }
                    newItem.setContent(news.getContent());
                    newItem.setTitle(news.getTitle());
                    newItem.setDeleted(news.getDeleted());
                    return newsTblRepository.save(newItem);
                })
                .orElseGet(() -> {
                    NewsTblEntity newNews = new NewsTblEntity();
                    newNews.setCreateat(news.getCreateat());
                    newNews.setImage(news.getImage());
                    newNews.setContent(news.getContent());
                    newNews.setTitle(news.getTitle());
                    newNews.setDeleted(false);
                    return newsTblRepository.save(newNews);
                });
    }

}
