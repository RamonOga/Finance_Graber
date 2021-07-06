package model.store;

import model.Company;
import model.Price;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class PriceStore {
    private Map<Company, Map<Long, Price>> prices;

    public PriceStore() {
        this.prices = new HashMap<>();
    }

   public void add(Company company, Map<Long, Price> priceMap) {
        if (!prices.containsKey(company)) {
            prices.put(company, priceMap);
        } else {
            Map<Long, Price> tmp = prices.get(company);
            for (Long l : priceMap.keySet()) {
                tmp.put(l, priceMap.get(l));
            }
        }
    }

    public Map<Long, Price> get(Company company) {
        return prices.get(company);
    }

    public Map<Company, Map<Long, Price>> getAll() {
        return prices;
    }

    public void print() {
        for (Company comp : prices.keySet()) {
            System.out.println(comp.getName());
            System.out.println(prices.get(comp));
        }
    }
}
