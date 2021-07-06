package model;

public class Company {
    private static int idCounter = 1;
    private int id;
    private String ticker;
    private String name;
    private String sector;
    private String industry;

    public Company(String ticker, String name, String sector, String industry) {
        this.id = idCounter++;
        this.ticker = ticker;
        this.name = name;
        this.sector = sector;
        this.industry = industry;
    }

    public String getTicker() {
        return ticker;
    }

    public String getName() {
        return name;
    }

    public String getSector() {
        return sector;
    }

    public String getIndustry() {
        return industry;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", ticker='" + ticker + '\'' +
                ", name='" + name + '\'' +
                ", sector='" + sector + '\'' +
                ", industry='" + industry + '\'' +
                '}';
    }
}


