package parse;

import model.Company;
import model.CompanyStore;
import model.Price;
import model.PriceStore;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.*;

public class CompanyParse {
    private CompanyStore companyStore;
    private PriceStore priceStore;

    public CompanyParse() {
        this.companyStore = new CompanyStore();
        this.priceStore = new PriceStore();
        fillCompanyList();
    }

    public Document getDocument(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            e.fillInStackTrace();
        }
        return null;
    }

    private void parse(Company company) throws IOException {
        Map<Long, Price> priceMap = new TreeMap<>(Comparator.reverseOrder());

        URL url = new URL("https://query1.finance.yahoo.com/v8/finance/chart/" + company.getTicker());
        Reader in = new InputStreamReader(url.openStream());
        JSONObject originalJSON = ((JSONObject) JSONValue.parse(in));
        JSONObject jsonObj = (JSONObject) JSONValue.parse(originalJSON.get("chart").toString());
        JSONArray resultArr = new JSONArray(jsonObj.get("result").toString());
        jsonObj = (JSONObject) JSONValue.parse(resultArr.get(0).toString());
        JSONObject indicatorsJSON =(JSONObject) JSONValue.parse(jsonObj.get("indicators").toString());
        JSONArray quoteArr = new JSONArray(indicatorsJSON.get("quote").toString());
        JSONObject quoteJSON = (JSONObject) JSONValue.parse(quoteArr.get(0).toString());
        JSONArray highArr = new JSONArray(quoteJSON.get("high").toString());
        JSONArray lowArr = new JSONArray(quoteJSON.get("high").toString());
        JSONArray timestampArr = new JSONArray(jsonObj.get("timestamp").toString());
        System.out.println(highArr.length());
        System.out.println(lowArr.length());
        System.out.println(timestampArr.length());

        for (int i = 0; i < timestampArr.length(); i++) {

            priceMap.put(Long.parseLong(timestampArr.get(i).toString()),
                    new Price(Double.parseDouble(highArr.get(i).toString()),
                            Double.parseDouble(lowArr.get(i).toString()),
                            Long.parseLong(timestampArr.get(i).toString())));
        }
        priceStore.add(company, priceMap);
    }

    public void parseAll() {
        for (Company comp : companyStore.getCompanyList()) {
            try {
                parse(comp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    private void fillCompanyList() {
        Document doc = getDocument("https://finviz.com/screener.ashx?v=111&f=idx_sp500&o=-marketcap");
        Elements els1 = doc.select(".table-dark-row-cp");
        Elements els2 = doc.select("tr.table-light-row-cp");
        for (int i = 0; i < els1.size(); i++) {
            companyStore.add(new Company(els1.get(i).child(1).text(),
                    els1.get(i).child(2).text(),
                    els1.get(i).child(3).text(),
                    els1.get(i).child(4).text()

            ));
            companyStore.add(new Company(els1.get(i).child(1).text(),
                    els2.get(i).child(2).text(),
                    els2.get(i).child(3).text(),
                    els2.get(i).child(4).text()

            ));
        }
    }

    public void printCompanies() {
        for (Company comp : companyStore.getCompanyList()) {
            System.out.println(comp);
        }
    }

    public void printPrices() {
        priceStore.print();
    }
}
