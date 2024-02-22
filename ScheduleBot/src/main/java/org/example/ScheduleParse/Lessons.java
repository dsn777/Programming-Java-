package org.example.ScheduleParse;

import java.util.List;

public class Lessons {
    private final List<LessonData> LessonsList;

    public List<LessonData> getLessons() {
        return LessonsList;
    }

    public Lessons(List<LessonData> LessonsList) {
        this.LessonsList = LessonsList;
    }

    @Override
    public String toString() {
        if (LessonsList.isEmpty())
            return "Пар нет\n\n";

        StringBuilder builder = new StringBuilder();
        for (LessonData lesson : LessonsList) {
            builder.append(lesson.toString()).append("\n");
        }
        return builder.toString();
    }
}
