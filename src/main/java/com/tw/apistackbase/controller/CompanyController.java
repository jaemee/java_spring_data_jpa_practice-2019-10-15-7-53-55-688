package com.tw.apistackbase.controller;

import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.service.CompanyService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping(path = "/all", produces = {"application/json"})
    public Iterable<Company> list(@RequestParam(required = false, defaultValue = "1") int page,
                                  @RequestParam(required = false, defaultValue = "5") int pageSize) {
        return companyService.findAll(page, pageSize);
    }

    @PostMapping(produces = {"application/json"})
    @ResponseStatus(code = CREATED)
    public Company add(@RequestBody Company company) {
        return companyService.save(company);
    }

    @DeleteMapping(produces = {"application/json"})
    public ResponseEntity delete(@RequestBody Company company) throws NotFoundException {

        Optional<Company> optionalCompany = companyService.delete(company);
        if(optionalCompany.isPresent()){
            return new ResponseEntity<>(optionalCompany.get(), OK);
        }
        throw new NotFoundException("Company not found");

}

    @PutMapping(produces = {"application/json"})
    public ResponseEntity update(@RequestBody Company company) {
        if(companyService.isUpdated(company)) {
            return new ResponseEntity<>(company, OK);
        }
        return new ResponseEntity<>(NOT_FOUND);
    }

    @GetMapping(produces = {"application/json"})
    public Company getCompanyByName(@RequestParam(required = false,defaultValue = "") String name) {
        return companyService.findByNameContaining(name);
    }
}
