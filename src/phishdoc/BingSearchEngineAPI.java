/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phishdoc;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Base64;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author srinivas
 */
public class BingSearchEngineAPI
{
    public boolean bingResultsStatus(String host,String text)throws Exception
    {
        boolean status=false;
        String webHost="";
    final String accountKey = "X3f6+bqcetbaJY7Vu77/BBMsbV36qzo0tC/zReIr5yU";
        final String bingUrlPattern = "https://api.datamarket.azure.com/Bing/Search/Web?Query=%%27%s%%27&$format=JSON";

        final String query = URLEncoder.encode(text, Charset.defaultCharset().name());
        final String bingUrl = String.format(bingUrlPattern, query);

        final String accountKeyEnc = Base64.getEncoder().encodeToString((accountKey + ":" + accountKey).getBytes());

        final URL url = new URL(bingUrl);
        final URLConnection connection = url.openConnection();
        connection.setRequestProperty("Authorization", "Basic " + accountKeyEnc);

        try (final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) 
        {
            String inputLine;
            final StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) 
            {
                response.append(inputLine);
            }
            final JSONObject json = new JSONObject(response.toString());
            final JSONObject d = json.getJSONObject("d");
            final JSONArray results = d.getJSONArray("results");
            int resultsLength = results.length();
            if(resultsLength>25)
                resultsLength=25;
            for (int i = 0; i < resultsLength; i++) 
            {
                final JSONObject aResult = results.getJSONObject(i);
                //System.out.println(aResult.get("Url"));
                 URL uri = new URL(aResult.get("Url").toString());
                webHost=uri.getHost();
                 int score=StringUtils.getLevenshteinDistance(host, webHost);
                 if(score==0)
                     return true;
            }
        }
        return status;
    }
}
