import model.Company;
import model.CompanyStore;
import parse.CompanyParse;

import java.io.IOException;

public class Runner {
    public static void main(String[] args) throws IOException {
        CompanyParse parse = new CompanyParse();
        parse.parseAll();
        parse.printPrices();
    }
}
