package org.example.ScheduleParse;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.google.common.math.IntMath.mod;

public class Schedule {
    private final Map<Integer, Map<String, Lessons>> schedule;
    private static Map<String, String> indexToDay;

    private static void FillIndexToDayMap() {
        indexToDay = new HashMap<>();
        indexToDay.put("0", "Понедельник");
        indexToDay.put("1", "Вторник");
        indexToDay.put("2", "Среда");
        indexToDay.put("3", "Четверг");
        indexToDay.put("4", "Пятница");
        indexToDay.put("5", "Суббота");
        indexToDay.put("6", "Воскресенье");
    }

    public Schedule(Map<String, Lessons> schedule) {
        Map<String, Lessons> evenWeekSchedule = new HashMap<>(),
                oddWeekSchedule = new HashMap<>();

        Lessons allLessons;
        for (String day : schedule.keySet()) {
            List<LessonData> evenWeekLessons = new ArrayList<>(), oddWeekLessons = new ArrayList<>();
            allLessons = schedule.get(day);
            for (LessonData lesson : allLessons.getLessons()) {
                if (lesson.getWeek().equals("1"))
                    oddWeekLessons.add(lesson);
                else
                    evenWeekLessons.add(lesson);
            }
            evenWeekSchedule.put(day, new Lessons(evenWeekLessons));
            oddWeekSchedule.put(day, new Lessons(oddWeekLessons));
        }

        this.schedule = new HashMap<>();
        this.schedule.put(1, oddWeekSchedule);
        this.schedule.put(0, evenWeekSchedule);
        FillIndexToDayMap();
    }

    public String getTomorrowLessons() {
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.HOUR, 24);
        int tomorrowWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        String tomorrowDay = Integer.toString(mod(calendar.get(Calendar.DAY_OF_WEEK) - 2, 7));

        Map<String, Lessons> tomorrowSched = schedule.get(mod(tomorrowWeek, 2));
        StringBuilder builder = new StringBuilder();
        Lessons lessons = tomorrowSched.get(tomorrowDay);
        if (lessons == null)
            return "Завтра пар нет\n";

        builder.append("<u><b>").
                append(indexToDay.get(tomorrowDay)).
                append("</b></u>\n").
                append("\n").
                append(lessons).
                append("\n\n");

        return builder.toString();
    }

    public String getDaySchedule(String index) {
        int thisWeek = new GregorianCalendar().get(Calendar.WEEK_OF_YEAR);
        StringBuilder builder = new StringBuilder();
        Lessons lessons;

        Map<String, Lessons> thisWeekSchedule = schedule.get(mod(thisWeek, 2));
        lessons = thisWeekSchedule.get(index);

        if (lessons == null)
            return "Завтра пар нет\n";

        builder.append("<u><b>").
                append(indexToDay.get(index)).
                append("</b></u>\n").
                append("\n").
                append(lessons).
                append("\n\n");

        return builder.toString();
    }

    public String getWeekSchedule(boolean next) {
        int thisWeek = new GregorianCalendar().get(Calendar.WEEK_OF_YEAR);
        if (next) thisWeek++;

        StringBuilder builder = new StringBuilder();
        Lessons lessons;

        Map<String, Lessons> thisWeekSchedule = schedule.get(mod(thisWeek, 2));
        for (String day : thisWeekSchedule.keySet()) {
            lessons = thisWeekSchedule.get(day);
            builder.append("<u><b>").
                    append(indexToDay.get(day)).
                    append("</b></u>\n").
                    append("\n").
                    append(lessons).
                    append("______________________________________").
                    append("\n\n");
        }

        return builder.toString();
    }

    public String getNearLesson() throws Exception {
        Calendar cal = new GregorianCalendar(),
                lessonCal = new GregorianCalendar();
        int thisWeek = cal.get(Calendar.WEEK_OF_YEAR);
        int thisDay = mod(cal.get(Calendar.DAY_OF_WEEK) - 2, 7);
        String thisDayString = String.valueOf(thisDay);
        SimpleDateFormat simpleDate = new SimpleDateFormat("HH:mm");
        Map<String, Lessons> sched = schedule.get(mod(thisWeek, 2));
        Date lessonTime;

        while (true) {
            Lessons lessons = sched.get(thisDayString);
            for (LessonData lesson : lessons.getLessons()) {
                lessonTime = simpleDate.parse(lesson.getStart_time());
                lessonCal.set(Calendar.HOUR_OF_DAY, lessonTime.getHours());
                lessonCal.set(Calendar.MINUTE, lessonTime.getMinutes());
                if (lessonCal.after(cal))
                    return lesson.toString();
            }
            thisDay++;
            lessonCal.add(Calendar.HOUR, 24);
            if (thisDay > 6) {
                thisDay -= 7;
                sched = schedule.get(mod(++thisWeek, 2));
            }
            thisDayString = String.valueOf(thisDay);
        }
    }

}


