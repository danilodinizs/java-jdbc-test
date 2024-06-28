package project.service;

import lombok.extern.log4j.Log4j2;
import project.domain.DeveloperCompany;
import project.repository.DeveloperCompanyRepository;

import java.util.List;

@Log4j2
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
    public static void updatePreparedStatement(DeveloperCompany developerCompany) {
//        validateId(developerCompany.getId());
        DeveloperCompanyRepository.updatePreparedStatement(developerCompany);
    }
    public static void validateId(Integer id) {
        if (id == null || id <= 0) throw new IllegalArgumentException();
    }
    public static void findAll() {
        DeveloperCompanyRepository.findAll();
    }
    public static List<DeveloperCompany> findByName(String name) {
        return DeveloperCompanyRepository.findByName(name);
    }

    public static void showDCMetaData() {
        DeveloperCompanyRepository.showDCMetaData();
    }
    public static void showDriverMetaData() {
        DeveloperCompanyRepository.showDriverMetaData();
    }

    public static void showTypeScrollWorking() {
        DeveloperCompanyRepository.showTypeScrollWorking();
    }

    public static List<DeveloperCompany> findByNameAndUpdate(String name) {
        return DeveloperCompanyRepository.findByNameAndUpdate(name);
    }
    public static List<DeveloperCompany> findByNameAndInsertIfNull(String name) {
        return DeveloperCompanyRepository.findByNameAndInsertIfNull(name);
    }
    public static List<DeveloperCompany> findByNameAndDelete(String name) {
        return DeveloperCompanyRepository.findByNameAndDelete(name);
    }
    public static List<DeveloperCompany> findByNamePreparedStatement(String name) {
        return DeveloperCompanyRepository.findByNamePreparedStatement(name);
    }
}
