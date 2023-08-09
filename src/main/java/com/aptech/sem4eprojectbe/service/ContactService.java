package com.aptech.sem4eprojectbe.service;

import java.util.List;
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.sem4eprojectbe.common.model.IdModel;
import com.aptech.sem4eprojectbe.entity.ContactEntity;
import com.aptech.sem4eprojectbe.repository.ContactRepository;

 

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    public ContactEntity insertContact(ContactEntity contact){
        return contactRepository.save(contact);
    }

    public List<ContactEntity> getAllContacts(){
        return contactRepository.findByDeletedIsFalse();
    }

    public Optional<ContactEntity> getContactById(IdModel idModel){
        return contactRepository.findById(idModel.getId());
    }

    public ContactEntity updateContact(ContactEntity contact){
        return contactRepository.findById(contact.getId())
        .map(contactItem -> {
            contactItem.setAddress(contact.getAddress());
            contactItem.setDistrict(contact.getDistrict());
            contactItem.setCity(contact.getCity());
            contactItem.setEmail(contact.getEmail());
            contactItem.setPhone(contact.getPhone());
            contactItem.setDeleted(contact.getDeleted());
            return contactRepository.save(contactItem);
        })
        .orElseGet(() -> {
            ContactEntity newContact = new ContactEntity();
            newContact.setAddress(contact.getAddress());
            newContact.setDistrict(contact.getDistrict());
            newContact.setCity(contact.getCity());
            newContact.setEmail(contact.getEmail());
            newContact.setPhone(contact.getPhone());
            newContact.setDeleted(false);
            return contactRepository.save(newContact);
        });
    }

  
}
