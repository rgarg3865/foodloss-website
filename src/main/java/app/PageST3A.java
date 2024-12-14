package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class PageST3A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page3A.html";

    @Override
    public void handle(Context context) throws Exception {
        JDBCConnection jdbc = new JDBCConnection();
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>COUNTRY SIMILARITY TOOL</title>" +
               "<link rel='icon' type='image/png' href='weblogo.png'>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "<link href='https://unpkg.com/css.gg@2.0.0/icons/css/chevron-double-up-r.css' rel='stylesheet'>";
        html += """
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script>
            $(document).ready(function(){
                $("#countryDiv3a").show();
                $("#regionDiv3a").hide();
                $(".countryButton").css("color", "#fab802");
                $("#valuesFilter").hide();
                $(".countryButton").click(function(){
                    $("#countryDiv3a").show();
                    $("#regionDiv3a").hide();
                    $(".both").show();
                    $(".region").prop('checked', false);
                    $(".countryButton").css("color", "#fab802");
                    $(".regionButton").css("color", "black");
                });
                $(".regionButton").click(function(){
                    $("#regionDiv3a").show();
                    $("#countryDiv3a").hide();
                    $(".both").hide();
                    $(".both").prop('checked', false);
                    $(".country").prop('checked', false);
                    $(".countryButton").css("color", "black");
                    $(".regionButton").css("color", "#fab802");
                });
                $(".food").click(function(){
                    $("#valuesFilter").show();
                });
                $(".percentage").click(function(){
                    $("#valuesFilter").hide();
                    $("#absolute").prop('checked', false);
                    $("#overlap").prop('checked', false);
                });
                $(".both").click(function(){
                    $("#valuesFilter").hide();
                    $("#absolute").prop('checked', false);
                    $("#overlap").prop('checked', false);
                });
            });
        </script>
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

        html = html + """
            <div class='header3a' style='display: flex; flex-direction: column; justify-content: center; align-items: center; font-size: 20px; margin-bottom: 20px'>
                <p style='margin-bottom: 0px'><b>GEOGRAPHIC LOCATION SIMILARITY TOOL</b></p>
                <p style='margin-top: 5px; font-size: 0.75em; color: rgba(255, 255, 255, 0.6)'>Choose a location, a year and find similar locations in the same year, sorted by most to least similar</p>
            </div>
        """;

        // Add Div for page Content
        html += """
                <div class='content3a'>
                    <div class='sideContent3a'>
                        <form action='/page3A.html' method='post' id='containerCountryPage3a'>
                            <div style='text-align: center; margin-bottom: 5px; z-index: 999'>
                                <a>CHOOSE A GEOGRAPHIC LOCATION BY</a>
                            </div>
                            <div style='display: inline-flex; justify-content: center; z-index: 999; text-align: center; margin-bottom: 0px'>
                                <a class='countryButton' href='/page3A.html#countryDiv3a/' style='margin-right: 30px'><b>COUNTRY</b></a>
                                <a>OR</a>
                                <a class='regionButton' href='/page3A.html#regionDiv3a/' style='margin-left: 30px'><b>REGION</b></a>
                            </div>
                            <div id='countryDiv3a'>           
                """;

        ArrayList<String> countriesList = jdbc.getCountriesList();

        for (int i = 0 ; i < countriesList.size() ; ++i) {
            html += "<input class='country' type='radio' id='" + countriesList.get(i) + "' name='country_radio' value='" + countriesList.get(i) + "'>";
            html += "<label for='" + countriesList.get(i) + "'>" + countriesList.get(i) + "</label><br>";
        }

        html += "</div>";
        html += "<div id='regionDiv3a'>";

        //  <div id='regionDiv3a'>
        //      <a>Region Test</a>
        //  </div>
        
        ArrayList<String> countriesWithRegions = jdbc.getCountriesWithRegions();
        ArrayList<Loss> regions = jdbc.getRegionsList();

        for (int i = 0; i < countriesWithRegions.size(); ++i) {
            html += "<a><b>" + countriesWithRegions.get(i) + ":</b></a><br>";
            for (int j = 0; j < regions.size(); ++j) {
                if (regions.get(j).getCountry().equals(countriesWithRegions.get(i))) {
                    html += "<input class='region' type='radio' id='" + regions.get(j).getRegion() + ":" + regions.get(j).getCountry() + "' name='region_radio' value='" + regions.get(j).getRegion() + ":" + regions.get(j).getCountry() + "'>";
                    html += "<label for='" + regions.get(j).getRegion() + ":" + regions.get(j).getCountry() + "'>" + regions.get(j).getRegion() + "</label><br>";
                }
            }
        }

        html += "</div>";

        html += """
                <div class='form-group3a'>
                    <label for='year_drop'><b>CHOOSE A YEAR:</b></label>
                    <select id='year_drop' name='year_drop' style='border-radius: 7px; width: 30%;'>
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
        // <span class='hintText' style='margin-left: -180px; width: 275px;'>Must pick a country to use this option</span>
        html += """
                <div style='margin-bottom: 0px' class='form-group3a'>
                    <a><b>SELECT A BASIS OF SIMILARITY:</b></a><br>
                    <input class='food' type='radio' id='food' name='similarity_radio' value='food'>
                    <label class='food' for='food'>COMMON FOOD PRODUCTS</label><br>
                    <input class='percentage' type='radio' id='percentage' name='similarity_radio' value='percentage'>
                    <label class='percentage' for='percentage'>SIMILAR FOOD LOSS PERCENTAGE</label><br>
                    <input type='radio' class='both' id='both' name='similarity_radio' value='both'>
                    <label class='both hint' for='both'>BOTH OF THE ABOVE<span class='hintText' style='width:285px; left: 250px'>Compares the average loss percentage of the common food products between the chosen and displayed country</span></label>
                </div>
                """;

        html += """
                <div id='valuesFilter' style='margin-bottom: 0px' class='form-group3a'>
                    <a><b>CALCULATE SIMILARITY BY:</b></a><br>
                    <input type='radio' id='absolute' name='values_radio' value='absolute'>
                    <label class='hint' for='absolute'>ABSOLUTE VALUES<span class='hintText' style='width:285px; left: 250px'>Finds the exact number of common food products between the chosen and displayed location <b>(INTERSECTION)</b></span></label><br>
                    <input type='radio' id='overlap' name='values_radio' value='overlap'>
                    <label class='hint' for='overlap'>PERCENTAGE OVERLAP<span class='hintText' style='width:285px; left: 250px'>Divides the absolute value by the total unique food products between the chosen and displayed location <b>(INTERSECTION/UNION)</b></span></label><br>
                </div>
                """;

        html += """
                <div class='form-group3a'>
                    <label for='num_results'><b>INPUT NUMBER OF RESULTS:</b></label>
                    <input style='border-radius: 7px; width: 60px' type='number' id='num_results' name='num_results' min='1' max='272' required>
                </div>
                """; 
                
        html = html + "<button type='submit' class='submitCountryPage'>SEARCH FOR SIMILAR DATA</button>";

        // Close form
        html += "</form>";
        html+="<div class='backToTop'><a href='#top'><div class='iconAndText'><i style='margin-right: 7px;' class='gg-chevron-double-up-r'></i><span>BACK TO TOP</span></div></a></div>";
        // Close sideContent3a
        html += "</div>";

        String chosenLocation = "";
        String chosenYear = context.formParam("year_drop");
        String similarityBasis = context.formParam("similarity_radio");
        String similarityValues = context.formParam("values_radio");
        String numResults = context.formParam("num_results");

        html  += "<div class='mainContent3a'>";
        html += "<div class='inputMessage3a'>";

        if ((similarityBasis == null)) {
            html += "<a style='color: #fc7979;'><b>PLEASE COMPLETE EVERY FILTER TO SEE DATA.</b></a></div></div>";
        }

        else if (similarityBasis.equals("food")) {
            if (((context.formParam("region_radio") == null) && (context.formParam("country_radio") == null)) || (chosenYear.equals("SELECT")) || (chosenYear == null) || (similarityValues == null)) {
                html += "<a style='color: #fc7979;'><b>PLEASE COMPLETE EVERY FILTER TO SEE DATA.</b></a></div></div>";
            }
            else if ((context.formParam("region_radio") == null) && (similarityValues.equals("absolute"))) {
                chosenLocation = context.formParam("country_radio");
                if (Integer.valueOf(numResults) == 1) {
                    html += "<a><b><a style='color: #fab802'>YOUR SELECTION: " + chosenLocation + " " + chosenYear + " | </a></b>Searching for the most similar country in terms of common food products</a>";    
                }
                else {
                    html += "<a><b><a style='color: #fab802'>YOUR SELECTION: " + chosenLocation + " " + chosenYear + " | </a></b>Searching for the top " + numResults + " most similar countries in terms of common food products</a>";
                }
                ArrayList<String> countryCommodities = jdbc.getCountryCommodities(chosenLocation, chosenYear);
                if (!countryCommodities.isEmpty()) {

                    // Need to end inputmessage div first
                    html += """
                            <br><br>
                            <a><b><a style='color: #fab802'>FOOD PRODUCTS LOST: (""";
                    
                    html += countryCommodities.size() + " total) | </a></b>";

                    for (int i = 0; i < countryCommodities.size(); ++i) {
                        if (i == (countryCommodities.size() - 1)) {
                            html += countryCommodities.get(i);
                        }
                        else {
                            html += countryCommodities.get(i) + " | ";
                        }
                    }
                    
                    html += "</a>";

                    // System.out.println(countryCommodities);
                    ArrayList<Loss> similarFoodAbsoluteCount = jdbc.getSimilarFoodAbsoluteCount(countryCommodities, chosenLocation, chosenYear);
                    // for (int i = 0; i < similarFoodAbsoluteCount.size(); ++i) {
                    //     System.out.println(similarFoodAbsoluteCount.get(i).getCount() + ":" + similarFoodAbsoluteCount.get(i).getCountry());
                    // }

                    if (!similarFoodAbsoluteCount.isEmpty()) {
                        html += """
                            </div>
                            <div class='tableContainer'>
                                <table class='contentTable'>
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>COUNTRY</th>
                                            <th>COMMON FOOD PRODUCTS</th>
                                            <th>ABSOLUTE VALUE</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                            """;
                        
                        int limit = 0;

                        if (similarFoodAbsoluteCount.size() > Integer.valueOf(numResults)) {
                            limit = Integer.valueOf(numResults);
                        }
                        else {
                            limit = similarFoodAbsoluteCount.size();
                        }
                        // System.out.println(limit);
                        for (int i = 0; i < limit; ++i) {
                            html += """
                                    <tr>
                                        <td>
                                    """;
                        
                            html += (i + 1) + "</td><td>";
                        
                            html += similarFoodAbsoluteCount.get(i).getCountry() + "</td><td>";
                            ArrayList<String> printCommodities = jdbc.getSimilarCountryCommodities(countryCommodities, similarFoodAbsoluteCount.get(i).getCountry(), chosenYear);
                            for (int j = 0; j < printCommodities.size(); ++j) {
                                if (j == (printCommodities.size() - 1)) {
                                    html += printCommodities.get(j) + "</td><td>";
                                }
                                else {
                                    html += printCommodities.get(j) + " | ";
                                }
                            }
                            html += similarFoodAbsoluteCount.get(i).getCount() + "</td></tr>";
                        }

                        html += "</tbody></table></div></div>";

                    }
                    else {
                        html += "<br><br><a style='color: #fc7979'><b>THERE IS NO SIMILAR FOOD LOSS DATA AVAILABLE FOR ANY OTHER COUNTRIES WITHIN THE CHOSEN YEAR, PLEASE TRY A DIFFERENT COMBINATION TO SEE SIMILARITY DATA!</b></a></div></div>";
                    }
                }
                else {
                    html += "<br><br><a style='color: #fc7979;'><b>THE CURRENT SELECTION HAS NO FOOD LOSS DATA AVAILABLE, PLEASE TRY A DIFFERENT COMBINATION!</b></a></div></div>";
                }
            }


            
            
            // REGION ABSOLUTE
            
            
            else if ((context.formParam("country_radio") == null) && (similarityValues.equals("absolute"))) {
                
                chosenLocation = context.formParam("region_radio");
                System.out.println(chosenLocation);
                int colon = chosenLocation.indexOf(':');
                String chosenLocationCountry = chosenLocation.substring(colon + 1, chosenLocation.length());
                System.out.println(chosenLocationCountry);
                chosenLocation = chosenLocation.substring(0, colon);
                System.out.println(chosenLocation);

                if (Integer.valueOf(numResults) == 1) {
                    html += "<a><b><a style='color: #fab802'>YOUR SELECTION: " + chosenLocation + " (in " + chosenLocationCountry + "), " + chosenYear + " | </a></b>Searching for the most similar region in terms of common food products</a>";    
                }
                else {
                    html += "<a><b><a style='color: #fab802'>YOUR SELECTION: " + chosenLocation + " (in " + chosenLocationCountry + "), " + chosenYear + " | </a></b>Searching for the top " + numResults + " most similar regions in terms of common food products</a>";
                }
                ArrayList<String> regionCommodities = jdbc.getRegionCommodities(chosenLocation, chosenLocationCountry, chosenYear);
                System.out.println(regionCommodities);
                if (!regionCommodities.isEmpty()) {

                    // Need to end inputmessage div first
                    html += """
                            <br><br>
                            <a><b><a style='color: #fab802'>FOOD PRODUCTS LOST: (""";
                    
                    html += regionCommodities.size() + " total) | </a></b>";

                    for (int i = 0; i < regionCommodities.size(); ++i) {
                        if (i == (regionCommodities.size() - 1)) {
                            html += regionCommodities.get(i);
                        }
                        else {
                            html += regionCommodities.get(i) + " | ";
                        }
                    }
                    
                    html += "</a>";

                    // System.out.println(countryCommodities);
                    ArrayList<Loss> similarFoodAbsoluteCountRegion = jdbc.getSimilarFoodAbsoluteCountRegion(regionCommodities, chosenLocation, chosenLocationCountry, chosenYear);
                    // for (int i = 0; i < similarFoodAbsoluteCount.size(); ++i) {
                    //     System.out.println(similarFoodAbsoluteCount.get(i).getCount() + ":" + similarFoodAbsoluteCount.get(i).getCountry());
                    // }

                    if (!similarFoodAbsoluteCountRegion.isEmpty()) {
                        html += """
                            </div>
                            <div class='tableContainer'>
                                <table class='contentTable'>
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>REGION</th>
                                            <th>COUNTRY</th>
                                            <th>COMMON FOOD PRODUCTS</th>
                                            <th>ABSOLUTE VALUE</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                            """;
                        
                        int limit = 0;

                        if (similarFoodAbsoluteCountRegion.size() > Integer.valueOf(numResults)) {
                            limit = Integer.valueOf(numResults);
                        }
                        else {
                            limit = similarFoodAbsoluteCountRegion.size();
                        }
                        // System.out.println(limit);
                        for (int i = 0; i < limit; ++i) {
                            html += """
                                    <tr>
                                        <td>
                                    """;
                        
                            html += (i + 1) + "</td><td>";
                        
                            html += similarFoodAbsoluteCountRegion.get(i).getRegion() + "</td><td>";
                            html += similarFoodAbsoluteCountRegion.get(i).getCountry() + "</td><td>";
                            ArrayList<String> printCommodities = jdbc.getSimilarRegionCommodities(regionCommodities, similarFoodAbsoluteCountRegion.get(i).getRegion(), similarFoodAbsoluteCountRegion.get(i).getCountry(), chosenYear);
                            for (int j = 0; j < printCommodities.size(); ++j) {
                                if (j == (printCommodities.size() - 1)) {
                                    html += printCommodities.get(j) + "</td><td>";
                                }
                                else {
                                    html += printCommodities.get(j) + " | ";
                                }
                            }
                            html += similarFoodAbsoluteCountRegion.get(i).getCount() + "</td></tr>";
                        }

                        html += "</tbody></table></div></div>";

                    }
                    else {
                        html += "<br><br><a style='color: #fc7979'><b>THERE IS NO SIMILAR FOOD LOSS DATA AVAILABLE FOR ANY OTHER REGIONS WITHIN THE CHOSEN YEAR, PLEASE TRY A DIFFERENT COMBINATION TO SEE SIMILARITY DATA!</b></a></div></div>";
                    }
                }
                else {
                    html += "<br><br><a style='color: #fc7979;'><b>THE CURRENT SELECTION HAS NO FOOD LOSS DATA AVAILABLE, PLEASE TRY A DIFFERENT COMBINATION!</b></a></div></div>";
                }
            }


            

            else if ((context.formParam("region_radio") == null) && (similarityValues.equals("overlap"))) {
                chosenLocation = context.formParam("country_radio");
                if (Integer.valueOf(numResults) == 1) {
                    html += "<a><b><a style='color: #fab802'>YOUR SELECTION: " + chosenLocation + " " + chosenYear + " | </a></b>Searching for the most similar country in terms of common food products</a>";    
                }
                else {
                    html += "<a><b><a style='color: #fab802'>YOUR SELECTION: " + chosenLocation + " " + chosenYear + " | </a></b>Searching for the top " + numResults + " most similar countries in terms of common food products</a>";
                }
                ArrayList<String> countryCommodities = jdbc.getCountryCommodities(chosenLocation, chosenYear);
                if (!countryCommodities.isEmpty()) {

                    // Need to end inputmessage div first
                    html += """
                            <br><br>
                            <a><b><a style='color: #fab802'>FOOD PRODUCTS LOST: (""";
                    
                    html += countryCommodities.size() + " total) | </a></b>";

                    for (int i = 0; i < countryCommodities.size(); ++i) {
                        if (i == (countryCommodities.size() - 1)) {
                            html += countryCommodities.get(i);   
                        }
                        else {
                            html += countryCommodities.get(i) + " | ";
                        }
                    }
                    
                    html += "</a>";

                    // System.out.println(countryCommodities);
                    ArrayList<Loss> similarFoodAbsoluteCount = jdbc.getSimilarFoodAbsoluteCount(countryCommodities, chosenLocation, chosenYear);
                    // for (int i = 0; i < similarFoodAbsoluteCount.size(); ++i) {
                    //     System.out.println(similarFoodAbsoluteCount.get(i).getCount() + ":" + similarFoodAbsoluteCount.get(i).getCountry());
                    // }

                    if (!similarFoodAbsoluteCount.isEmpty()) {
                        html += """
                            </div>
                            <div class='tableContainer'>
                                <table class='contentTable'>
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>COUNTRY</th>
                                            <th>COMMON FOOD PRODUCTS</th>
                                            <th>TOTAL</th>
                                            <th>% OVERLAP</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                            """;

                        for (int i = 0; i < similarFoodAbsoluteCount.size(); ++i) {
                            similarFoodAbsoluteCount.get(i).setTotal(jdbc.getUnion(similarFoodAbsoluteCount.get(i).getCountry(), chosenLocation, chosenYear));
                            System.out.println(similarFoodAbsoluteCount.get(i).getCount() + ":" + similarFoodAbsoluteCount.get(i).getCountry() + ":" + similarFoodAbsoluteCount.get(i).getTotal());
                        }

                        selectionSort(similarFoodAbsoluteCount);
                        
                        int limit = 0;

                        if (similarFoodAbsoluteCount.size() > Integer.valueOf(numResults)) {
                            limit = Integer.valueOf(numResults);
                        }
                        else {
                            limit = similarFoodAbsoluteCount.size();
                        }
                        System.out.println(limit);

                        for (int i = 0; i < limit; ++i) {
                            html += """
                                    <tr>
                                        <td>
                                    """;
                            
                            html += (i + 1) + "</td><td>";
                            html += similarFoodAbsoluteCount.get(i).getCountry() + "</td><td>";

                            ArrayList<String> printCommodities = jdbc.getSimilarCountryCommodities(countryCommodities, similarFoodAbsoluteCount.get(i).getCountry(), chosenYear);
                            
                            for (int j = 0; j < printCommodities.size(); ++j) {
                                if (j == (printCommodities.size() - 1)) {
                                    if (printCommodities.size() == 1) {
                                        html += printCommodities.get(j) + "<br><a style='color: #fab802'><b>(" + printCommodities.size() + " match)</a></b></td><td>";
                                    }
                                    else {
                                        html += printCommodities.get(j) + "<br><a style='color: #fab802'><b>(" + printCommodities.size() + " matches)</a></b></td><td>";
                                    }
                                }
                                else {
                                    html += printCommodities.get(j) + " | ";
                                }
                            }

                            html += String.format("%.0f", similarFoodAbsoluteCount.get(i).getTotal()) + "</td><td>";

                            html += String.format("%.3f", similarFoodAbsoluteCount.get(i).getOverlap()) + "</td></tr>";
                        }

                        html += "</tbody></table></div></div>";
                    }
                    else {
                        html += "<br><br><a style='color: #fc7979'><b>THERE IS NO SIMILAR FOOD LOSS DATA AVAILABLE FOR ANY OTHER COUNTRIES WITHIN THE CHOSEN YEAR, PLEASE TRY A DIFFERENT COMBINATION TO SEE SIMILARITY DATA!</b></a></div></div>";
                    }
                }
                else {
                    html += "<br><br><a style='color: #fc7979;'><b>THE CURRENT SELECTION HAS NO FOOD LOSS DATA AVAILABLE, PLEASE TRY A DIFFERENT COMBINATION!</b></a></div></div>";
                }
            }




            
            
            // REGION OVERLAP

            else if ((context.formParam("country_radio") == null) && (similarityValues.equals("overlap"))) {
                chosenLocation = context.formParam("region_radio");
                System.out.println(chosenLocation);
                int colon = chosenLocation.indexOf(':');
                String chosenLocationCountry = chosenLocation.substring(colon + 1, chosenLocation.length());
                System.out.println(chosenLocationCountry);
                chosenLocation = chosenLocation.substring(0, colon);
                System.out.println(chosenLocation);

                if (Integer.valueOf(numResults) == 1) {
                    html += "<a><b><a style='color: #fab802'>YOUR SELECTION: " + chosenLocation + " (in " + chosenLocationCountry + "), " + chosenYear + " | </a></b>Searching for the most similar region in terms of common food products</a>";    
                }
                else {
                    html += "<a><b><a style='color: #fab802'>YOUR SELECTION: " + chosenLocation + " (in " + chosenLocationCountry + "), " + chosenYear + " | </a></b>Searching for the top " + numResults + " most similar regions in terms of common food products</a>";
                }

                ArrayList<String> regionCommodities = jdbc.getRegionCommodities(chosenLocation, chosenLocationCountry, chosenYear);
                if (!regionCommodities.isEmpty()) {

                    // Need to end inputmessage div first
                    html += """
                            <br><br>
                            <a><b><a style='color: #fab802'>FOOD PRODUCTS LOST: (""";
                    
                    html += regionCommodities.size() + " total) | </a></b>";

                    for (int i = 0; i < regionCommodities.size(); ++i) {
                        if (i == (regionCommodities.size() - 1)) {
                            html += regionCommodities.get(i);   
                        }
                        else {
                            html += regionCommodities.get(i) + " | ";
                        }
                    }
                    
                    html += "</a>";

                    // System.out.println(countryCommodities);
                    ArrayList<Loss> similarFoodAbsoluteCountRegion = jdbc.getSimilarFoodAbsoluteCountRegion(regionCommodities, chosenLocation, chosenLocationCountry, chosenYear);
                    // for (int i = 0; i < similarFoodAbsoluteCount.size(); ++i) {
                    //     System.out.println(similarFoodAbsoluteCount.get(i).getCount() + ":" + similarFoodAbsoluteCount.get(i).getCountry());
                    // }

                    if (!similarFoodAbsoluteCountRegion.isEmpty()) {
                        html += """
                            </div>
                            <div class='tableContainer'>
                                <table class='contentTable'>
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>REGION</th>
                                            <th>COUNTRY</th>
                                            <th>COMMON FOOD PRODUCTS</th>
                                            <th>TOTAL</th>
                                            <th>% OVERLAP</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                            """;

                        for (int i = 0; i < similarFoodAbsoluteCountRegion.size(); ++i) {
                            similarFoodAbsoluteCountRegion.get(i).setTotal(jdbc.getUnionRegion(similarFoodAbsoluteCountRegion.get(i).getRegion(), similarFoodAbsoluteCountRegion.get(i).getCountry(), chosenLocation, chosenLocationCountry, chosenYear));
                            // System.out.println(similarFoodAbsoluteCountRegion.get(i).getCount() + ":" + similarFoodAbsoluteCountRegion.get(i).getCountry() + ":" + similarFoodAbsoluteCount.get(i).getTotal());
                        }

                        selectionSort(similarFoodAbsoluteCountRegion);
                        
                        int limit = 0;

                        if (similarFoodAbsoluteCountRegion.size() > Integer.valueOf(numResults)) {
                            limit = Integer.valueOf(numResults);
                        }
                        else {
                            limit = similarFoodAbsoluteCountRegion.size();
                        }
                        System.out.println(limit);

                        for (int i = 0; i < limit; ++i) {
                            html += """
                                    <tr>
                                        <td>
                                    """;
                            
                            html += (i + 1) + "</td><td>";
                            html += similarFoodAbsoluteCountRegion.get(i).getRegion() + "</td><td>";
                            html += similarFoodAbsoluteCountRegion.get(i).getCountry() + "</td><td>";

                            ArrayList<String> printCommodities = jdbc.getSimilarRegionCommodities(regionCommodities, similarFoodAbsoluteCountRegion.get(i).getRegion(), similarFoodAbsoluteCountRegion.get(i).getCountry(), chosenYear);
                            
                            for (int j = 0; j < printCommodities.size(); ++j) {
                                if (j == (printCommodities.size() - 1)) {
                                    if (printCommodities.size() == 1) {
                                        html += printCommodities.get(j) + "<br><a style='color: #fab802'><b>(" + printCommodities.size() + " match)</a></b></td><td>";
                                    }
                                    else {
                                        html += printCommodities.get(j) + "<br><a style='color: #fab802'><b>(" + printCommodities.size() + " matches)</a></b></td><td>";
                                    }
                                }
                                else {
                                    html += printCommodities.get(j) + " | ";
                                }
                            }

                            html += String.format("%.0f", similarFoodAbsoluteCountRegion.get(i).getTotal()) + "</td><td>";

                            html += String.format("%.3f", similarFoodAbsoluteCountRegion.get(i).getOverlap()) + "</td></tr>";
                        }

                        html += "</tbody></table></div></div>";
                    }
                    else {
                        html += "<br><br><a style='color: #fc7979'><b>THERE IS NO SIMILAR FOOD LOSS DATA AVAILABLE FOR ANY OTHER COUNTRIES WITHIN THE CHOSEN YEAR, PLEASE TRY A DIFFERENT COMBINATION TO SEE SIMILARITY DATA!</b></a></div></div>";
                    }
                }
                else {
                    html += "<br><br><a style='color: #fc7979;'><b>THE CURRENT SELECTION HAS NO FOOD LOSS DATA AVAILABLE, PLEASE TRY A DIFFERENT COMBINATION!</b></a></div></div>";
                }
            }



        }

        else if (similarityBasis.equals("percentage")) {
            if (((context.formParam("region_radio") == null) && (context.formParam("country_radio") == null)) || (chosenYear.equals("SELECT")) || (chosenYear == null)) {
                html += "<a style='color: #fc7979;'><b>PLEASE COMPLETE EVERY FILTER TO SEE DATA.</b></a></div></div>";
            }
            else if (context.formParam("region_radio") == null) {
                chosenLocation = context.formParam("country_radio");
                if (Integer.valueOf(numResults) == 1) {
                    html += "<a><b><a style='color: #fab802'>YOUR SELECTION: " + chosenLocation + " " + chosenYear + " | </a></b>Searching for the most similar country in terms of average loss percentage</a>";    
                }
                else {
                    html += "<a><b><a style='color: #fab802'>YOUR SELECTION: " + chosenLocation + " " + chosenYear + " | </a></b>Searching for the top " + numResults + " most similar countries in terms of average loss percentage</a>";
                }
                
                double lossPercentage = jdbc.getTotalLossPercentage(chosenLocation, Integer.valueOf(chosenYear));
                if (lossPercentage == 0) {
                    html += "<br><br><a style='color: #fc7979;'><b>THE CURRENT SELECTION HAS NO FOOD LOSS DATA AVAILABLE, PLEASE TRY A DIFFERENT COMBINATION!</b></a></div></div>";
                }
                else {
                    html += "<br><br><a><b><a style='color: #fab802'>AVG LOSS FOR SELECTION: </a></b>" + String.format("%.3f%%", lossPercentage) + "</a>";
                    Loss chosenData = new Loss(chosenLocation, Integer.valueOf(chosenYear), lossPercentage);
                    ArrayList<Loss> countriesInYear = jdbc.getCountriesInYear(chosenLocation, Integer.valueOf(chosenYear));
                    if (countriesInYear.isEmpty()) {
                        html += "<br><br><a style='color: #fc7979'><b>THERE IS NO SIMILAR FOOD LOSS DATA AVAILABLE FOR ANY OTHER COUNTRIES WITHIN THE CHOSEN YEAR, PLEASE TRY A DIFFERENT COMBINATION TO SEE SIMILARITY DATA!</b></a></div></div>";
                    }
                    else {
                        for (int i = 0; i < countriesInYear.size(); ++i) {
                            System.out.println(countriesInYear.get(i).getCountry() + ":" + countriesInYear.get(i).getYear() + ":" + countriesInYear.get(i).getAverage());
                        }

                        ArrayList<Loss> ordered = new ArrayList<Loss>();
                        for (int i = 0; i < countriesInYear.size(); ++i) {;
                            double difference = Math.abs(countriesInYear.get(i).getAverage() - chosenData.getAverage());
                            String country = countriesInYear.get(i).getCountry();
                            double average = countriesInYear.get(i).getAverage();
                            Loss orderingRow = new Loss(difference, country, average);
                            ordered.add(orderingRow);
                        }

                        selectionSortPercentage(ordered);

                        for (int i = 0; i < ordered.size(); ++i) {
                            System.out.println(ordered.get(i).getCountry() + ":" + ordered.get(i).getAvgDifference());
                        }

                        html += """
                            </div>
                            <div class='tableContainer'>
                                <table class='contentTable'>
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>COUNTRY</th>
                                            <th>AVG LOSS %</th>
                                            <th>DIFFERENCE %</th>
                                            <th>SIMILARITY %</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                            """;

                        int limit = 0;

                        if (ordered.size() > Integer.valueOf(numResults)) {
                            limit = Integer.valueOf(numResults);
                        }
                        else {
                            limit = ordered.size();
                        }
                        System.out.println(limit);

                        for (int i = 0; i < limit; ++i) {
                            html += """
                                    <tr>
                                        <td>
                                    """;
                            
                            html += (i + 1) + "</td><td>";
                            html += ordered.get(i).getCountry() + "</td><td>";
                            html += String.format("%.3f", ordered.get(i).getAverage()) + "</td><td>";
                            html += String.format("%.3f", ordered.get(i).getAvgDifference()) + "</td><td>";
                            html += String.format("%.3f", (100.000 - ordered.get(i).getAvgDifference())) + "</td></tr>";
                        }

                        html += "</tbody></table></div></div>";
                    }
                }
            }




            // REGION PERCENTAGE

            else if (context.formParam("country_radio") == null) {
                
                chosenLocation = context.formParam("region_radio");
                System.out.println(chosenLocation);
                int colon = chosenLocation.indexOf(':');
                String chosenLocationCountry = chosenLocation.substring(colon + 1, chosenLocation.length());
                System.out.println(chosenLocationCountry);
                chosenLocation = chosenLocation.substring(0, colon);
                System.out.println(chosenLocation);


                if (Integer.valueOf(numResults) == 1) {
                    html += "<a><b><a style='color: #fab802'>YOUR SELECTION: " + chosenLocation + " (in " + chosenLocationCountry + "), " + chosenYear + " | </a></b>Searching for the most similar region in terms of average loss percentage</a>";    
                }
                else {
                    html += "<a><b><a style='color: #fab802'>YOUR SELECTION: " + chosenLocation + " (in " + chosenLocationCountry + "), " + chosenYear + " | </a></b>Searching for the top " + numResults + " most similar regions in terms of average loss percentage</a>";
                }
                
                double lossPercentage = jdbc.getTotalLossPercentageRegion(chosenLocation, chosenLocationCountry, Integer.valueOf(chosenYear));
                if (lossPercentage == 0) {
                    html += "<br><br><a style='color: #fc7979;'><b>THE CURRENT SELECTION HAS NO FOOD LOSS DATA AVAILABLE, PLEASE TRY A DIFFERENT COMBINATION!</b></a></div></div>";
                }
                else {
                    html += "<br><br><a><b><a style='color: #fab802'>AVG LOSS FOR SELECTION: </a></b>" + String.format("%.3f%%", lossPercentage) + "</a>";
                    Loss chosenData = new Loss(chosenLocation, chosenLocationCountry, Integer.valueOf(chosenYear), lossPercentage);
                    ArrayList<Loss> regionsInYear = jdbc.getRegionsInYear(chosenLocation, chosenLocationCountry, Integer.valueOf(chosenYear));
                    if (regionsInYear.isEmpty()) {
                        html += "<br><br><a style='color: #fc7979'><b>THERE IS NO SIMILAR FOOD LOSS DATA AVAILABLE FOR ANY OTHER REGIONS WITHIN THE CHOSEN YEAR, PLEASE TRY A DIFFERENT COMBINATION TO SEE SIMILARITY DATA!</b></a></div></div>";
                    }
                    else {
                        for (int i = 0; i < regionsInYear.size(); ++i) {
                            System.out.println(regionsInYear.get(i).getRegion() + ":" + regionsInYear.get(i).getCountry() + ":" + regionsInYear.get(i).getYear() + ":" + regionsInYear.get(i).getAverage());
                        }

                        ArrayList<Loss> ordered = new ArrayList<Loss>();
                        for (int i = 0; i < regionsInYear.size(); ++i) {;
                            double difference = Math.abs(regionsInYear.get(i).getAverage() - chosenData.getAverage());
                            String country = regionsInYear.get(i).getCountry();
                            String region = regionsInYear.get(i).getRegion();
                            double average = regionsInYear.get(i).getAverage();
                            Loss orderingRow = new Loss(difference, region, country, average);
                            ordered.add(orderingRow);
                        }

                        selectionSortPercentage(ordered);

                        for (int i = 0; i < ordered.size(); ++i) {
                            System.out.println(ordered.get(i).getRegion() + ":" + ordered.get(i).getCountry() + ":" + ordered.get(i).getAvgDifference());
                        }

                        html += """
                            </div>
                            <div class='tableContainer'>
                                <table class='contentTable'>
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>REGION</th>
                                            <th>COUNTRY</th>
                                            <th>AVG LOSS %</th>
                                            <th>DIFFERENCE %</th>
                                            <th>SIMILARITY %</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                            """;

                        int limit = 0;

                        if (ordered.size() > Integer.valueOf(numResults)) {
                            limit = Integer.valueOf(numResults);
                        }
                        else {
                            limit = ordered.size();
                        }
                        System.out.println(limit);

                        for (int i = 0; i < limit; ++i) {
                            html += """
                                    <tr>
                                        <td>
                                    """;
                            
                            html += (i + 1) + "</td><td>";
                            html += ordered.get(i).getRegion() + "</td><td>";
                            html += ordered.get(i).getCountry() + "</td><td>";
                            html += String.format("%.3f", ordered.get(i).getAverage()) + "</td><td>";
                            html += String.format("%.3f", ordered.get(i).getAvgDifference()) + "</td><td>";
                            html += String.format("%.3f", (100.00 - ordered.get(i).getAvgDifference())) + "</td></tr>";
                        }

                        html += "</tbody></table></div></div>";
                    }
                }
            }

        }
        
        
        // BOTH FILTER

        else if (similarityBasis.equals("both")) {
            if (((context.formParam("region_radio") == null) && (context.formParam("country_radio") == null)) || (chosenYear.equals("SELECT")) || (chosenYear == null)) {
                html += "<a style='color: #fc7979;'><b>PLEASE COMPLETE EVERY FILTER TO SEE DATA.</b></a></div></div>";
            }
            else if (context.formParam("region_radio") == null) {
                chosenLocation = context.formParam("country_radio");
                if (Integer.valueOf(numResults) == 1) {
                    html += "<a><b><a style='color: #fab802'>YOUR SELECTION: " + chosenLocation + " " + chosenYear + " | </a></b>Searching for the most similar country in terms of common food products and their combined average loss percentage</a>";    
                }
                else {
                    html += "<a><b><a style='color: #fab802'>YOUR SELECTION: " + chosenLocation + " " + chosenYear + " | </a></b>Searching for the top " + numResults + " most similar countries in terms of common food products and their combined average loss percentage</a>";
                }
                
                double lossPercentage = jdbc.getTotalLossPercentage(chosenLocation, Integer.valueOf(chosenYear));
                if (lossPercentage == 0) {
                    html += "<br><br><a style='color: #fc7979;'><b>THE CURRENT SELECTION HAS NO FOOD LOSS DATA AVAILABLE, PLEASE TRY A DIFFERENT COMBINATION!</b></a></div></div>";
                }
                else {
                    ArrayList<String> countryCommodities = jdbc.getCountryCommodities(chosenLocation, chosenYear);
                    if (!countryCommodities.isEmpty()) {
    
                        // Need to end inputmessage div first
                        html += """
                                <br><br>
                                <a><b><a style='color: #fab802'>FOOD PRODUCTS LOST: (""";
                        
                        html += countryCommodities.size() + " total) | </a></b>";
    
                        for (int i = 0; i < countryCommodities.size(); ++i) {
                            if (i == (countryCommodities.size() - 1)) {
                                html += countryCommodities.get(i);   
                            }
                            else {
                                html += countryCommodities.get(i) + " | ";
                            }
                        }
                    }
                        
                    html += "</a>";
                    
                    html += "<br><br><a><b><a style='color: #fab802'>AVG LOSS FOR SELECTION: </a></b>" + String.format("%.3f%%", lossPercentage) + "</a>";

                    ArrayList<Loss> bothData = jdbc.getBothData(chosenLocation, Integer.valueOf(chosenYear));

                    // for (int i = 0; i < ordered.size(); ++i) {
                    //     System.out.println(ordered.get(i).getCountry() + ":" + ordered.get(i).getAvgDifference());
                    // }
                    if (!bothData.isEmpty()) {
                        html += """
                            </div>
                            <div class='tableContainer'>
                                <table class='contentTable'>
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>COUNTRY</th>
                                            <th>COMMON FOOD PRODUCTS</th>
                                            <th>AVG LOSS %</th>
                                            <th>DIFFERENCE %</th>
                                            <th>SIMILARITY %</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                            """;

                        int limit = 0;

                        if (bothData.size() > Integer.valueOf(numResults)) {
                            limit = Integer.valueOf(numResults);
                        }
                        else {
                            limit = bothData.size();
                        }
                        System.out.println(limit);

                        for (int i = 0; i < limit; ++i) {
                            html += """
                                    <tr>
                                        <td>
                                    """;
                            
                            html += (i + 1) + "</td><td>";
                            html += bothData.get(i).getCountry() + "</td><td>";
                            ArrayList<String> printCommodities = jdbc.getSimilarCountryCommodities(countryCommodities, bothData.get(i).getCountry(), chosenYear);
                                
                            for (int j = 0; j < printCommodities.size(); ++j) {
                                if (j == (printCommodities.size() - 1)) {
                                    if (printCommodities.size() == 1) {
                                        html += printCommodities.get(j) + "<br><a style='color: #fab802'><b>(" + printCommodities.size() + " match)</a></b></td><td>";
                                    }
                                    else {
                                        html += printCommodities.get(j) + "<br><a style='color: #fab802'><b>(" + printCommodities.size() + " matches)</a></b></td><td>";
                                    }
                                }
                                else {
                                    html += printCommodities.get(j) + " | ";
                                }
                            }

                            html += String.format("%.3f", bothData.get(i).getAverage()) + "</td><td>";
                            html += String.format("%.3f", bothData.get(i).getAvgDifference()) + "</td><td>";
                            html += String.format("%.3f", (100.00 - bothData.get(i).getAvgDifference())) + "</td></tr>";
                        }

                        html += "</tbody></table></div></div>";
                    }
                    else {
                        html += "<br><br><a style='color: #fc7979'><b>THERE IS NO SIMILAR FOOD LOSS DATA AVAILABLE FOR ANY OTHER COUNTRIES WITHIN THE CHOSEN YEAR, PLEASE TRY A DIFFERENT COMBINATION TO SEE SIMILARITY DATA!</b></a></div></div>";
                    }
                }
            }
        }

        // Close Content3a div
        html = html + "</div>";
        // Finish the HTML webpage
        html = html + "</body>" + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

    public ArrayList<Loss> selectionSort(ArrayList<Loss> arr){
        for (int i = 0; i < arr.size(); i++){
        int sindex = i;
             for (int j = i+1; j < arr.size(); j++){
                  if (arr.get(sindex).getOverlap() < arr.get(j).getOverlap())
                       sindex = j;
             }
             Loss temp = arr.get(sindex);
             arr.set(sindex, arr.get(i));
             arr.set(i, temp);
        }
        System.out.println();
        for (int i = 0; i < arr.size(); i++){
             System.out.println(arr.get(i).getOverlap() + ", " + arr.get(i).getCountry());
        }

        return arr;
   }

   public ArrayList<Loss> selectionSortPercentage(ArrayList<Loss> arr){
        for (int i = 0; i < arr.size(); i++){
        int sindex = i;
            for (int j = i+1; j < arr.size(); j++){
                if (arr.get(sindex).getAvgDifference() > arr.get(j).getAvgDifference())
                    sindex = j;
            }
            Loss temp = arr.get(sindex);
            arr.set(sindex, arr.get(i));
            arr.set(i, temp);
        }
        System.out.println();
        for (int i = 0; i < arr.size(); i++){
            System.out.println(arr.get(i).getAvgDifference() + ", " + arr.get(i).getCountry());
        }

        return arr;
    }

}
