package com.java.springrestful.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public List<Company> handleGetAllCompany(Pageable pageable) {
        Page<Company> pageCompany = this.companyRepository.findAll(pageable);
        return pageCompany.getContent();
    }

    public Company handleGetCompanyById(Long id) {
        Optional<Company> optionalCompany = this.companyRepository.findById(id);
        return optionalCompany.orElseThrow(() -> new RuntimeException("Company not found"));
    }

    public Company handleUpdateCompanyByID(Long id, Company company) {
        Optional<Company> optionalCompany = this.companyRepository.findById(id);
        if (optionalCompany.isPresent()) {
            Company updateCompany = optionalCompany.get();
            updateCompany.setName(company.getName());
            updateCompany.setDescription(company.getDescription());
            updateCompany.setAddress(company.getAddress());
            updateCompany.setLogo(company.getLogo());
            return this.companyRepository.save(updateCompany);
        }
        return null;
    }

    public void handleDeleteCompanyByID(Long id) {
        this.companyRepository.deleteById(id);
    }
}
