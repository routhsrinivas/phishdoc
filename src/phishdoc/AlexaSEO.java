/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phishdoc;

/**
 *
 * @author srinivas
 */
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
public class AlexaSEO {
    public int getAlexaRanking(String domain) {

		int result = 0;
		
		String url = "http://data.alexa.com/data?cli=10&url=" + domain;

		try {

			URLConnection conn = new URL(url).openConnection();
			InputStream is = conn.getInputStream();
			
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = dBuilder.parse(is);
                        doc.toString();

			Element element = doc.getDocumentElement();
                       
                         NodeList element1=element.getElementsByTagName("COUNTRY");
                        if (element1.getLength() > 0) {
                        Element elementAttribute = (Element) element1.item(0);
                        String TLDCode = elementAttribute.getAttribute("CODE");
                        String TLDName = elementAttribute.getAttribute("NAME");
                        String RankInCountry = elementAttribute.getAttribute("RANK");
                        if(!TLDCode.isEmpty()||!TLDName.isEmpty()||!RankInCountry.isEmpty()){
                        //result = Integer.valueOf(TLD);
                        System.out.println("Country Code "+TLDCode);
                        System.out.println("Country NAME "+TLDName);
                        System.out.println("Rank in "+TLDName+": "+RankInCountry);
                        }
                        }
                     
			NodeList nodeList = element.getElementsByTagName("POPULARITY");
			if (nodeList.getLength() > 0) {
				Element elementAttribute = (Element) nodeList.item(0);
				String ranking = elementAttribute.getAttribute("TEXT");
				if(!"".equals(ranking)){
					result = Integer.valueOf(ranking);
				}
			}
                        /**** rank in their countries i.e tlds******/
                        NodeList nodeList2 = element.getElementsByTagName("REACH");
			if (nodeList2.getLength() > 0) {
				Element elementAttribute = (Element) nodeList2.item(0);
				String ranking = elementAttribute.getAttribute("RANK");
				if(!"".equals(ranking)){
					int countryrank = Integer.valueOf(ranking);
                                     System.out.println("Alexa reach rank : "+ countryrank);   
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return result;
	}
}
