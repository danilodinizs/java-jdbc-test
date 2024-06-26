package project.test;

import project.domain.DeveloperCompany;
import project.repository.DeveloperCompanyRepository;
import project.service.DeveloperCompanyService;

public class ConnectionFactoryTest {
    public static void main(String[] args) {
        DeveloperCompany developerCompany = DeveloperCompany.builder().name("Valve").build();
        DeveloperCompanyService.insert(developerCompany);
        //DeveloperCompanyService.findByName(DeveloperCompany.builder().name("iso").build());
        DeveloperCompanyService.findAll();

    }
}
