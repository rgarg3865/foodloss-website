package app;

import java.util.*;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class PageST2A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page2A.html";

    @Override
    public void handle(Context context) throws Exception {
        JDBCConnection jdbc = new JDBCConnection();
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>COUNTRY DATA</title>" + 
               "<link rel='icon' type='image/png' href='weblogo.png'>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "<link href='https://unpkg.com/css.gg@2.0.0/icons/css/chevron-double-up-r.css' rel='stylesheet'>";
        
        html += """
            <style>
              .content2a {
                padding-left: 16px;
                padding-right: 16px;
              }
            </style>
            """;

        html = html + "</head>";

        // Add the body
        html = html + "<body><div class='scrollWatcher'></div>";

        // Add the topnav
        // This uses a Java v15+ Text Block
        html = html + """
            <div class="topnav">
            <a href='/' style="float:left">FOODLOSS.COM</a>

            <a href='data.html'>DATA</a>
          </div>
        """;

        // Add header content block
        html = html + """
            <div class='header2a' style='display: flex; flex-direction: column; align-items: center; justify-content: center; font-size: 20px; margin-bottom: 20px'>
                <p style='margin-bottom: 0px;'><b>COUNTRY DATA</b></p>
                <p style='margin-top: 5px; font-size: 0.75em; color: rgba(255, 255, 255, 0.6)'>Search for food loss data filtered by different countries in the database</p>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content2a'>";
        
        html += "<div class='sideContent'>";
        // Add HTML for the page content
        html = html + """
          <form action='/page2A.html' method='post' id='containerCountryPage'>
             <a style='z-index: 999'><b>CHOOSE COUNTRIES:</b></a>
             <div class='form-group' id='countryDiv' style='margin-bottom:15px;'>
            """;

        ArrayList<String> countriesList = jdbc.getCountriesList();

        for (int i = 0 ; i < countriesList.size() ; ++i) {
            html += "<input type='checkbox' id='country_select" + i + "' name='country_select" + i + "' value='" + countriesList.get(i) + "'>";
            html += "<label for='country_select" + i + "'>" + countriesList.get(i) + "</label><br>";
        }

        html += "</div>";

        html = html + """
             <div class='form-group' style='padding-bottom:3px;'>
               <label for='startYear_drop'><b>START YEAR:</b></label>
               <select id='startYear_drop' name='startYear_drop' style='border-radius: 7px; width: 30%;'>
               <option>SELECT</option>
            """;
          
        ArrayList<Integer> yearsList = jdbc.getYearsList();

        html += "<option>" + yearsList.get(0) + "</option>";
        html += "<option>1967</option>";

        for (int i = 1 ; i < yearsList.size() ; ++i) {
          html = html + "<option>" + yearsList.get(i) +"</option>";
        }

        html += """
            </select>
            </div>
            """;

        html = html + """
              <div class='form-group' style='margin-bottom:15px;'>
                <label for='endYear_drop'><b>END YEAR:</b></label>
                <select id='endYear_drop' name='endYear_drop' style='border-radius: 7px; width: 30%;'>
                <option>SELECT</option>
             """;
 
        html += "<option>" + yearsList.get(0) + "</option>";
        html += "<option>1967</option>";
 
        for (int i = 1 ; i < yearsList.size() ; ++i) {
           html = html + "<option>" + yearsList.get(i) +"</option>";
        }
 
        html += """
             </select>
             </div>
             """;

        html += """
              <div class='form-group' style='margin-bottom: 15px'>
                <p class='hint' style='margin:0px; padding:0px; padding-bottom: 3px;'><b>SHOW YEARS IN SELECTED RANGE?</b><span class='hintText' style='width:285px'>Turning this on will show data for all <b>AVAILABLE</b> years between the start and end year, off will <b>ONLY</b> show data for the start and the end year</span></p><br>
                <input type='radio' id='on' name='years_radio' value='on'>
                <label for="on">YES</label><br>
                <input type='radio' id='off' name='years_radio' value='off'>
                <label for="off">NO</label>
             </div>
            """;

        html = html + """
              <div class='form-group'>
                <p style='margin:0px; padding:0px; padding-bottom: 3px'><b>SORT FOOD LOSS PERCENTAGE BY:</b></p>
                <input type='radio' id='asc' name='order_radio' value='asc'>
                <label for="asc">ASCENDING</label><br>
                <input type='radio' id='desc' name='order_radio' value='desc'>
                <label for="desc">DESCENDING</label>
             </div>
             """;

        html = html + """
              <div class='form-group' style='padding-bottom: 5px'>
                <p style='margin:0px; padding:0px; margin-top: 15px;padding-bottom: 3px;'><b>ADD COLUMNS TO RESULTS TABLE:</b></p>
                <input type='checkbox' id='commodity' name='column_select0' value='commodity'>
                <label class='hint' for="commodity">COMMODITY<span class='hintText' style='margin-left: -130px;'>The actual food item that was lost/wasted</span></label>
                <input type='checkbox' id='food_supply_stage' name='column_select1' value='food_supply_stage'>
                <label class='hint' for="food_supply_stage">FOOD SUPPLY STAGE<span class='hintText' style='margin-left: -210px;'>The stage in the supply chain during which the loss/waste occured</span></label><br>
                <input type='checkbox' id='activity' name='column_select2' value='activity'>
                <label class='hint' for="activity">ACTIVITY<span class='hintText' style='margin-left: -100px;'>The task during which the loss/waste occured</span></label>
                <input type='checkbox' id='cause_of_loss' name='column_select3' value='cause_of_loss'>
                <label class='hint' for="cause_of_loss">CAUSE OF LOSS<span class='hintText' style='margin-left: -150px;'>Why the loss/waste occured</span></label>
             </div>
             """;
        
        html = html + "<button type='submit' class='submitCountryPage'>SHOW DATA</button>";
        html = html + "</form>";

        html+="<div class='backToTop'><a href='#top'><div class='iconAndText'><i style='margin-right: 7px;' class='gg-chevron-double-up-r'></i><span>BACK TO TOP</span></div></a></div>";
        //close sideContent div
        html += "</div>";

        boolean isValid = false;

        ArrayList<String> chosenCountries = new ArrayList<String>();

        //country arraylist
        for (int i = 0; i < countriesList.size(); ++i) {
          String chosenCountry = context.formParam("country_select" + i);
          if (chosenCountry != null) {
            chosenCountries.add(chosenCountry);
          }
          // else {
          //   continue;
          // }  
        }

        ArrayList<String> additionalColumns = new ArrayList<String>();

        for (int i = 0; i < 4; ++i) {
          String column = context.formParam("column_select" + i);
          if (column != null) {
            additionalColumns.add(column);
          }
        }

        String startYear = context.formParam("startYear_drop");
        String endYear = context.formParam("endYear_drop");
        String order = context.formParam("order_radio");
        String allYears = context.formParam("years_radio");

        html  += "<div class='mainContent'>";
        html += "<div class='inputMessage'>";
        //check valid input
        if ((startYear != null) && !(startYear.equalsIgnoreCase("SELECT")) && (endYear != null) && !(endYear.equalsIgnoreCase("SELECT")) && (chosenCountries.size() > 0) && (order != null) && (allYears != null)) {
          if (!((Integer.valueOf(startYear)) > (Integer.valueOf(endYear)))) {
            isValid = true;
            if (allYears.equals("off")) {
              html += "<a><b><a style='color: #fab802'>CHOSEN YEARS: </a></b>" + startYear + ", " + endYear + "</a><br><br>";
              html += "<a><b><a style='color: #fab802'>CHOSEN COUNTRIES: </a></b>";
              for (int i = 0; i < chosenCountries.size(); ++i) {
                if (i == (chosenCountries.size() - 1)) {
                  html += chosenCountries.get(i) + "</a></div>";
                }
                else {
                  html += chosenCountries.get(i) + ", ";
                }
              }
            }
            else if (allYears.equals("on")) {
              html += "<a><b><a style='color: #fab802'>CHOSEN YEARS: </a></b>" + startYear + " - " + endYear + "</a><br><br>";
              html += "<a><b><a style='color: #fab802'>CHOSEN COUNTRIES: </a></b>";
              for (int i = 0; i < chosenCountries.size(); ++i) {
                if (i == (chosenCountries.size() - 1)) {
                  html += chosenCountries.get(i) + "</a></div>";
                }
                else {
                  html += chosenCountries.get(i) + ", ";
                }
              }
            }
          }
          else {
            html += "<a style='color: #fc7979'><b>START YEAR MUST BE LESS THAN OR EQUAL TO THE END YEAR.</b></a><br><br>";
            html += "<a style='color: #fc7979'><b>PLEASE CHOOSE AT LEAST ONE OPTION FROM EVERY INPUT FIELD, ADDITIONAL COLUMNS ARE OPTIONAL.</b></a></div>";
          }
        }
        else {
          html += "<a style='color: #fc7979;'><b>PLEASE CHOOSE AT LEAST ONE OPTION FROM EVERY INPUT FIELD, ADDITIONAL COLUMNS ARE OPTIONAL.</b></a></div>";
        }
        //close inputMessage div
        // html += "</div>";
       

        if (isValid && allYears.equals("off")) {
          ArrayList<Loss> tableData = jdbc.getTableDataOff(chosenCountries, startYear, endYear, order);
          if ((!tableData.isEmpty()) && (additionalColumns.isEmpty())) {
            html += "<div class='tableContainer'>";
            html += """
              <table class='contentTable'>
              <thead>
                <tr>

                  <th>COUNTRY</th>
                  <th>YEAR</th>
                  <th>AVG LOSS %</th>
                </tr>
              </thead>
              <tbody>
              """;
            for (int i = 0; i < tableData.size(); ++i) {
              html += """
                  <tr>
                    
                    <td>
                    """ + tableData.get(i).getCountry() + """
                    </td>
                    <td>
                    """ + tableData.get(i).getYear() + """
                    </td>
                    <td>
                    """ + String.format("%.3f", tableData.get(i).getAverage()) + """
                    </td>
                  </tr>
                  """;

                  if (i < tableData.size() - 1) {
                    if (tableData.get(i).getCountry().equals(tableData.get(i + 1).getCountry())) {
                      continue;
                    }
                    else {
                      ArrayList<Double> differenceValues = jdbc.getDifference(tableData.get(i).getCountry(), Integer.valueOf(startYear), Integer.valueOf(endYear));
                      if(differenceValues.size() == 2) {
                        double difference = differenceValues.get(1) - differenceValues.get(0);
                        if (difference <= 0) {
                          html += "<tr><td colspan='100' class='message' style='color: #32a85a'>THE CHANGE IN THE TOTAL LOSS PERCENTAGE FOR " + tableData.get(i).getCountry().toUpperCase() + " BETWEEN " + startYear + " AND " + endYear + " WAS " + String.format("%.3f", difference) + "%</td></tr>";
                        }
                        else {
                          html += "<tr><td colspan='100' class='message' style='color: #fc7979'>THE CHANGE IN THE TOTAL LOSS PERCENTAGE FOR " + tableData.get(i).getCountry().toUpperCase() + " BETWEEN " + startYear + " AND " + endYear + " WAS +" + String.format("%.3f", difference) + "%</td></tr>";
                        }
                      }
                      else {
                        html += "<tr><td colspan='100' class='message' style='color: #fab802'>INSUFFICIENT DATA TO CALCULATE THE CHANGE IN TOTAL LOSS PERCENTAGE FOR " + tableData.get(i).getCountry().toUpperCase() + " BETWEEN " + startYear + " AND " + endYear + "</td></tr>";
                      }
                    }
                  }

                  if (i == tableData.size() - 1) {
                    ArrayList<Double> differenceValues = jdbc.getDifference(tableData.get(i).getCountry(), Integer.valueOf(startYear), Integer.valueOf(endYear));
                    if(differenceValues.size() == 2) {
                      double difference = differenceValues.get(1) - differenceValues.get(0);
                      if (difference <= 0) {
                        html += "<tr><td colspan='100' class='message' style='color: #32a85a'>THE CHANGE IN THE TOTAL LOSS PERCENTAGE FOR " + tableData.get(i).getCountry().toUpperCase() + " BETWEEN " + startYear + " AND " + endYear + " WAS " + String.format("%.3f", difference) + "%</td></tr>";
                      }
                      else {
                        html += "<tr><td colspan='100' class='message' style='color: #fc7979'>THE CHANGE IN THE TOTAL LOSS PERCENTAGE FOR " + tableData.get(i).getCountry().toUpperCase() + " BETWEEN " + startYear + " AND " + endYear + " WAS +" + String.format("%.3f", difference) + "%</td></tr>";
                      }
                    }
                    else {
                      html += "<tr><td colspan='100' class='message' style='color: #fab802'>INSUFFICIENT DATA TO CALCULATE THE CHANGE IN TOTAL LOSS PERCENTAGE FOR " + tableData.get(i).getCountry().toUpperCase() + " BETWEEN " + startYear + " AND " + endYear + "</td></tr>";
                    }
                  }

            }
          html += "</tbody>";
          html += "</table>";
          html += "</div>";
          }
          else if (tableData.isEmpty()) {
            html += "<div class='tableContainer'>";
            html += "<a style='color: #fc7979'><b>SORRY, THERE IS NO DATA AVAILABLE FOR THIS SELECTION!</b></a>";
            html += "</div>";
          }

          else if ((!tableData.isEmpty()) && (!additionalColumns.isEmpty())) {
            ArrayList<Loss> tableDataWithColumns = jdbc.getTableDataOffColumns(chosenCountries, startYear, endYear, order, additionalColumns);
            // System.out.println(tableDataWithColumns.size());

            html += "<div class='tableContainer'>";
            html += """
              <table class='contentTable'>
              <thead>
                <tr>

                  <th>COUNTRY</th>
                  <th>YEAR</th>
                  
              """;

              for (int i = 0; i < additionalColumns.size(); ++i) {
                if (additionalColumns.get(i).equals("commodity")) {
                  html += "<th>COMMODITY</th>";
                }
                if (additionalColumns.get(i).equals("food_supply_stage")) {
                  html += "<th>SUPPLY STAGE</th>";
                }
                if (additionalColumns.get(i).equals("activity")) {
                  html += "<th>ACTIVITY</th>";
                }
                if (additionalColumns.get(i).equals("cause_of_loss")) {
                  html += "<th>CAUSE OF LOSS</th>";
                }
              }
            html += "<th>AVG LOSS %</th></tr></thead><tbody>";

            for (int i = 0; i < tableDataWithColumns.size(); ++i) {
              html += """
                  <tr>
                    
                    <td>
                    """ + tableDataWithColumns.get(i).getCountry() + """
                    </td>
                    <td>
                    """ + tableDataWithColumns.get(i).getYear() + """
                    </td>
                  

                  """;
                  for (int j = 0; j < additionalColumns.size(); ++j) {
                    if (additionalColumns.get(j).equals("commodity")) {
                      html += "<td>" + tableDataWithColumns.get(i).getCommodity() + "</td>";
                    }
                    if (additionalColumns.get(j).equals("food_supply_stage")) {
                      html += "<td>" + tableDataWithColumns.get(i).getFood_supply_stage() + "</td>";
                    }
                    if (additionalColumns.get(j).equals("activity")) {
                      html += "<td>" + tableDataWithColumns.get(i).getActivity() + "</td>";
                    }
                    if (additionalColumns.get(j).equals("cause_of_loss")) {
                      html += "<td>" + tableDataWithColumns.get(i).getCause_of_loss() + "</td>";
                    }
                  }
    
                  html += "<td>" + String.format("%.3f", tableDataWithColumns.get(i).getAverage()) + "</td></tr>";

                
                  if (i < tableDataWithColumns.size() - 1) {
                    if ((tableDataWithColumns.get(i).getCountry().equals(tableDataWithColumns.get(i + 1).getCountry())) && (tableDataWithColumns.get(i).getYear() == (tableDataWithColumns.get(i + 1).getYear()))) {
                      continue;
                    }
                    else {
                      double totalLossPercentage = jdbc.getTotalLossPercentage(tableDataWithColumns.get(i).getCountry(), tableDataWithColumns.get(i).getYear());
                      html += "<tr><td colspan='100' class='message'>THE TOTAL AVERAGE LOSS PERCENTAGE FOR " + tableDataWithColumns.get(i).getCountry().toUpperCase() + " IN " + tableDataWithColumns.get(i).getYear() + " WAS " + String.format("%.3f", totalLossPercentage) + "%</td></tr>";
                    }
                  }

                  if (i == tableDataWithColumns.size() - 1) {
                    double totalLossPercentageLast = jdbc.getTotalLossPercentage(tableDataWithColumns.get(i).getCountry(), tableDataWithColumns.get(i).getYear());
                    html += "<tr><td colspan='100' class='message'>THE TOTAL AVERAGE LOSS PERCENTAGE FOR " + tableDataWithColumns.get(i).getCountry().toUpperCase() + " IN " + tableDataWithColumns.get(i).getYear() + " WAS " + String.format("%.3f", totalLossPercentageLast) + "%</td></tr>";
                  }

                  if (i < tableDataWithColumns.size() - 1) {
                    if (tableDataWithColumns.get(i).getCountry().equals(tableDataWithColumns.get(i + 1).getCountry())) {
                      continue;
                    }
                    else {
                      ArrayList<Double> differenceValues = jdbc.getDifference(tableDataWithColumns.get(i).getCountry(), Integer.valueOf(startYear), Integer.valueOf(endYear));
                      if(differenceValues.size() == 2) {
                        double difference = differenceValues.get(1) - differenceValues.get(0);
                        if (difference <= 0) {
                          html += "<tr><td colspan='100' class='message' style='color: #32a85a'>THE CHANGE IN THE TOTAL LOSS PERCENTAGE FOR " + tableDataWithColumns.get(i).getCountry().toUpperCase() + " BETWEEN " + startYear + " AND " + endYear + " WAS " + String.format("%.3f", difference) + "%</td></tr>";
                        }
                        else {
                          html += "<tr><td colspan='100' class='message' style='color: #fc7979'>THE CHANGE IN THE TOTAL LOSS PERCENTAGE FOR " + tableDataWithColumns.get(i).getCountry().toUpperCase() + " BETWEEN " + startYear + " AND " + endYear + " WAS +" + String.format("%.3f", difference) + "%</td></tr>";
                        }
  
                      }
                      else {
                        html += "<tr><td colspan='100' class='message' style='color: #fab802'>INSUFFICIENT DATA TO CALCULATE THE CHANGE IN TOTAL LOSS PERCENTAGE FOR " + tableDataWithColumns.get(i).getCountry().toUpperCase() + " BETWEEN " + startYear + " AND " + endYear + "</td></tr>";
                      }
                    }
                  }

                  if (i == tableDataWithColumns.size() - 1) {
                    ArrayList<Double> differenceValues = jdbc.getDifference(tableDataWithColumns.get(i).getCountry(), Integer.valueOf(startYear), Integer.valueOf(endYear));
                    if(differenceValues.size() == 2) {
                      double difference = differenceValues.get(1) - differenceValues.get(0);
                      if (difference <= 0) {
                        html += "<tr><td colspan='100' class='message' style='color: #32a85a'>THE CHANGE IN THE TOTAL LOSS PERCENTAGE FOR " + tableDataWithColumns.get(i).getCountry().toUpperCase() + " BETWEEN " + startYear + " AND " + endYear + " WAS " + String.format("%.3f", difference) + "%</td></tr>";
                      }
                      else {
                        html += "<tr><td colspan='100' class='message' style='color: #fc7979'>THE CHANGE IN THE TOTAL LOSS PERCENTAGE FOR " + tableDataWithColumns.get(i).getCountry().toUpperCase() + " BETWEEN " + startYear + " AND " + endYear + " WAS +" + String.format("%.3f", difference) + "%</td></tr>";
                      }

                    }
                    else {
                      html += "<tr><td colspan='100' class='message' style='color: #fab802'>INSUFFICIENT DATA TO CALCULATE THE CHANGE IN TOTAL LOSS PERCENTAGE FOR " + tableDataWithColumns.get(i).getCountry().toUpperCase() + " BETWEEN " + startYear + " AND " + endYear + "</td></tr>";
                    }
                  }

            }

          html += "</tbody>";
          html += "</table>";
          html += "</div>";
            
          }

        }
        else if (isValid && allYears.equals("on")) {
          ArrayList<Loss> tableData = jdbc.getTableDataOn(chosenCountries, startYear, endYear, order);
          if ((!tableData.isEmpty()) && (additionalColumns.isEmpty())) {
            html += "<div class='tableContainer'>";
            html += """
              <table class='contentTable'>
              <thead>
                <tr>

                  <th>COUNTRY</th>
                  <th>YEAR</th>
                  <th>AVG LOSS %</th>
                </tr>
              </thead>
              <tbody>
              """;
            for (int i = 0; i < tableData.size(); ++i) {
              html += """
                  <tr>
                    
                    <td>
                    """ + tableData.get(i).getCountry() + """
                    </td>
                    <td>
                    """ + tableData.get(i).getYear() + """
                    </td>
                    <td>
                    """ + String.format("%.3f", tableData.get(i).getAverage()) + """
                    </td>
                  </tr>
                  """;

                  if (i < tableData.size() - 1) {
                    if (tableData.get(i).getCountry().equals(tableData.get(i + 1).getCountry())) {
                      continue;
                    }
                    else {
                      ArrayList<Double> differenceValues = jdbc.getDifference(tableData.get(i).getCountry(), Integer.valueOf(startYear), Integer.valueOf(endYear));
                      if(differenceValues.size() == 2) {
                        double difference = differenceValues.get(1) - differenceValues.get(0);
                        if (difference <= 0) {
                          html += "<tr><td colspan='100' class='message' style='color: #32a85a'>THE CHANGE IN THE TOTAL LOSS PERCENTAGE FOR " + tableData.get(i).getCountry().toUpperCase() + " BETWEEN " + startYear + " AND " + endYear + " WAS " + String.format("%.3f", difference) + "%</td></tr>";
                        }
                        else {
                          html += "<tr><td colspan='100' class='message' style='color: #fc7979'>THE CHANGE IN THE TOTAL LOSS PERCENTAGE FOR " + tableData.get(i).getCountry().toUpperCase() + " BETWEEN " + startYear + " AND " + endYear + " WAS +" + String.format("%.3f", difference) + "%</td></tr>";
                        }
  
                      }
                      else {
                        html += "<tr><td colspan='100' class='message' style='color: #fab802'>INSUFFICIENT DATA TO CALCULATE THE CHANGE IN TOTAL LOSS PERCENTAGE FOR " + tableData.get(i).getCountry().toUpperCase() + " BETWEEN " + startYear + " AND " + endYear + "</td></tr>";
                      }
                    }
                  }

                  if (i == tableData.size() - 1) {
                    ArrayList<Double> differenceValues = jdbc.getDifference(tableData.get(i).getCountry(), Integer.valueOf(startYear), Integer.valueOf(endYear));
                    if(differenceValues.size() == 2) {
                      double difference = differenceValues.get(1) - differenceValues.get(0);
                      if (difference <= 0) {
                        html += "<tr><td colspan='100' class='message' style='color: #32a85a'>THE CHANGE IN THE TOTAL LOSS PERCENTAGE FOR " + tableData.get(i).getCountry().toUpperCase() + " BETWEEN " + startYear + " AND " + endYear + " WAS " + String.format("%.3f", difference) + "%</td></tr>";
                      }
                      else {
                        html += "<tr><td colspan='100' class='message' style='color: #fc7979'>THE CHANGE IN THE TOTAL LOSS PERCENTAGE FOR " + tableData.get(i).getCountry().toUpperCase() + " BETWEEN " + startYear + " AND " + endYear + " WAS +" + String.format("%.3f", difference) + "%</td></tr>";
                      }

                    }
                    else {
                      html += "<tr><td colspan='100' class='message' style='color: #fab802'>INSUFFICIENT DATA TO CALCULATE THE CHANGE IN TOTAL LOSS PERCENTAGE FOR " + tableData.get(i).getCountry().toUpperCase() + " BETWEEN " + startYear + " AND " + endYear + "</td></tr>";
                    } 
                  }

            }
          html += "</tbody>";
          html += "</table>";
          html += "</div>";
          }
          else if (tableData.isEmpty()) {
            html += "<div class='tableContainer'>";
            html += "<a style='color: #fc7979'><b>SORRY, THERE IS NO DATA AVAILABLE FOR THIS SELECTION!</b></a>";
            html += "</div>";
          }

          else if ((!tableData.isEmpty()) && (!additionalColumns.isEmpty())) {
            ArrayList<Loss> tableDataWithColumns = jdbc.getTableDataOnColumns(chosenCountries, startYear, endYear, order, additionalColumns);
            html += "<div class='tableContainer'>";
            html += """
              <table class='contentTable'>
              <thead>
                <tr>

                  <th>COUNTRY</th>
                  <th>YEAR</th>
                 
              """;

              for (int i = 0; i < additionalColumns.size(); ++i) {
                if (additionalColumns.get(i).equals("commodity")) {
                  html += "<th>COMMODITY</th>";
                }
                if (additionalColumns.get(i).equals("food_supply_stage")) {
                  html += "<th>SUPPLY STAGE</th>";
                }
                if (additionalColumns.get(i).equals("activity")) {
                  html += "<th>ACTIVITY</th>";
                }
                if (additionalColumns.get(i).equals("cause_of_loss")) {
                  html += "<th>CAUSE OF LOSS</th>";
                }
              }
            html += " <th>AVG LOSS %</th></tr></thead><tbody>";

            for (int i = 0; i < tableDataWithColumns.size(); ++i) {
              html += """
                  <tr>
                    
                    <td>
                    """ + tableDataWithColumns.get(i).getCountry() + """
                    </td>
                    <td>
                    """ + tableDataWithColumns.get(i).getYear() + """
                    </td>
          

                  """;
                  for (int j = 0; j < additionalColumns.size(); ++j) {
                    if (additionalColumns.get(j).equals("commodity")) {
                      html += "<td>" + tableDataWithColumns.get(i).getCommodity() + "</td>";
                    }
                    if (additionalColumns.get(j).equals("food_supply_stage")) {
                      html += "<td>" + tableDataWithColumns.get(i).getFood_supply_stage() + "</td>";
                    }
                    if (additionalColumns.get(j).equals("activity")) {
                      html += "<td>" + tableDataWithColumns.get(i).getActivity() + "</td>";
                    }
                    if (additionalColumns.get(j).equals("cause_of_loss")) {
                      html += "<td>" + tableDataWithColumns.get(i).getCause_of_loss() + "</td>";
                    }
                  }
    
                  html += "<td>" + String.format("%.3f", tableDataWithColumns.get(i).getAverage()) + "</td></tr>";

                  if (i < tableDataWithColumns.size() - 1) {
                    if ((tableDataWithColumns.get(i).getCountry().equals(tableDataWithColumns.get(i + 1).getCountry())) && (tableDataWithColumns.get(i).getYear() == (tableDataWithColumns.get(i + 1).getYear()))) {
                      continue;
                    }
                    else {
                      double totalLossPercentage = jdbc.getTotalLossPercentage(tableDataWithColumns.get(i).getCountry(), tableDataWithColumns.get(i).getYear());
                      html += "<tr><td colspan='100' class='message'>THE TOTAL AVERAGE LOSS PERCENTAGE FOR " + tableDataWithColumns.get(i).getCountry().toUpperCase() + " IN " + tableDataWithColumns.get(i).getYear() + " WAS " + String.format("%.3f", totalLossPercentage) + "%</td></tr>";
                    }
                  }

                  if (i == tableDataWithColumns.size() - 1) {
                    double totalLossPercentageLast = jdbc.getTotalLossPercentage(tableDataWithColumns.get(i).getCountry(), tableDataWithColumns.get(i).getYear());
                    html += "<tr><td colspan='100' class='message'>THE TOTAL AVERAGE LOSS PERCENTAGE FOR " + tableDataWithColumns.get(i).getCountry().toUpperCase() + " IN " + tableDataWithColumns.get(i).getYear() + " WAS " + String.format("%.3f", totalLossPercentageLast) + "%</td></tr>";
                  }




                  if (i < tableDataWithColumns.size() - 1) {
                    if (tableDataWithColumns.get(i).getCountry().equals(tableDataWithColumns.get(i + 1).getCountry())) {
                      continue;
                    }
                    else {
                      ArrayList<Double> differenceValues = jdbc.getDifference(tableDataWithColumns.get(i).getCountry(), Integer.valueOf(startYear), Integer.valueOf(endYear));
                      if(differenceValues.size() == 2) {
                        double difference = differenceValues.get(1) - differenceValues.get(0);
                        if (difference <= 0) {
                          html += "<tr><td colspan='100' class='message' style='color: #32a85a'>THE CHANGE IN THE TOTAL LOSS PERCENTAGE FOR " + tableDataWithColumns.get(i).getCountry().toUpperCase() + " BETWEEN " + startYear + " AND " + endYear + " WAS " + String.format("%.3f", difference) + "%</td></tr>";
                        }
                        else {
                          html += "<tr><td colspan='100' class='message' style='color: #fc7979'>THE CHANGE IN THE TOTAL LOSS PERCENTAGE FOR " + tableDataWithColumns.get(i).getCountry().toUpperCase() + " BETWEEN " + startYear + " AND " + endYear + " WAS +" + String.format("%.3f", difference) + "%</td></tr>";
                        }
  
                      }
                      else {
                        html += "<tr><td colspan='100' class='message' style='color: #fab802'>INSUFFICIENT DATA TO CALCULATE THE CHANGE IN TOTAL LOSS PERCENTAGE FOR " + tableDataWithColumns.get(i).getCountry().toUpperCase() + " BETWEEN " + startYear + " AND " + endYear + "</td></tr>";
                      }
                    }
                  }

                  if (i == tableDataWithColumns.size() - 1) {
                    ArrayList<Double> differenceValues = jdbc.getDifference(tableDataWithColumns.get(i).getCountry(), Integer.valueOf(startYear), Integer.valueOf(endYear));
                    if(differenceValues.size() == 2) {
                      double difference = differenceValues.get(1) - differenceValues.get(0);
                      if (difference <= 0) {
                        html += "<tr><td colspan='100' class='message' style='color: #32a85a'>THE CHANGE IN THE TOTAL LOSS PERCENTAGE FOR " + tableDataWithColumns.get(i).getCountry().toUpperCase() + " BETWEEN " + startYear + " AND " + endYear + " WAS " + String.format("%.3f", difference) + "%</td></tr>";
                      }
                      else {
                        html += "<tr><td colspan='100' class='message' style='color: #fc7979'>THE CHANGE IN THE TOTAL LOSS PERCENTAGE FOR " + tableDataWithColumns.get(i).getCountry().toUpperCase() + " BETWEEN " + startYear + " AND " + endYear + " WAS +" + String.format("%.3f", difference) + "%</td></tr>";
                      }

                    }
                    else {
                      html += "<tr><td colspan='100' class='message' style='color: #fab802'>INSUFFICIENT DATA TO CALCULATE THE CHANGE IN TOTAL LOSS PERCENTAGE FOR " + tableDataWithColumns.get(i).getCountry().toUpperCase() + " BETWEEN " + startYear + " AND " + endYear + "</td></tr>";
                    }
                  }
                  
              }

          html += "</tbody>";
          html += "</table>";
          html += "</div>";

          }
          
        }

        //close tableContainer div
        // html += "</div>";
        //close mainContent div
        html += "</div>";

        // Close Content div
        html = html + "</div>";

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";
        
        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }
}

