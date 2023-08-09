package com.aptech.sem4eprojectbe.controller;

import java.util.List;
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
import com.aptech.sem4eprojectbe.entity.InvoiceEntity;
import com.aptech.sem4eprojectbe.service.InvoiceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/insert-invoice")
    public ResponseModel insertInvoice(@Valid @RequestBody InvoiceEntity invoice) {
        return new ResponseModel("ok", "success", invoiceService.insertInvoice(invoice));
    }

    @GetMapping("/invoices")
    public ResponseModel getAllInvoices() {
        return new ResponseModel("ok", "success", invoiceService.getAllInvoice());
    }

    @GetMapping("/invoice")
    public ResponseModel getInvoiceById(@Valid @RequestBody IdModel idModel) {
        Optional<InvoiceEntity> invoice = invoiceService.getInvoiceById(idModel);
        if (invoice.isPresent() && !invoice.get().getDeleted()) {
            return new ResponseModel("ok", "success", invoice.get());
        } else {
            return new ResponseModel("fail", "Can not find id: " + idModel.getId(), null);
        }
    }

    @DeleteMapping("/delete-invoice")
    public ResponseModel deleteInvoice(@Valid @RequestBody IdModel idModel) {
        Optional<InvoiceEntity> invoice = invoiceService.getInvoiceById(idModel);
        if (invoice.isPresent()) {
            InvoiceEntity invoiceDelete = invoice.get();
            invoiceDelete.setDeleted(true);
            invoiceService.deleteInvoice(invoiceDelete);
            return new ResponseModel("ok", "success", null);
        } else {
            return new ResponseModel("fail", "Can not id: " + idModel.getId(), null);
        }
    }

    @PostMapping("/invoice-by-user-id")
    public ResponseModel getInvoiceByUserid(@Valid @RequestBody IdModel idModel) {
        List<InvoiceEntity> invoices = invoiceService.getInvoiceByUserid(idModel).get();
        return new ResponseModel("ok", "success", invoices);
    }

    @PutMapping("/update-invoice")
    public ResponseModel updateInvoiceStatus(@Valid @RequestBody InvoiceEntity invoiceEntity) {
        return new ResponseModel("ok", "success", invoiceService.updateInvoiceItem(invoiceEntity));

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseModel handleValidationException(MethodArgumentNotValidException exception) {
        return new ResponseModel("fail", "Validation Error", exception.getMessage());
    }
}
