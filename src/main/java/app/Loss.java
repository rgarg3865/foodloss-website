package app;

public class Loss {
    private String m49code;
    private String country;
    private String region;
    private String cpc_code;
    private String commodity;
    private int year;
    private double loss_percentage;
    private String activity;
    private String food_supply_stage;
    private String cause_of_loss;
    private String classs;
    private String subClass;
    private String groupDiv;
    private int count;
    private double total;
    @SuppressWarnings("unused")
    private double overlap;
    private double avgDifference;
    // private String descriptor; //(AKA commodity)
    
    public double getOverlap() {
        return ((this.getCount() / this.getTotal()) * 100);
    }
    public void setOverlap(double overlap) {
        this.overlap = overlap;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public String getM49code() {
        return m49code;
    }
    public void setM49code(String m49code) {
        this.m49code = m49code;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }
    public String getCpc_code() {
        return cpc_code;
    }
    public void setCpc_code(String cpc_code) {
        this.cpc_code = cpc_code;
    }
    public String getCommodity() {
        return commodity;
    }
    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public double getLoss_percentage() {
        return loss_percentage;
    }
    public void setLoss_percentage(double loss_percentage) {
        this.loss_percentage = loss_percentage;
    }
    public String getActivity() {
        return activity;
    }
    public void setActivity(String activity) {
        this.activity = activity;
    }
    public String getFood_supply_stage() {
        return food_supply_stage;
    }
    public void setFood_supply_stage(String food_supply_stage) {
        this.food_supply_stage = food_supply_stage;
    }
    public String getCause_of_loss() {
        return cause_of_loss;
    }
    public void setCause_of_loss(String cause_of_loss) {
        this.cause_of_loss = cause_of_loss;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }

    public String getClasss() {
        return classs;
    }

    public void setSubClass(String subClass) {
        this.subClass = subClass;
    }

    public String getSubClass() {
        return subClass;
    }

    public void setGroupDiv(String groupDiv) {
        this.groupDiv = groupDiv;
    }

    public String getGroupDiv() {
        return groupDiv;
    }

    private int minYear;
    private int maxYear;

    public int getMinYear() {
        return minYear;
    }
    public void setMinYear(int minYear) {
        this.minYear = minYear;
    }
    public int getMaxYear() {
        return maxYear;
    }
    public void setMaxYear(int maxYear) {
        this.maxYear = maxYear;
    }

    public Loss(int year, String country, double loss_percentage, String commodity) {
        this.year = year;
        this.country = country;
        this.loss_percentage = loss_percentage;
        this.commodity = commodity;
    }


    public Loss(int minYear, int maxYear) {
        this.minYear = minYear;
        this.maxYear = maxYear;
    }

    private double average;

    public double getAverage() {
        return average;
    }
    public void setAverage(double average) {
        this.average = average;
    }

    public Loss(String country, int year, double average) {
        // this.m49code = m49code;
        this.country = country;
        this.year = year;
        this.average = average;
    }

    public Loss(String country, int year, double average, String commodity, String food_supply_stage, String activity, String cause_of_loss) {
        // this.m49code = m49code;
        this.country = country;
        this.year = year;
        this.average = average;
        this.commodity = commodity;
        this.food_supply_stage = food_supply_stage;
        this.activity = activity;
        this.cause_of_loss = cause_of_loss;
    }

    public Loss(String country, String region) {
        this.country = country;
        this.region = region;
    }

    public Loss(String country, int count) {
        this.country = country;
        this.count = count;
    }

    public Loss(double avgDifference, String country, double average) {
        this.avgDifference = avgDifference;
        this.country = country;
        this.average = average;
    }

    public Loss(double avgDifference, String region, String country, double average) {
        this.avgDifference = avgDifference;
        this.region = region;
        this.country = country;
        this.average = average;
    }

    public double getAvgDifference() {
        return avgDifference;
    }
    public void setAvgDifference(double avgDifference) {
        this.avgDifference = avgDifference;
    }

    public Loss(String region, String country, int count) {
        this.region = region;
        this.country = country;
        this.count = count;
    }

    public Loss(String region, String country, int year, double average) {
        this.region = region;
        this.country = country;
        this.year = year;
        this.average = average;
    }

    public Loss(String country, double average, double avgDifference) {
        this.country = country;
        this.average = average;
        this.avgDifference = avgDifference;
    }

}
