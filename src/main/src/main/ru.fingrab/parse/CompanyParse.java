package parse;

import model.Company;
import model.CompanyStore;
import netscape.javascript.JSObject;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

public class CompanyParse {
    CompanyStore store = new CompanyStore();

    public Document getDocument(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            e.fillInStackTrace();
        }
        return null;
    }

    public void parse() throws IOException {
        Document doc = getDocument("https://query1.finance.yahoo.com/v8/finance/chart/AAPL");
        URL url = null;
        url = new URL("https://query1.finance.yahoo.com/v8/finance/chart/AAPL");
        Reader in = new InputStreamReader(url.openStream());
        Object json = JSONValue.parse(in);
        JSONObject originalJSON = (JSONObject) json;
        JSONObject jsonObj2 = (JSONObject) JSONValue.parse(originalJSON.get("chart").toString());

        System.out.println(jsonObj2.get("result"));

    }


    public void fillCompanyList() {
        Document doc = getDocument("https://finviz.com/screener.ashx?v=111&f=idx_sp500&o=-marketcap");
        Elements els1 = doc.select(".table-dark-row-cp");
        Elements els2 = doc.select("tr.table-light-row-cp");
        for (int i = 0; i < els1.size(); i++) {
            store.add(new Company(els1.get(i).child(1).text(),
                    els1.get(i).child(2).text(),
                    els1.get(i).child(3).text(),
                    els1.get(i).child(4).text()

            ));
            store.add(new Company(els1.get(i).child(1).text(),
                    els2.get(i).child(2).text(),
                    els2.get(i).child(3).text(),
                    els2.get(i).child(4).text()

            ));
        }
    }

    public void print() {
        for (Company comp : store.getCompanyList()) {
            System.out.println(comp);
        }
    }


}
