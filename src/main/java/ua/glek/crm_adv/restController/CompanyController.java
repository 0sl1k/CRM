package ua.glek.crm_adv.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.glek.crm_adv.model.jpa.Company;
import ua.glek.crm_adv.service.CompanyService;

import java.util.List;

@RestController
@RequestMapping("api/company")
public class CompanyController {


    @Autowired
    private CompanyService companyService;

    @PostMapping("/create")
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        return new ResponseEntity<>(companyService.createCompany(company), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Company>> getAll() {
        return new ResponseEntity<>(companyService.getAllCompany(),HttpStatus.OK);
    }

    @PutMapping("/{companyId}/{userId}")
    public String promoteManagerToCompany(@PathVariable Long companyId, @PathVariable Long userId) {
        return companyService.promoteManagerToCompany(companyId,userId);
    }
    @PutMapping("/addProduct/{companyId}/{productId}")
    public Company addProduct(@PathVariable Long companyId,@PathVariable Long productId) {
        return companyService.addProduct(companyId,productId);
    }
}
