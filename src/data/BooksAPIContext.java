package data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


/**
 * Class to consume REST API from Google APIs
 */
public class BooksAPIContext {

    public BooksAPIContext() {
    }

    /**
     * Gets the first book from response of Google books apis. Returns null if connection fails.
     * @param query = string for searching the book
     * @return
     */
    public Book getFirstBook(String query){
        try {

            //Endpoint + query URL
            URL url = new URL(String.format("https://www.googleapis.com/books/v1/volumes?q=%s&key=AIzaSyAhvaeEGnF-HIGW29iWUz0QzVoUlk_LPBo", URLEncoder.encode(query, StandardCharsets.UTF_8.toString())));

            //connection properties, method GET
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();


            //response code
            int responseCode = conn.getResponseCode();

            InputStream stream = conn.getErrorStream();
            if (stream == null) {
                stream = conn.getInputStream();
            }


            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                String inline = "";
                Scanner scanner = new Scanner(url.openStream());

                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }

                //Close the scanner
                scanner.close();

                //Using the JSON simple library parse the string into a json object
                JSONParser parse = new JSONParser();
                JSONObject data_obj = (JSONObject) parse.parse(inline);

                //Get the required object from the above created object
                JSONArray obj = (JSONArray) data_obj.get("items");
                JSONObject firstRecord = (JSONObject) obj.get(0);
                JSONObject volumeInfo = (JSONObject) firstRecord.get("volumeInfo");



                Book bookRecord = new Book();

                if(volumeInfo.containsKey("pageCount")) {
                    bookRecord.setPageCount(volumeInfo.get("pageCount").toString());
                }
                if(volumeInfo.containsKey("title")) {
                    bookRecord.setTitle(volumeInfo.get("title").toString());
                }
                if(volumeInfo.containsKey("description")) {
                    bookRecord.setDescription(volumeInfo.get("description").toString());
                }
                if(volumeInfo.containsKey("publishedDate")) {
                    bookRecord.setPublishedDate(volumeInfo.get("publishedDate").toString());
                }
                if(volumeInfo.containsKey("publisher")) {
                    bookRecord.setPublisher(volumeInfo.get("publisher").toString());
                }
                if(volumeInfo.containsKey("authors")) {
                    JSONArray authors = (JSONArray) volumeInfo.get("authors");

                    String sAuthors = "";
                    for(var author: authors){
                        sAuthors = sAuthors.concat(author.toString() + ", ");
                    }
                    if(sAuthors.length() > 0) {
                        bookRecord.setAuthors(sAuthors.substring(0, sAuthors.length() - 2));
                    }
                }


                return bookRecord;
            }



        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }



    }
}
