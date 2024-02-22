package org.example.ScheduleParse;

public class LessonData {
    private String name;
    private String teacher;
    private String subjectType;
    private String week;
    private String start_time;
    private String room;

    public String getName() {
        return name;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public String getWeek() {
        return week;
    }

    public String getStart_time() {
        return start_time;
    }

    @Override
    public String toString() {
        return "Начало: " + start_time + "\n" +
                name.toUpperCase() + " (" + subjectType + ")" + "\n" +
                "Преподаватель: " + teacher + "\n" +
                "Аудитория: " + room + "\n";
    }
}
