package com.tw.apistackbase.service;

import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public boolean isUpdated(Company company){
        Company companyRecord = companyRepository.getOne(company.getId());
        List<Company> companies = companyRepository.findAll();
        if(companies.contains(companyRecord)) {
            companyRepository.save(company);
            return true;
        }
        return false;
    }

    public Company findByNameContaining(String name) {
        return companyRepository.findByNameContaining(name);
    }
}
