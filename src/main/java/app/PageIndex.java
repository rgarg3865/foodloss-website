package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;


public class PageIndex implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/";

    @Override
    public void handle(Context context) throws Exception {
        JDBCConnection jdbc = new JDBCConnection();
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Header information
        html = html + "<head>" + 
               "<title>FOODLOSS.COM</title>" + 
               "<link rel='icon' type='image/png' href='weblogo.png'>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css'/>";
        html = html + """
          <style>
            body {position: relative;}
          
            body::before {
              background-image: url('bg.jpg');
              background-repeat: no-repeat;
              background-attachment: fixed;
              background-size: cover;
              content: '';
              position: absolute;
              inset: 0;
              opacity: 0.15;
            }

            .topnav {isolation: isolate;}
        
            .topnav a {isolation: isolate;}

            .content1 {isolation: isolate;}

            ::-webkit-scrollbar {display:none;}
          </style>
        """;

        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // Add the topnav
        // This uses a Java v15+ Text Block
        html = html + """
            <div class="topnav">
            <a href='/' style="float:left">FOODLOSS.COM</a>
            <a href='data.html'>DATA</a>
          </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content1' style='margin-top: 150px; font-size: 16px'>";
        
        // Add HTML for the page content

        ArrayList<Loss> facts = jdbc.getHomePageFacts();
        
        for (int i = 0; i < 2; ++i) {
          if (i == 0) {
            html += ("<p style='margin-bottom: 0px'>IN " + facts.get(i).getYear() + ", " + facts.get(i).getCountry() + " LOST " + String.format("%.0f", facts.get(i).getLoss_percentage()) + "% OF " + facts.get(i).getCommodity() + " THEY PRODUCED.</p>").toUpperCase();
          }
          else {
            html += ("<p style='margin-bottom: 0px'>IN " + facts.get(i).getYear() + ", " + facts.get(i).getCountry() + " LOST " + facts.get(i).getLoss_percentage() + "% OF " + facts.get(i).getCommodity() + " THEY PRODUCED.</p>").toUpperCase();
          }
        }

        html += "<p style='margin-bottom: 0px'>THESE HAVE BEEN TWO OF THE BIGGEST FOOD LOSSES IN THE PAST DECADE.</p>";

        // html = html + """
        //     <p style="margin-bottom: 0px">IN 2013, AUSTRALIA LOST 65% OF CAULIFLOWERS AND BROCCOLI'S THEY PRODUCED.</p>
        //     <p style="margin-bottom: 0px">IN 2018, USA LOST 62.9% OF THE CARROTS AND TURNIPS THEY PRODUCED.</p>
        
        html += "<p style='padding-top: 30px; margin-bottom: 0px'>BUT THERE'S MANY MORE.</p>";

        ArrayList<Loss> range = jdbc.getYearRange();
        html += ("<p style='padding-bottom: 20px'>CLICK BELOW TO SEE ALL THE FIGURES FROM " + range.get(0).getMinYear() + " - " + range.get(0).getMaxYear() + ".</p>").toUpperCase();
        //     <p style="padding-bottom: 30px">CLICK BELOW TO SEE THE FIGURES FROM 1966-2022.</p>
        html += "<a href='data.html' class='indexButton'>SEE THE DATA</a>";
        //     """;
            
        // Close Content div
        html = html + "</div>";

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
        
    }
}
