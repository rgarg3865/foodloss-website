 package app;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JDBCConnection {

    // Name of database file (contained in database folder)
    public static final String DATABASE = "jdbc:sqlite:database/foodloss.db";

    /**
     * This creates a JDBC Object so we can keep talking to the database
     */
    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }

    public ArrayList<Loss> getHomePageFacts() {
        // Create the ArrayList of Country objects to return
        ArrayList<Loss> facts = new ArrayList<Loss>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select year, country, loss_percentage, commodity\n" + //
                                "from lossNoDup\n" + //
                                "where (country = 'Australia' or country = 'United States of America') and (year = 2013 or year = 2018)\n" + //
                                "order by loss_percentage desc";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int year = results.getInt("year");
                String country = results.getString("country");
                double loss_percentage = results.getDouble("loss_percentage");
                String commodity = results.getString("commodity");

                // Create a Country Object
                Loss loss = new Loss(year, country, loss_percentage, commodity);

                // Add the Country object to the array
                facts.add(loss);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return facts;
    }

    public ArrayList<Loss> getYearRange() {
        // Create the ArrayList of Country objects to return
        ArrayList<Loss> ranges = new ArrayList<Loss>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select min(year), max(year) from lossNoDup";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int minYear = results.getInt(1);
                int maxYear = results.getInt(2);

                // Create a Country Object
                Loss range = new Loss(minYear, maxYear);

                // Add the Country object to the array
                ranges.add(range);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return ranges;
    }


    public ArrayList<String> getCountriesList() {
        // Create the ArrayList of Country objects to return
        ArrayList<String> list = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT distinct country FROM lossnodup ORDER BY country";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString(1);
                
                // Add the Country object to the array
                list.add(name);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return list;
    }

    public ArrayList<Integer> getYearsList() {
        // Create the ArrayList of Country objects to return
        ArrayList<Integer> yearsList = new ArrayList<Integer>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT DISTINCT year FROM lossNoDup ORDER BY year";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int year = results.getInt(1);
                
                // Add the Country object to the array
                yearsList.add(year);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return yearsList;
    }

    public ArrayList<Loss> getTableDataOff(ArrayList<String> chosenCountries, String startYear, String endYear, String order) {
        // Create the ArrayList of Country objects to return
        ArrayList<Loss> tableData = new ArrayList<Loss>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query

            String query = "select country, year, avg(loss_percentage) as average\n" + //
                                        "from lossnoDup\n" + //
                                        "where (country = '";

            for (int i = 0; i < chosenCountries.size(); ++i) {
                if (i == (chosenCountries.size() - 1)) {
                    query += chosenCountries.get(i);
                }
                else {
                    query += chosenCountries.get(i) + "' or country = '";
                }
            }
            
            query += "') and (year = " + startYear + " or year = " + endYear + ")\n" + //
                                        "group by country, year\n" + //
                                        "order by country, average " + order;
            
                                        // Get Result
            ResultSet results = statement.executeQuery(query);
            System.out.println(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                // String m49code = results.getString("m49code");
                String country = results.getString("country");
                int year = results.getInt("year");
                double average = results.getDouble("average");

                // Create a Country Object
                Loss tableRow = new Loss(country, year, average);

                // Add the Country object to the array
                tableData.add(tableRow);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return tableData;
    }

    public ArrayList<Loss> getTableDataOn(ArrayList<String> chosenCountries, String startYear, String endYear, String order) {
        // Create the ArrayList of Country objects to return
        ArrayList<Loss> tableData = new ArrayList<Loss>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query

            String query = "select country, year, avg(loss_percentage) as average\n" + //
                                        "from lossnoDup\n" + //
                                        "where (country = '";

            for (int i = 0; i < chosenCountries.size(); ++i) {
                if (i == (chosenCountries.size() - 1)) {
                    query += chosenCountries.get(i);
                }
                else {
                    query += chosenCountries.get(i) + "' or country = '";
                }
            }
            
            query += "') and (year >= " + startYear + " and year <= " + endYear + ")\n" + //
                                        "group by country, year\n" + //
                                        "order by country, average " + order;
            
                                        // Get Result
            ResultSet results = statement.executeQuery(query);
            System.out.println(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                // String m49code = results.getString("m49code");
                String country = results.getString("country");
                int year = results.getInt("year");
                double average = results.getDouble("average");

                // Create a Country Object
                Loss tableRow = new Loss(country, year, average);

                // Add the Country object to the array
                tableData.add(tableRow);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return tableData;
    }

    public ArrayList<Loss> getTableDataOffColumns(ArrayList<String> chosenCountries, String startYear, String endYear, String order, ArrayList<String> additionalColumns) {
        // Create the ArrayList of Country objects to return
        ArrayList<Loss> tableDataWithColumns = new ArrayList<Loss>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query

            String columns = ", ";
            for (int i = 0; i < additionalColumns.size(); ++i) {
                if (i == (additionalColumns.size() - 1)) {
                    columns += additionalColumns.get(i);
                }
                else {
                    columns += additionalColumns.get(i) + ", ";
                }
            }

            String query = "select country, year, avg(loss_percentage) as average" + columns + " from lossnodup where (country = '";

            for (int i = 0; i < chosenCountries.size(); ++i) {
                if (i == (chosenCountries.size() - 1)) {
                    query += chosenCountries.get(i);
                }
                else {
                    query += chosenCountries.get(i) + "' or country = '";
                }
            }
            
            query += "') and (year = " + startYear + " or year = " + endYear + ")\n" + //
                                        "group by country, year" + columns + "\n" + //
                                        "order by country, year, average " + order;
            
                                        // Get Result
            ResultSet results = statement.executeQuery(query);
            // System.out.println(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                // String m49code = results.getString("m49code");
                String country = results.getString("country");
                int year = results.getInt("year");
                double average = results.getDouble("average");
                String commodity = "";
                String food_supply_stage = "";
                String activity = "";
                String cause_of_loss = "";

                for (int i = 0; i < additionalColumns.size(); ++i) {
                    if (additionalColumns.get(i).equals("commodity")) {
                        commodity = results.getString("commodity");
                    }
                    else if (additionalColumns.get(i).equals("food_supply_stage")) {
                        food_supply_stage = results.getString("food_supply_stage");
                    }
                    else if (additionalColumns.get(i).equals("activity")) {
                        activity = results.getString("activity");
                    }
                    else if (additionalColumns.get(i).equals("cause_of_loss")) {
                        cause_of_loss = results.getString("cause_of_loss");
                    }
                }

                // Create a Country Object
                Loss tableRow = new Loss(country, year, average, commodity, food_supply_stage, activity, cause_of_loss);

                // Add the Country object to the array
                tableDataWithColumns.add(tableRow);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return tableDataWithColumns;
    }

    public ArrayList<Loss> getTableDataOnColumns(ArrayList<String> chosenCountries, String startYear, String endYear, String order, ArrayList<String> additionalColumns) {
        // Create the ArrayList of Country objects to return
        ArrayList<Loss> tableDataWithColumns = new ArrayList<Loss>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query

            String columns = ", ";
            for (int i = 0; i < additionalColumns.size(); ++i) {
                if (i == (additionalColumns.size() - 1)) {
                    columns += additionalColumns.get(i);
                }
                else {
                    columns += additionalColumns.get(i) + ", ";
                }
            }

            String query = "select country, year, avg(loss_percentage) as average" + columns + " from lossnodup where (country = '";

            for (int i = 0; i < chosenCountries.size(); ++i) {
                if (i == (chosenCountries.size() - 1)) {
                    query += chosenCountries.get(i);
                }
                else {
                    query += chosenCountries.get(i) + "' or country = '";
                }
            }
            
            query += "') and (year >= " + startYear + " and year <= " + endYear + ")\n" + //
                                        "group by country, year" + columns + "\n" + //
                                        "order by country, year, average " + order;
            
                                        // Get Result
            ResultSet results = statement.executeQuery(query);
            // System.out.println(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                // String m49code = results.getString("m49code");
                String country = results.getString("country");
                int year = results.getInt("year");
                double average = results.getDouble("average");
                String commodity = "";
                String food_supply_stage = "";
                String activity = "";
                String cause_of_loss = "";

                for (int i = 0; i < additionalColumns.size(); ++i) {
                    if (additionalColumns.get(i).equals("commodity")) {
                        commodity = results.getString("commodity");
                    }
                    else if (additionalColumns.get(i).equals("food_supply_stage")) {
                        food_supply_stage = results.getString("food_supply_stage");
                    }
                    else if (additionalColumns.get(i).equals("activity")) {
                        activity = results.getString("activity");
                    }
                    else if (additionalColumns.get(i).equals("cause_of_loss")) {
                        cause_of_loss = results.getString("cause_of_loss");
                    }
                }

                // Create a Country Object
                Loss tableRow = new Loss(country, year, average, commodity, food_supply_stage, activity, cause_of_loss);

                // Add the Country object to the array
                tableDataWithColumns.add(tableRow);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return tableDataWithColumns;
    }


    public double getTotalLossPercentage(String country, int year) {
        // Create the ArrayList of Country objects to return
        // ArrayList<String> list = new ArrayList<String>();
        double total = 0;

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select avg(loss_percentage) from lossnodup where country = '" + country + "' and year = " + year;
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                total = results.getDouble(1);
                
                // Add the Country object to the array
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return total;
    }

    public double getTotalLossPercentageRegion(String region, String country, int year) {
        // Create the ArrayList of Country objects to return
        // ArrayList<String> list = new ArrayList<String>();
        double total = 0;

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select avg(loss_percentage) from lossnodup where region = '" + region + "' and country = '" + country + "' and year = " + year;
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                total = results.getDouble(1);
                
                // Add the Country object to the array
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return total;
    }



    public ArrayList<Double> getDifference(String country, int startYear, int endYear) {
        // Create the ArrayList of Country objects to return
        // ArrayList<String> list = new ArrayList<String>();
        ArrayList<Double> values = new ArrayList<Double>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select avg(loss_percentage) from lossnodup \n" + //
                                "where (country = '" + country + "') and (year = " + startYear + " or year = " + endYear + ")\n" + //
                                "group by country, year";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);
            

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                double value = results.getDouble(1);
                
                // Add the Country object to the array
                values.add(value);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return values;
    }

    public double getTotalLossPercentageFood(String commodity, int year) {
        // Create the ArrayList of Country objects to return
        // ArrayList<String> list = new ArrayList<String>();
        double total = 0;

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select avg(loss_percentage) from lossnodup where commodity = '" + commodity + "' and year = " + year;
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                total = results.getDouble(1);
                
                // Add the Country object to the array
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return total;
    }

    public ArrayList<String> getCountriesWithRegions() {
        // Create the ArrayList of Country objects to return
        // ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> countriesWithRegions = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select distinct country from lossnodup where region is not null order by country";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);
            

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String country = results.getString(1);

                countriesWithRegions.add(country);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return countriesWithRegions;
    }


    public ArrayList<Loss> getRegionsList() {
        // Create the ArrayList of Country objects to return
        // ArrayList<String> list = new ArrayList<String>();
        ArrayList<Loss> regions = new ArrayList<Loss>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select distinct country, region from lossnodup where region is not null order by country";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);
            

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String country = results.getString(1);
                String region = results.getString(2);

                Loss row = new Loss(country, region);

                regions.add(row);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return regions;
    }



    public ArrayList<String> getCommoditiesList() {
        // Create the ArrayList of Country objects to return
        // ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> commoditiesList = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT DISTINCT commodity FROM lossNoDup ORDER BY commodity asc";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);
            

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String commodity = results.getString(1);
                

                commoditiesList.add(commodity);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return commoditiesList;
    }


    public ArrayList<String> getCountryCommodities(String country, String year) {
        // Create the ArrayList of Country objects to return
        // ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> countryCommodities = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select distinct commodity from lossnodup where (country = '" + country + "' and year = " + year + ") order by commodity";
            // System.out.println(query);

            // Get Result
            ResultSet results = statement.executeQuery(query);
            

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String commodity = results.getString(1);
                

                countryCommodities.add(commodity);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return countryCommodities;
    }

    public ArrayList<String> getRegionCommodities(String region, String country, String year) {
        // Create the ArrayList of Country objects to return
        // ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> regionCommodities = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select distinct commodity from lossnodup where (region = '" + region + "' and year = " + year + " and country = '" + country + "') order by commodity";
            System.out.println(query);

            // Get Result
            ResultSet results = statement.executeQuery(query);
            

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String commodity = results.getString(1);
                

                regionCommodities.add(commodity);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return regionCommodities;
    }


    public ArrayList<Loss> getSimilarFoodAbsoluteCount(ArrayList<String> countryCommodities, String country, String year) {
        // Create the ArrayList of Country objects to return
        ArrayList<Loss> similarFoodAbsoluteCount = new ArrayList<Loss>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            
            String commodities = "(commodity = '";
            for (int i = 0; i < countryCommodities.size(); ++i) {
                if (i == (countryCommodities.size() - 1)) {
                    commodities += countryCommodities.get(i) + "')";
                }
                else {
                    commodities += countryCommodities.get(i) + "' or commodity = '";
                }
            }

            String query = "select country, count(*) as matchedCommodities from (\n" + //
                                "select distinct commodity, country from lossnodup \n" + //
                                "where (" + commodities + " \n" + //
                                "and country != '" + country + "' and year = " + year + ")\n" + //
                                ") \n" + //
                                "group by country order by matchedCommodities desc, country asc";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);
            // System.out.println(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                // String m49code = results.getString("m49code");
                String countryName = results.getString(1);
                int commodityMatchCount = results.getInt(2);

                // Create a Country Object

                Loss tableRow = new Loss(countryName, commodityMatchCount);

                // Add the Country object to the array
                similarFoodAbsoluteCount.add(tableRow);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return similarFoodAbsoluteCount;
    }

    public ArrayList<Loss> getSimilarFoodAbsoluteCountRegion(ArrayList<String> regionCommodities, String region, String country, String year) {
        // Create the ArrayList of Country objects to return
        ArrayList<Loss> similarFoodAbsoluteCountRegion = new ArrayList<Loss>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            
            String commodities = "(commodity = '";
            for (int i = 0; i < regionCommodities.size(); ++i) {
                if (i == (regionCommodities.size() - 1)) {
                    commodities += regionCommodities.get(i) + "')";
                }
                else {
                    commodities += regionCommodities.get(i) + "' or commodity = '";
                }
            }

            String query = "select region, country, count(*) as matchedCommodities from (\n" + //
                                "select distinct commodity, country, region, (region || ':' || country) as combination from lossnodup \n" + //
                                "where (" + commodities + " \n" + //
                                "and year = " + year + " and combination != '" + region + ":" + country + "')\n" + //
                                ") \n" + //
                                "group by country, region order by matchedCommodities desc, country asc, region asc";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);
            System.out.println(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                // String m49code = results.getString("m49code");
                String regionName = results.getString(1);
                String countryName = results.getString(2);
                int commodityMatchCount = results.getInt(3);

                // Create a Country Object

                Loss tableRow = new Loss(regionName, countryName, commodityMatchCount);

                // Add the Country object to the array
                similarFoodAbsoluteCountRegion.add(tableRow);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return similarFoodAbsoluteCountRegion;
    }


    public ArrayList<String> getSimilarCountryCommodities(ArrayList<String> commoditiesToCompare, String country, String year) {
        // Create the ArrayList of Country objects to return
        // ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> similarCountryCommodities = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select distinct commodity from lossnodup where (country = '" + country + "' and year = " + year + " and (commodity = '";
            
            for (int i = 0; i < commoditiesToCompare.size(); ++i) {
                if (i == (commoditiesToCompare.size() - 1)) {
                    query += commoditiesToCompare.get(i) + "')) order by commodity";
                }
                else {
                    query += commoditiesToCompare.get(i) + "' or commodity = '";
                }
            }
            
            // )) order by commodity";
            // System.out.println(query);

            // Get Result
            ResultSet results = statement.executeQuery(query);
            

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String commodity = results.getString(1);
                

                similarCountryCommodities.add(commodity);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return similarCountryCommodities;
    }

    public ArrayList<String> getSimilarRegionCommodities(ArrayList<String> commoditiesToCompare, String region, String country, String year) {
        // Create the ArrayList of Country objects to return
        // ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> similarRegionCommodities = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select distinct commodity from lossnodup where (region = '" + region + "' and country = '" + country + "' and year = " + year + " and (commodity = '";
            
            for (int i = 0; i < commoditiesToCompare.size(); ++i) {
                if (i == (commoditiesToCompare.size() - 1)) {
                    query += commoditiesToCompare.get(i) + "')) order by commodity";
                }
                else {
                    query += commoditiesToCompare.get(i) + "' or commodity = '";
                }
            }
            
            // )) order by commodity";
            System.out.println(query);

            // Get Result
            ResultSet results = statement.executeQuery(query);
            

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String commodity = results.getString(1);
                

                similarRegionCommodities.add(commodity);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return similarRegionCommodities;
    }


    public double getUnion(String country, String chosenCountry, String year) {
        // Create the ArrayList of Country objects to return
        // ArrayList<String> list = new ArrayList<String>();
        double total = 0;

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select count(*) from (select distinct commodity from lossnodup where (country = '" + chosenCountry + "' and year = '" + year + "')\n" + //
                                "\n" + //
                                "UNION\n" + //
                                "\n" + //
                                "select distinct commodity from lossnodup where (country = '" + country + "' and year = '" + year + "'))";
            
            // )) order by commodity";
            // System.out.println(query);

            // Get Result
            ResultSet results = statement.executeQuery(query);
            

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                total = results.getDouble(1);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return total;
    }

    public double getUnionRegion(String region, String country, String chosenRegion, String chosenRegionCountry, String year) {
        // Create the ArrayList of Country objects to return
        // ArrayList<String> list = new ArrayList<String>();
        double total = 0;

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select count(*) from (select distinct commodity from lossnodup where (region = '" + region + "' and country = '" + country + "' and year = '" + year + "')\n" + //
                                "\n" + //
                                "UNION\n" + //
                                "\n" + //
                                "select distinct commodity from lossnodup where (region = '" + chosenRegion + "' and country = '" + chosenRegionCountry + "' and year = '" + year + "'))";
            
            // )) order by commodity";
            System.out.println(query);

            // Get Result
            ResultSet results = statement.executeQuery(query);
            

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                total = results.getDouble(1);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return total;
    }



    public ArrayList<Loss> getCountriesInYear(String chosenCountry, int year) {
        // Create the ArrayList of Country objects to return
        // ArrayList<String> list = new ArrayList<String>();
        ArrayList<Loss> countriesInYear = new ArrayList<Loss>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select avg(loss_percentage) as average, country from lossnodup where (year = " + year + " and country != '" + chosenCountry + "') group by country order by average";
            
            // )) order by commodity";
            // System.out.println(query);

            // Get Result
            ResultSet results = statement.executeQuery(query);
            

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                double lossPercentage = results.getDouble(1);
                String commodity = results.getString(2);
                
                Loss row = new Loss(commodity, year, lossPercentage);
                countriesInYear.add(row);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return countriesInYear;
    }


    public ArrayList<Loss> getRegionsInYear(String region, String country, int year) {
        // Create the ArrayList of Country objects to return
        // ArrayList<String> list = new ArrayList<String>();
        ArrayList<Loss> regionsInYear = new ArrayList<Loss>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select avg(loss_percentage) as average, country, region, (region || ':' || country) as combination from lossnodup where (year = " + year + " and combination != '" + region + ":" + country + "') group by country, region order by average";
            
            // )) order by commodity";
            // System.out.println(query);

            // Get Result
            ResultSet results = statement.executeQuery(query);
            

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                double lossPercentage = results.getDouble(1);
                String countryName= results.getString(2);
                String regionName = results.getString(3);
                
                Loss row = new Loss(regionName, countryName, year, lossPercentage);
                regionsInYear.add(row);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return regionsInYear;
    }

    public ArrayList<Loss> getBothData(String country, int year) {
        // Create the ArrayList of Country objects to return
        // ArrayList<String> list = new ArrayList<String>();
        ArrayList<Loss> bothData = new ArrayList<Loss>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "with selectedLoss as(\n" + //
                                "    select avg(loss_percentage) as targetLoss\n" + //
                                "    from lossnodup \n" + //
                                "    where country = '" + country + "' and year = " + year + "\n" + //
                                ")\n" + //
                                "select country, avg(loss_Percentage) as averageLoss, sl.targetLoss, abs(targetLoss - avg(loss_Percentage)) as difference\n" + //
                                "from lossnodup as a\n" + //
                                "left join selectedLoss as sl\n" + //
                                "where year = " + year + " and country != '" + country + "' and commodity in (select distinct commodity from lossnodup where country = '" + country + "' and year = " + year + ")\n" + //
                                "group by country\n" + //
                                "order by difference asc, country asc";
            
            // )) order by commodity";
            // System.out.println(query);

            // Get Result
            ResultSet results = statement.executeQuery(query);
            

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String countryName = results.getString(1);
                double average = results.getDouble(2);
                double avgDifference = results.getDouble(4);
                
                Loss row = new Loss(countryName, average, avgDifference);
                bothData.add(row);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return bothData;
    }
    
}
