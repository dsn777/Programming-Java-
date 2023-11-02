package org.example.ScheduleParse;

import com.google.gson.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ScheduleParser {
    private Lessons getLessonsByIndexDayFromJson(Map<String, JsonElement> jsonSchedule, String indexDay) {
        Gson gson = new Gson();
        JsonElement jsonElement = jsonSchedule.get(indexDay);
        JsonArray jsonArray = jsonElement.getAsJsonObject().getAsJsonArray("lessons");
        List<LessonData> lessonDataList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            lessonDataList.add(gson.fromJson(jsonArray.get(i), LessonData.class));
        }

        return new Lessons(lessonDataList);
    }

    private Schedule getSchedule(String jsonData, String groupNumber) {
        JsonElement jsonElement = JsonParser.parseString(jsonData);
        JsonObject jsonObject = jsonElement.getAsJsonObject().getAsJsonObject(groupNumber);
        jsonObject = jsonObject.getAsJsonObject("days");

        Map<String, JsonElement> JsonScheduleMap = jsonObject.asMap();
        Map<String, Lessons> ScheduleMap = new HashMap<>();

        Lessons tempLessons;
        for (String day : JsonScheduleMap.keySet()) {
            tempLessons = getLessonsByIndexDayFromJson(JsonScheduleMap, day);
            ScheduleMap.put(day, tempLessons);
        }

        return new Schedule(ScheduleMap);
    }

    public Schedule ScheduleParse(String groupNumber) {
        try {
            String encodedGroupNumber = URLEncoder.encode(groupNumber, StandardCharsets.UTF_8);
            URI uri = new URI("https://digital.etu.ru/api/mobile/schedule?groupNumber=" + encodedGroupNumber);
            HttpsURLConnection conn = (HttpsURLConnection) uri.toURL().openConnection();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }

                return getSchedule(response.toString(), groupNumber);
            } else {
                System.out.println("Ошибка запроса + " + responseCode);
            }
        } catch (Exception e) {
            System.out.println("ErrorParse");
        }
        return null;
    }
}
