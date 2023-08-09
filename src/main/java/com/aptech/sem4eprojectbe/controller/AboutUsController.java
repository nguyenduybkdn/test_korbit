package com.aptech.sem4eprojectbe.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aptech.sem4eprojectbe.common.model.IdModel;
import com.aptech.sem4eprojectbe.common.model.ResponseModel;
import com.aptech.sem4eprojectbe.entity.AboutUsEntity;
import com.aptech.sem4eprojectbe.service.AboutUsService;
@RestController
@RequestMapping("/api/v1")
public class AboutUsController {
    @Autowired
    private AboutUsService aboutUsService;

    @PostMapping("/insert-about-us")
    public ResponseModel insertAboutUs(@RequestBody AboutUsEntity aboutUs){
        return new ResponseModel("ok", "success", aboutUsService.insertAboutUs(aboutUs));
    }

    @GetMapping("/all-about-us")
    public ResponseModel getAllAboutUs(){
        return new ResponseModel("ok", "success", aboutUsService.getAllAboutUs());
    }

    @GetMapping("/about-us")
    public ResponseModel getAboutUsById(@RequestBody IdModel idModel){
        Optional<AboutUsEntity> aboutUs = aboutUsService.getAboutUsById(idModel);
        if(aboutUs.isPresent() && !aboutUs.get().getDeleted()){
            return new ResponseModel("ok", "success", aboutUs.get());
        } else {
            return new ResponseModel("fail", "Can not find id " + idModel.getId(), null);
        }
    }

    @PutMapping("/update-about-us")
    public ResponseModel updateAboutUs(@RequestBody AboutUsEntity aboutUs){
        return new ResponseModel("ok", "success", aboutUsService.updateAboutUs(aboutUs));
    }

    @DeleteMapping("/delete-about-us")
    public ResponseModel deleteAboutUsById(@RequestBody IdModel idModel){
        Optional<AboutUsEntity> aboutUs = aboutUsService.getAboutUsById(idModel);
        if(aboutUs.isPresent()){
            AboutUsEntity deleteAboutUs = aboutUs.get();
            deleteAboutUs.setDeleted(true);
            aboutUsService.updateAboutUs(deleteAboutUs);
            return new ResponseModel("ok", "success", null);
        } else {
            return new ResponseModel("fail", "Can not find " + idModel.getId(), null);
        }

    }
}
