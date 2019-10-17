package com.tw.apistackbase.service;

import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public Iterable<Company> findAll(int page, int pageSize) {
        return companyRepository.findAll(PageRequest.of(page-1,pageSize, Sort.by("name").ascending()));
    }

    public Company save(Company company) {
        return companyRepository.save(company);
    }
}
