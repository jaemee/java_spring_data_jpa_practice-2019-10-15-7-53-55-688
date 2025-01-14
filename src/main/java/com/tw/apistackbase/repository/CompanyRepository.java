package com.tw.apistackbase.repository;

import com.tw.apistackbase.core.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("SELECT comp FROM Company comp WHERE comp.name=:name")
    Company findOneByName(@Param("name") String name);

    Company findByNameContaining(String  name);
}
