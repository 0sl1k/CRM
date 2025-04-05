package ua.glek.crm_adv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.glek.crm_adv.model.Company;
import ua.glek.crm_adv.model.User;
import ua.glek.crm_adv.repository.CompanyRepo;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private static final String HASH_NAME = "company";

    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private UserService userService;

    @CacheEvict(value = HASH_NAME,key = "#company.id",allEntries = true)
    @Transactional
    public Company createCompany(Company company) {
        Company newCompany = new Company();
        Optional<User> manager = Optional.ofNullable(userService.findById(company.getManager().getId()));
        newCompany.setManager(manager.get());
        newCompany.setName(company.getName());
        newCompany.setDescription(company.getDescription());
        return companyRepo.save(newCompany);
    }
    @Cacheable(value = HASH_NAME)
    public List<Company> getAllCompany(){
        return companyRepo.findAll();
    }
    @CacheEvict(value = HASH_NAME,allEntries = true)
    public String promoteManagerToCompany(Long companyId, Long userId) {
        Optional<Company> savedCompany = Optional.ofNullable(companyRepo.findById(companyId).orElse(null));
        Optional<User> user = Optional.ofNullable(userService.findById(userId));
        if (savedCompany.isPresent() && user.isPresent()) {
            Company company = savedCompany.get();
            company.setManager(user.get());
            companyRepo.save(company);
            return "manager added";
        }else if (savedCompany.isPresent() && user.isEmpty()) {
            return "user not found";

        }
        return "company not found";

    }
}
