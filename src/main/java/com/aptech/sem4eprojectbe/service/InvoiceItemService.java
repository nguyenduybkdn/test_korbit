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
import com.aptech.sem4eprojectbe.entity.InvoiceItemEntity;
import com.aptech.sem4eprojectbe.repository.InvoiceItemRepository;

@Service
public class InvoiceItemService {

    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

    @CacheEvict(value = "invoiceItems", allEntries = true)
    public InvoiceItemEntity insertInvoiceItem(InvoiceItemEntity invoiceItem) {
        System.out.println("Caching insert invoiceItem ( delete cache : invoiceItems)");
        return invoiceItemRepository.save(invoiceItem);
    }

    @Cacheable(value = "invoiceItems")
    public List<InvoiceItemEntity> getAllInvoiceItem() {
        System.out.println("Caching get invoiceItems ( create cache : invoiceItems)");
        return invoiceItemRepository.findByDeletedIsFalse();
    }

    @Cacheable(value = "invoiceItem", key = "#idModel.getId()")
    public Optional<InvoiceItemEntity> getInvoiceItemById(IdModel idModel) {
        System.out.println("Caching get invoiceItem ( create cache : invoiceItem)");
        return invoiceItemRepository.findById(idModel.getId());
    }

    @Caching(evict = { @CacheEvict(value = "invoiceItems", allEntries = true) }, put = {
            @CachePut(value = "invoiceItem", key = "#invoiceItem.getId()") })
    public InvoiceItemEntity updateInvoiceItem(InvoiceItemEntity invoiceItem) {
        return invoiceItemRepository.findById(invoiceItem.getId())
                .map(newInvoiceItem -> {
                    newInvoiceItem.setQuantity(invoiceItem.getQuantity());
                    newInvoiceItem.setTotalprice(invoiceItem.getTotalprice());
                    newInvoiceItem.setDeleted(invoiceItem.getDeleted());
                    return invoiceItemRepository.save(newInvoiceItem);
                })
                .orElseGet(() -> {
                    return null;
                });
    }

    public Optional<List<InvoiceItemEntity>> getByInvoiceId(IdModel idModel) {
        return invoiceItemRepository.findByInvoiceid(idModel.getId());
    }
}
