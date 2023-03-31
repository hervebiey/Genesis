package com.imanuwel.genesis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ContactRepository contactRepository;

    // View all companies

    @GetMapping("/")
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    // Create a company

    @PostMapping("/")
    public Company createCompany(@RequestBody Company company) {
        return companyRepository.save(company);
    }

    // Update a company

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable(value = "id") Long companyId, @RequestBody Company companyDetails) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found for this id: " + companyId));

        company.setAddress(companyDetails.getAddress());
        company.setVatNumber(companyDetails.getVatNumber());

        Company updatedCompany = companyRepository.save(company);
        return ResponseEntity.ok(updatedCompany);
    }

    // Search for a company using its VAT number

    @GetMapping("/{vatNumber}")
    public ResponseEntity<Company> getCompanyByVatNumber(@PathVariable(value = "vatNumber") String vatNumber)
            throws RuntimeException {
        Company company = companyRepository.findByVatNumber(vatNumber)
                .orElseThrow(() -> new RuntimeException("Company not found for this VAT Number: " + vatNumber));

        return ResponseEntity.ok().body(company);
    }

    // Add a contact to a company

    @PostMapping("/companies/{companyId}/contacts/{contactId}")
    public ResponseEntity<?> addContactToCompany(@PathVariable(value = "companyId") Long companyId, @PathVariable(value = "contactId") Long contactId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found for this id: " + companyId));

        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found for this id: " + contactId));

        company.addContact(contact);
        companyRepository.save(company);

        return ResponseEntity.ok().build();
    }
}
