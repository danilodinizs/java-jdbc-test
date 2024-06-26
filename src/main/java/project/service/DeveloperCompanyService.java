package project.service;

import project.domain.DeveloperCompany;
import project.repository.DeveloperCompanyRepository;

public class DeveloperCompanyService {
    public static void insert(DeveloperCompany developerCompany) {
        DeveloperCompanyRepository.insert(developerCompany);
    }
    public static void delete(DeveloperCompany developerCompany) {
        validateId(developerCompany.getId());
        DeveloperCompanyRepository.delete(developerCompany);
    }
    public static void update(DeveloperCompany developerCompany) {
        validateId(developerCompany.getId());
        DeveloperCompanyRepository.update(developerCompany);
    }
    public static void validateId(Integer id) {
        if (id == null || id <= 0) throw new IllegalArgumentException();
    }
    public static void findAll() {
        DeveloperCompanyRepository.findAll();
    }
    public static void findByName(DeveloperCompany developerCompany) {
        DeveloperCompanyRepository.findByName(developerCompany);
    }
}
