package com.imanuwel.genesis;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CompanyRepository {

    private final List<Company> companyList = new ArrayList<>();

    public List<Company> findAll() {
        return companyList;
    }

    public Company save(Company company) {
        companyList.add(company);
        return company;
    }

    public Optional<Company> findById(Long companyId) {
        return companyList.stream().filter(c -> c.getId().equals(companyId)).findFirst();
    }

    public Optional<Company> findByVatNumber(String vatNumber) {
        return companyList.stream().filter(c -> c.getVatNumber().equals(vatNumber)).findFirst();
    }
}
