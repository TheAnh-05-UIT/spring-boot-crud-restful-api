package com.java.springrestful.service;

import org.springframework.stereotype.Service;

import com.java.springrestful.domain.Company;
import com.java.springrestful.repository.CompanyRepository;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company handleCreateCompany(Company company) {
        return this.companyRepository.save(company);
    }
}
