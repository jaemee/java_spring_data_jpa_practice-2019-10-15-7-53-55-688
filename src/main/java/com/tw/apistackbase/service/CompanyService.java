package com.tw.apistackbase.service;

import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {

    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Iterable<Company> findAll(int page, int pageSize) {
        return companyRepository.findAll(PageRequest.of(page-1,pageSize, Sort.by("name").ascending()));
    }

    public Company save(Company company) {
        return companyRepository.save(company);
    }

    public Optional<Company> delete(Company company) {
        Optional<Company> optionalCompany = companyRepository.findById(company.getId());
        optionalCompany.ifPresent(value -> companyRepository.delete(value));
        return optionalCompany;
    }
}
