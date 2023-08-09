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
import com.aptech.sem4eprojectbe.entity.ContactEntity;

import com.aptech.sem4eprojectbe.service.ContactService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/insert-contact")
    public ResponseModel inserContact(@Valid @RequestBody ContactEntity contact) {
        return new ResponseModel("ok", "success", contactService.insertContact(contact));
    }

    @GetMapping("/contacts")
    public ResponseModel getAllContact() {
        return new ResponseModel("ok", "success", contactService.getAllContacts());
    }

    @GetMapping("/contact")
    public ResponseModel getContactById(@Valid @RequestBody IdModel idModel) {
        Optional<ContactEntity> contact = contactService.getContactById(idModel);
        if (contact.isPresent() && !contact.get().getDeleted()) {
            return new ResponseModel("ok", "success", contact.get());
        } else {
            return new ResponseModel("fail", "Can not find id " + idModel.getId(), null);
        }

    }

    @PutMapping("/update-contact")
    public ResponseModel updateContact(@Valid @RequestBody ContactEntity contact) {
        return new ResponseModel("ok", "success", contactService.updateContact(contact));
    }

    @DeleteMapping("/delete-contact")
    public ResponseModel deleteContactById(@Valid @RequestBody IdModel idModel) {
        Optional<ContactEntity> contact = contactService.getContactById(idModel);
        if (contact.isPresent()) {
            ContactEntity deleteContact = contact.get();
            deleteContact.setDeleted(true);
            contactService.updateContact(deleteContact);
            return new ResponseModel("ok ", "success", null);
        } else {
            return new ResponseModel("fail", "Can not find id " + idModel.getId(), null);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseModel handleValidationException(MethodArgumentNotValidException exception) {
        return new ResponseModel("fail", "Validation Error", exception.getMessage());
    }
}
