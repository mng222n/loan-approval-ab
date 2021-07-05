package com.cdd.controller;

import com.cdd.dto.PageResult;
import com.cdd.model.CreditBundle;
import com.cdd.service.CreditBundleService;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/credit-bundle")
public class CreditBundleController {
    @Autowired
    private CreditBundleService creditBundleService;

    @PostMapping("/save")
    @ApiOperation(value = "Add new credit bundle", notes = "Add new credit bundle with an appropriate package Id")
    public CreditBundle saveCreditBundle(@RequestBody CreditBundle creditBundle, @RequestParam("packageId") UUID packageId) {
        return creditBundleService.saveCreditBundle(creditBundle, packageId);
    }

    @GetMapping("/")
    @ApiOperation(value = "Get credit bundle by pages", notes = "Get credit bundle by pages")
    public PageResult<CreditBundle> getCreditBundlePageable(Pageable pageable) {
        return creditBundleService.getCreditBundlePageable(pageable);
    }

    @GetMapping("/all")
    @ApiOperation(value = "GEt all credit bundles", notes = "Get all credit bundle")
    public List<CreditBundle> getAllCreditBundles() {
        return creditBundleService.getAllCreditBundles();
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "Update credit bundle", notes = "Update credit bundle with an appropriate packageId")
    public CreditBundle updateCreditBundle(@PathVariable("id") UUID id, @RequestBody CreditBundle creditBundle, @RequestParam("packageId") UUID packageId) {
        return creditBundleService.updateCreditBundle(id, creditBundle, packageId);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete credit bundle", notes = "Delete a credit bundle by Id")
    public void deleteCreditBundle(@PathVariable("id") UUID id) {
        creditBundleService.deleteCreditBundle(id);
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "Get credit bundle", notes = "GEt credit bundle by Id")
    public CreditBundle getCreditBundleById(@PathVariable("id") UUID id) {
        return creditBundleService.getCreditBundleById(id);
    }

    @GetMapping("/all-by-package")
    @ApiOperation(value = "GEt all credit bundles by active package", notes = "Get all credit bundle by active package")
    @Secured("ROLE_SERVICE")
    public List<CreditBundle> getAllCreditBundlesByPackage() {
        return creditBundleService.getAllCreditBundlesByPackage();
    }

}