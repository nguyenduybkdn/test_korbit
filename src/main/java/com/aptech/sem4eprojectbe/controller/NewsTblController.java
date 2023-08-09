package com.aptech.sem4eprojectbe.controller;

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
import com.aptech.sem4eprojectbe.entity.NewsTblEntity;
import com.aptech.sem4eprojectbe.service.NewsTblService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class NewsTblController {
    @Autowired
    private NewsTblService newsTblService;

    @PostMapping("/insert-news")
    public ResponseModel insertNews(@Valid @RequestBody NewsTblEntity news) {
        return new ResponseModel("ok", "success", newsTblService.insertNew(news));
    }

    @GetMapping("/all-news")
    public ResponseModel getAllNews() {
        return new ResponseModel("ok", "success", newsTblService.getAllNews());
    }

    @PostMapping("/news")
    public ResponseModel getNewById(@Valid @RequestBody IdModel idModel) {
        Optional<NewsTblEntity> news = newsTblService.getNewById(idModel);
        if (news.isPresent() && !news.get().getDeleted()) {
            return new ResponseModel("ok", "success", news.get());
        } else {
            return new ResponseModel("fail", "Cannot find news id: " + idModel.getId(), null);
        }
    }

    @PutMapping("/update-news")
    public ResponseModel updateNew(@Valid @RequestBody NewsTblEntity news) {
        return new ResponseModel("ok", "success", newsTblService.updateNew(news));
    }

    @DeleteMapping("/delete-news")
    public ResponseModel removeNewById(@Valid @RequestBody IdModel idModel) {
        Optional<NewsTblEntity> news = newsTblService.getNewById(idModel);
        if (news.isPresent()) {
            NewsTblEntity deletedNew = news.get();
            deletedNew.setDeleted(true);
            newsTblService.updateNew(deletedNew);
            return new ResponseModel("ok", "success", null);
        } else {
            return new ResponseModel("fail", "Cannot find new id: " + idModel.getId(), null);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseModel handleValidationException(MethodArgumentNotValidException exception) {
        return new ResponseModel("fail", "Validation Error", exception.getMessage());
    }
}
