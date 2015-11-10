package it.keisoft.puzzleanimazione;

import android.os.AsyncTask;
import android.util.Log;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mmarcheselli on 05/11/2015.
 */
public class HtmlParser{

    // oggetto TagNode radice del file html
    private Document rootNode;
    private String url_str = null;

    public HtmlParser(String url_str) {
        //URL di cui si vuole fare il parsing
        this.url_str = url_str;

        // inizializzazione dell'oggetto HtmlCleaner utile a generare un html pulito
/*        HtmlCleaner cleaner = new HtmlCleaner();
        CleanerProperties props = cleaner.getProperties();
        props.setAllowHtmlInsideAttributes(true);
        props.setAllowMultiWordAttributes(true);
        props.setRecognizeUnicodeChars(true);
        props.setOmitComments(true);
*/
        // apertura della connessione
//        URL url;
        try {

//            url = new URL(url_str);
            rootNode  = Jsoup.connect(url_str).get();
//            URLConnection conn = url.openConnection();

//            rootNode = cleaner.clean(new InputStreamReader(conn.getInputStream()));

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            Log.e("Error", e.getMessage());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e("Error", e.getMessage());
        }

    }


    private Element getElement(String elementName, String id){
        List<Object> elementList = new ArrayList<Object>();

        Elements topicList = rootNode.select(elementName);
//        TagNode Elements[] = rootNode.getElementsByName(elementName, true);
        for (Element element : topicList){

            Element el = element.getElementById(id);
            if(el != null){
                return el;
            }
//            String type = Elements[i].getAttributeByName("id");
//            if ( type != null  && type.equalsIgnoreCase(id)){
//                return rootNode;
//                return Elements[i];
                //elementList.add(Elements[i]);
//            }

        }

        return null;
        //return elementList;
    }

    public String Stampa(String elementName, String id)
    {
        StringBuffer sb = new StringBuffer();

        String html_value = "<html xmlns=\"http://www.w3.org/1999/xhtml\">" +
                "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">";

/*        Element style = this.getElement("style", "single_post");

        html_value = html_value + "<style type=\"text\\css\">body{float: left;" +
                "    padding-right: 5%;" +
                "    width: 90%;" +
                "    padding-left: 5%;" +
                "    border-bottom: 5px solid #c7c8f4;" +
                "    background: #c7c8f4;" +
                "    box-shadow: 0px 0px 2px rgba(0, 0, 0, 0.10);" +
                "    -moz-box-shadow: 0px 0px 2px rgba(0, 0, 0, 0.10);" +
                "    -webkit-box-shadow: 0px 0px 2px rgba(0, 0, 0, 0.10);" +
                "    margin-bottom: 30px;" +
                "    font-family: hobo, Helvetica, sans-serif;" +
                "    color: #888;" +
                "    font-size: 13px;}</style>";
*/        html_value = html_value + "</head><body>";
        try
        {

            Element elementi = this.getElement(elementName, id);

            html_value = html_value + elementi.html();

/*            sb.append(">>> Stampa contenuto degli elementi '"+elementName+"' per il sito '"+url_str+"'\n");
            for (Iterator<Object> iterator = elementi.iterator(); iterator.hasNext();)
            {
                TagNode Element = (TagNode) iterator.next();

                sb.append("Link: " + Element.getText()+"\n");//getAttributeByName("href").toString()+"\n");


            }
*/
//            return html_value;//sb.toString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        html_value = html_value + "</body></html>";
        return html_value;
    }

}
