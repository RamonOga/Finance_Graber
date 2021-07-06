package model.store;

import model.Company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CompanyStore {
    private Map<Integer, Company> companies;

    public CompanyStore() {
        this.companies = new HashMap<>();
    }

    public void add(Company company) {
        companies.put(company.getId(), company);
    }

    public Company get(int id) {
        return companies.get(id);
    }

    public List<Company> getCompanyList() {
        List<Company> rsl = new ArrayList<>();
        for (Integer id : companies.keySet()) {
            rsl.add(companies.get(id));
        }
        return rsl;
    }
}
