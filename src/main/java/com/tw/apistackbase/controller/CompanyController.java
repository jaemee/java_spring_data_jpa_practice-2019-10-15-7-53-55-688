package com.tw.apistackbase.controller;

import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyRepository repository;

    @GetMapping(path = "/all", produces = {"application/json"})
    public Iterable<Company> list(@RequestParam(required = false, defaultValue = "1") int page,
                                  @RequestParam(required = false,defaultValue = "5") int pageSize) {
        return repository.findAll(PageRequest.of(page-1,pageSize, Sort.by("name").ascending()));
    }

    @PostMapping(produces = {"application/json"})
    @ResponseStatus(code = CREATED)
    public Company add(@RequestBody Company company) {
        return repository.save(company);
    }

    @DeleteMapping(produces = {"application/json"})
    public ResponseEntity delete(@RequestBody Company company) {
        Optional<Company> optionalCompany = repository.findById(company.getId());

        if (optionalCompany.isPresent()) {
            repository.delete(optionalCompany.get());
            return new ResponseEntity<>(optionalCompany.get(), OK);
        } else {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @PutMapping(produces = {"application/json"})
    public ResponseEntity update(@RequestBody Company company) {
        Company companyRecord = repository.getOne(company.getId());
        List<Company> companies = repository.findAll();
        if(companies.contains(companyRecord)) {
            repository.save(company);
            return new ResponseEntity<>(company, OK);
        }
        return new ResponseEntity<>(NOT_FOUND);
    }

    @GetMapping(produces = {"application/json"})
    public Company getCompanyByName(@RequestParam(required = false,defaultValue = "") String name) {
        return repository.findByNameContaining(name);
    }
}
