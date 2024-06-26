package project.test;

import project.domain.DeveloperCompany;
import project.repository.DeveloperCompanyRepository;

public class ConnectionFactoryTest {
    public static void main(String[] args) {
        DeveloperCompany developerCompany = DeveloperCompany.builder().name("Mojang").build();
        DeveloperCompanyRepository.save(developerCompany);
    }
}
