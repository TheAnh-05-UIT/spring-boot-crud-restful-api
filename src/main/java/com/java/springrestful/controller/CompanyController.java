package com.java.springrestful.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.springrestful.domain.Company;
import com.java.springrestful.domain.dto.PagingResultDTO;
import com.java.springrestful.service.CompanyService;

@RestController
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/companies")
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {

        Company newCompany = this.companyService.handleCreateCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCompany);
    }

    @GetMapping("/companies")
    public ResponseEntity<PagingResultDTO> getAllCompany(
            @RequestParam("current") Optional<String> currentOptional,
            @RequestParam("pageSize") Optional<String> pageSizeOptional) {

        String currentString = currentOptional.isPresent() == true ? currentOptional.get() : "";
        String pageSizeString = pageSizeOptional.isPresent() == true ? pageSizeOptional.get() : "";

        int pageNumber = Integer.parseInt(currentString);
        int pageSize = Integer.parseInt(pageSizeString);

        // pageNumber start = 0
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        PagingResultDTO listCompanyPaging = this.companyService.handleGetAllCompany(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listCompanyPaging);
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable("id") Long id) {

        Company companyById = this.companyService.handleGetCompanyById(id);
        return ResponseEntity.status(HttpStatus.OK).body(companyById);
    }

    @PutMapping("/companies/{id}")
    public ResponseEntity<Company> updateCompanyById(
            @PathVariable("id") Long id,
            @RequestBody Company company) {

        Company updateCompany = this.companyService.handleUpdateCompanyByID(id, company);
        return ResponseEntity.status(HttpStatus.OK).body(updateCompany);
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> deleteCompanyById(@PathVariable("id") Long id) {
        this.companyService.handleDeleteCompanyByID(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
