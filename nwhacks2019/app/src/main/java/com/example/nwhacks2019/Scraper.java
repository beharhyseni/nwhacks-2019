package com.example.nwhacks2019;

import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scraper {
    // Initialize Globals:
    // Max number of google searches done by google api -- is 20 by default
    private int maxGoogleResults = 20;
    // store sites to scrape
    private String[] stores = {"amazon", "londondrugs", "realcanadiansuperstore", "thriftyfoods"};
    // Map loose store name to official store name (e.g. "londondrugs" -> "London Drugs")
    private Map<String, String> storeNameToFullName = new HashMap<String, String>();

    // Potential Store List
    // "amazon", "apple", "bestbuy", "costco", "homedepot", "iga", "londondrugs", "macys",
    // "realcanadiansuperstore", "safeway", "saveonfoods", "shoppersdrugmart", "target", "walmart"

    private void initializeStoreToFullNameMap() {
        storeNameToFullName.put("amazon", "Amazon");
        storeNameToFullName.put("londondrugs", "London Drugs");
        storeNameToFullName.put("realcanadiansuperstore", "Real Canadian SuperStore");
        storeNameToFullName.put("thriftyfoods", "Thrify Foods");
    }

    // Check if url is one of our desired stores
    private boolean verifyURL(String url) {
        try {
            URL aURL = new URL(url);
            String host = aURL.getHost();

            // Check for store in host string
            for (String r : stores) {
                if (host.contains(r)) {
                    return true;
                }
            }

        } catch (Exception e) {
            System.out.println("Unable to parse URL: " + url);
        }
        return false;
    }

    // Input: a product query (e.g. "lysol wipes")
    // Output: a map of store names to product urls
    private Map<String, List<String>> googleSearch(String query) {
        Map<String, List<String>> stores_to_urls = new HashMap<String, List<String>>();

        for (Map.Entry<String, String> entry : storeNameToFullName.entrySet()) {
            // Append store name to query for better results
            String specificQuery = query + " " + entry.getValue();
            // Perform google search
            List<String> urls = googleSearchstore(specificQuery);
            stores_to_urls.put(entry.getKey(), urls);
        }
        return stores_to_urls;
    }

    // Input: google search query for product
    // Output: list of urls of best matching results
    private List<String> googleSearchstore(String query) {
        // Create search query
        String searchURL = "https://www.google.com/search" + "?q=" + query + "&num=" + maxGoogleResults;
        // Results
        List<String> urls = new ArrayList<String>();

        try {
            Document doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();
            Elements results = doc.select("h3.r > a");
            for (Element result : results) {
                String linkHref = result.attr("href");
                String url = linkHref.substring(7, linkHref.indexOf("&"));

                // Verify URL belongs to our desired stores
                if (verifyURL(url)) {
                    urls.add(url);
                }
            }

        } catch (Exception e) {
            // Do something
            System.out.print(e.getMessage());
        }

        return urls;
    }

    // Input: a map of store names to urls
    // Output: JSON object containing info of products from the input urls
    private JSONObject getProducts(Map<String, List<String>> stores_to_urls) {
        // Results
        JSONObject resultsJson = new JSONObject();

        // Count number of results we obtain
        Integer numProducts = 0;

        for (Map.Entry<String, List<String>> entry : stores_to_urls.entrySet()) {
            String store = entry.getKey(); // store
            List<String> urls = entry.getValue(); // urls

            // details to web scrape a specific store site
            String[] details = getstoreScrapeDetails(store);

            for (String url : urls) {
                // Scrape store site to find product info
                String[] productInfo = scrapestoreSite(url, details);
                String productName = productInfo[0];
                String productPrice = productInfo[1];
                String productImage = productInfo[2];

                // Ignore results if unable to scrape product name or price
                if (productName.equals("") || productPrice.equals("")) {
                    continue;
                }

                JSONObject productJson = new JSONObject();
                try{
                    productJson.put("name", productName);
                    productJson.put("price", productPrice);
                    productJson.put("image", productImage);
                    productJson.put("store", storeNameToFullName.get(store));
                    productJson.put("url", url);
                    resultsJson.put(Integer.toString(numProducts), productJson);
                    numProducts++;
                }catch (JSONException je){
                }
            }
        }
        return resultsJson;
    }

    // Input: url containing info of a product
    // Output: returns product info after scraping url
    private String[] scrapestoreSite(String url, String[] details) {
        // HTML elements that contain info about product
        String nameElement = details[0];
        String priceElement = details[1];
        String imageElement = details[2];
        String imageAttr = details[3];

        String name = "";
        String price = "";
        String imgUrlString = "";

        try {
            Document doc = Jsoup.connect(url).userAgent("Mozilla/49.0").get();

            name = doc.select(nameElement).first().html();
            price = doc.select(priceElement).first().html();
            price = price.replaceAll("[^.0-9]", "");

            Element image = doc.select(imageElement).first();
            imgUrlString = image.attr(imageAttr);
        } catch (Exception e) {

        }

        String[] productDetails = {name, price, imgUrlString};
        return productDetails;

    }

    // Details for scraping specific sites
    private String[] getstoreScrapeDetails(String store) {
        String nameSearch;
        String priceSearch;
        String imageSearch;
        String imageAttrSearch;

        if (store.equals("amazon")) {
            nameSearch = "#productTitle";
            priceSearch = "#priceblock_ourprice";
            imageSearch = "#landingImage";
            imageAttrSearch = "data-old-hires";

        } else if (store.equals("londondrugs")) {
            nameSearch = "h1[itemprop = name]";
            priceSearch = "p[itemprop = price]";
            imageSearch = "img[itemprop = image]";
            imageAttrSearch = "src";

        } else if(store.equals("realcanadiansuperstore")) {
            nameSearch = "h1[class = product-name]";
            priceSearch = "span[class = reg-price-text]";
            imageSearch = "img[itemprop = image]";
            imageAttrSearch = "src";
        }else{
            nameSearch = "span[itemprop = name]";
            priceSearch = "span[itemprop = price]";
            imageSearch = "img[itemprop = image]";
            imageAttrSearch = "src";
        }

        String[] details = {nameSearch, priceSearch, imageSearch, imageAttrSearch};
        return details;
    }

    // Run the scraper
    public JSONObject runScraper(String query, Integer nSites){
        maxGoogleResults = nSites;
        initializeStoreToFullNameMap();
        Map<String, List<String>> stores_to_urls = googleSearch(query);
        JSONObject resultsJson = getProducts(stores_to_urls);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(resultsJson.toString());
        String prettyJsonString = gson.toJson(je);
        System.out.print(prettyJsonString);

        return resultsJson;
    }
}
