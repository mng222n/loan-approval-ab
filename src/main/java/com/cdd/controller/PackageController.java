package com.cdd.controller;

import java.util.List;
import java.util.UUID;

import com.cdd.dto.PageResult;
import com.cdd.model.Package;
import com.cdd.service.PackageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/package")
public class PackageController {
    @Autowired
    private PackageService packageService;

    @PostMapping("/save")
    public Package savePackage(@RequestBody Package aPackage) {
        return packageService.savePackage(aPackage);
    }

    @GetMapping("/")
    public PageResult<Package> getPackagePageable(Pageable pageable) {
        return packageService.getPackagePageable(pageable);
    }

    @GetMapping("/all")
    public List<Package> getAllPackages() {
        return packageService.getAllPackages();
    }

    @PutMapping("/update/{id}")
    public Package updatePackage(@PathVariable("id") UUID id, @RequestBody Package aPackage) {
        return packageService.updatePackage(id,aPackage);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePackage(@PathVariable("id") UUID id) {
        packageService.deletePackage(id);
    }

    @GetMapping("/get/{id}")
    public Package getPackageById(@PathVariable("id") UUID id) {
        return packageService.getPackageById(id);
    }
}