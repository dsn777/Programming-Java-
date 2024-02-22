package org.example;

import com.google.gson.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class WikiSearch {
    public WikiSearch(){
    }
    private List<Page> getPages(String jsonResponse){
        Gson gson = new Gson();
        JsonElement jsonElement = gson.fromJson(jsonResponse, JsonElement.class);
        JsonObject jsonObject = jsonElement.getAsJsonObject().getAsJsonObject("query");
        JsonArray js = jsonObject.getAsJsonArray("search");
        List<Page> pages = new ArrayList<>();

        for(int i = 0; i < js.size(); i++){
            pages.add(gson.fromJson(js.get(i), Page.class));
        }

        return pages;
    }
    public URL Search(String str)
    {
        try {
            String SearchRequest = URLEncoder.encode(str, "UTF-8");
            URL url = new URL("https://ru.wikipedia.org/w/api.php?action=query&list=search&utf8=&format=json&srsearch=\"" + SearchRequest + "\"\n");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }

                // Распарсенный ответ:
                List<Page> WikiPages = getPages(response.toString());
                if (WikiPages.isEmpty()) throw new NullPointerException("Nothing was found for your request");

                for (int i = 0; i < WikiPages.size(); i++){
                    System.out.println(i + 1 + ". " + WikiPages.get(i).getTitle());
                }

                System.out.println("Select the page number you want: ");
                br = new BufferedReader(new InputStreamReader(System.in));
                int indexChoice = Integer.parseInt(br.readLine());
                br.close();

                return new URL("https://ru.wikipedia.org/w/index.php?curid=" + WikiPages.get(indexChoice - 1).getPageId());
            }
            else{
                System.out.println("Ошибка запроса + " + responseCode);
            }
        } catch (NumberFormatException a) {
            System.out.println("Incorrect input");
        } catch (IndexOutOfBoundsException b) {
            System.out.println("the entered index is outside the array");
        } catch (NullPointerException c){
            System.out.println(c.getMessage());
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

