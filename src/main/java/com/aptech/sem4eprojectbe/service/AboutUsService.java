package com.aptech.sem4eprojectbe.service;

 
import java.util.List;
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.sem4eprojectbe.common.model.IdModel;
import com.aptech.sem4eprojectbe.entity.AboutUsEntity;
import com.aptech.sem4eprojectbe.repository.AboutUsRepository;

@Service
public class AboutUsService {
    @Autowired
    private AboutUsRepository aboutUsRepository;

    public AboutUsEntity insertAboutUs(AboutUsEntity aboutUs) {
        return aboutUsRepository.save(aboutUs);
    }

    public List<AboutUsEntity> getAllAboutUs() {
        return aboutUsRepository.findByDeletedIsFalse();
    }

    public Optional<AboutUsEntity> getAboutUsById(IdModel idModel) {
        return aboutUsRepository.findById(idModel.getId());
    }

    public AboutUsEntity updateAboutUs(AboutUsEntity aboutUs) {
        return aboutUsRepository.findById(aboutUs.getId())
                .map(aboutUsItem -> {
                    aboutUsItem.setContent(aboutUs.getContent());
                    aboutUsItem.setDeleted(aboutUs.getDeleted());
                    return aboutUsRepository.save(aboutUs);
                })
                .orElseGet(() ->{
                    AboutUsEntity newAboutUs = new AboutUsEntity();
                    newAboutUs.setContent(aboutUs.getContent());
                    newAboutUs.setDeleted(false);
                    return aboutUsRepository.save(newAboutUs);
                });

    }

  

}
