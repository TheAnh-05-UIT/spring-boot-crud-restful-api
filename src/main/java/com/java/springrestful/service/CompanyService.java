package com.java.springrestful.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.java.springrestful.domain.Company;
import com.java.springrestful.domain.dto.MetaData;
import com.java.springrestful.domain.dto.PagingResultDTO;
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

    public PagingResultDTO handleGetAllCompany(Pageable pageable) {
        Page<Company> pageCompany = this.companyRepository.findAll(pageable);

        PagingResultDTO pagingResultDTO = new PagingResultDTO();
        MetaData metaData = new MetaData();

        metaData.setPage(pageCompany.getNumber());
        metaData.setPageSize(pageCompany.getSize());
        metaData.setPages(pageCompany.getTotalPages());
        metaData.setTotal(pageCompany.getTotalElements());

        pagingResultDTO.setMetaData(metaData);
        pagingResultDTO.setResult(pageCompany.getContent());

        return pagingResultDTO;
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
