package app;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class PageData implements Handler {
    
    public static final String URL = "/data.html";

    @Override
    public void handle(Context context) throws Exception {

        String html = "<html>";

        html = html + "<head>" + 
               "<title>DATA SELECT</title>" + 
               "<link rel='icon' type='image/png' href='weblogo.png'>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css'/>";

        html = html + "</head>";

        // Add the body
        html = html + "<body class='data-page-body'>";

        html = html + """
        <div class='topnav-outer'>
            <div class="topnav">
            <a href='/' style="float:left">FOODLOSS.COM</a>
            <a href='data.html'>DATA</a>
          </div>
          </div>
        """;

        html += """
                <div class='outer-container'>
                    <a href='page2A.html' class='indexButtonData'><div class='pages'>
                        <br><span><b>COUNTRY DATA</b><span><br><br>
                        <span><b>BASIC INFORMATION.</b> Click to search for food loss data filtered by different countries in the database</span>
                    </div><br></a>

                    <a href='page3A.html' class='indexButtonData'><div class='pages'>
                        <br><span><b>GEOGRAPHIC LOCATION SIMILARITY TOOL</b><span><br><br>
                        <span><b>ADVANCED INFORMATION.</b> Choose a location, a year and find similar locations in the same year, sorted by most to least similar</span>
                    </div><br></a>             

                </div>
                """;


        html = html + "</body>" + "</html>";
        context.html(html);
    }
}
