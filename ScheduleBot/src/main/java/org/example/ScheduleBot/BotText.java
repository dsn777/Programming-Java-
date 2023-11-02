package org.example.ScheduleBot;

public class BotText {
    private final String commandText = "/change_group_num - изменить номер группы\n" +
            "/tomorrow - расписание на завтра\n" +
            "/daily_schedule - расписание в выбранный день\n" +
            "/this_week - расписание на эту неделю\n" +
            "/next_week - расписание на следующую неделю\n" +
            "/help - справка\n" +
            "/near_lesson - ближайшее занятие";
    private final String helpText = "Чтобы пользоваться ботом:\n -Введите правильный номер группы\n-Выберете нужную команду";

    public String getCommandText(){
        return commandText;
    }
    public String getHelpText(){
        return helpText;
    }
}
