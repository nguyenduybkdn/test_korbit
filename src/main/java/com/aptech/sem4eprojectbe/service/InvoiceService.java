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
import com.aptech.sem4eprojectbe.entity.InvoiceEntity;
import com.aptech.sem4eprojectbe.repository.InvoiceRepository;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @CacheEvict(value = "invoices", allEntries = true)
    public InvoiceEntity insertInvoice(InvoiceEntity invoice) {
        System.out.println("Caching insert invoice ( delete cache : invoices)");
        invoice.setCreateat(LocalDateTime.now());
        return invoiceRepository.save(invoice);
    }

    @Cacheable(value = "invoices")
    public List<InvoiceEntity> getAllInvoice() {
        System.out.println("Caching get invoices ( create cache : invoices)");
        return invoiceRepository.findByDeletedIsFalse();
    }

    @Cacheable(value = "invoice", key = "#idModel.getId()")
    public Optional<InvoiceEntity> getInvoiceById(IdModel idModel) {
        System.out.println("Caching get invoice ( create cache : invoice and id saved)");
        return invoiceRepository.findById(idModel.getId());
    }

    @Caching(evict = { @CacheEvict(value = "invoices", allEntries = true) }, put = {
            @CachePut(value = "invoice", key = "#invoice.getId()") })
    public InvoiceEntity deleteInvoice(InvoiceEntity invoice) {
        System.out
                .println("Caching update invoice ( delete cache : products and put and update cache invoice with id)");
        return invoiceRepository.findById(invoice.getId())
                .map(invoiceItem -> {
                    invoiceItem.setDeleted(invoice.getDeleted());
                    return invoiceRepository.save(invoiceItem);
                })
                .orElseGet(() -> {
                    return null;
                });
    }

    @Caching(evict = { @CacheEvict(value = "invoices", allEntries = true) }, put = {
            @CachePut(value = "invoice", key = "#invoice.getId()") })
    public InvoiceEntity updateInvoiceItem(InvoiceEntity invoice) {
        return invoiceRepository.findById(invoice.getId())
                .map(invoiceItem -> {
                    invoiceItem.setCreateat(invoice.getCreateat());
                    invoiceItem.setDeleted(invoice.getDeleted());
                    invoiceItem.setStatus(invoice.getStatus());
                    invoiceItem.setTotalprice(invoice.getTotalprice());
                    invoiceItem.setUserid(invoice.getUserid());

                    return invoiceRepository.save(invoiceItem);
                })
                .orElseGet(() -> {
                    return null;
                });
    }

    public Optional<List<InvoiceEntity>> getInvoiceByUserid(IdModel idModel) {
        return invoiceRepository.findByUserid(idModel.getId());
    }
}
