package org.example.ScheduleBot;

import org.example.BotDataBase.BotDataBase;
import org.example.ScheduleParse.Schedule;
import org.example.ScheduleParse.ScheduleParser;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleBot extends TelegramLongPollingBot {
    private Schedule schedule;
    private final BotText botText = new BotText();

    private BotDataBase botDataBase = new BotDataBase();
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            ScheduleParser parser = new ScheduleParser();
            String sendText = "";

            Long chatId = update.getMessage().getChatId(), userId = 0L;
            String userName = "", firstName = "", lastName = "";

            try {
                 userName = update.getMessage().getFrom().getUserName();
                 firstName = update.getMessage().getFrom().getFirstName();
                 lastName = update.getMessage().getFrom().getLastName();
                 userId = update.getMessage().getFrom().getId();

            } catch (Exception e) {
                System.out.println("huy");
            }
            try {
                botDataBase.Write(chatId, userName, firstName, lastName);
            } catch (SQLException e) {
                System.out.println("recording error");
            }

            if (schedule == null && !messageText.equals("/start")) {
                schedule = parser.ScheduleParse(messageText);
                if (schedule != null)
                    sendText = "Успешный ввод!\n";
                else {
                    sendText += "Введите номер группы\n";
                    sendMessage(chatId, sendText);
                    return;
                }
            }

            switch (messageText) {
                case "/start":
                    startCommand(chatId);
                    break;
                case "/near_lesson":
                    try {
                        sendMessage(chatId, schedule.getNearLesson());
                    } catch (Exception e){
                        System.out.println("ErrorNear");
                    }
                    break;
                case "/this_week":
                    sendText = schedule.getWeekSchedule(false);
                    sendMessage(chatId, sendText);
                    break;
                case "/next_week":
                    sendText = schedule.getWeekSchedule(true);
                    sendMessage(chatId, sendText);
                    break;
                case "/tomorrow":
                    sendText = schedule.getTomorrowLessons();
                    sendMessage(chatId, sendText);
                    break;
                case "/change_group_num":
                    sendText = "Введите желаемый номер группы";
                    sendMessage(chatId, sendText);
                    schedule = null;
                    break;
                case "/help":
                    sendMessage(chatId, botText.getHelpText());
                    break;
                case "/daily_schedule":
                    try {
                        execute(daysInlineKeyboard(chatId));
                    } catch (TelegramApiException e) {
                        System.out.println("gg");
                    }
                    break;
                default:
                    sendText += "Выберете действие:\n" + botText.getCommandText() + "\n";
                    sendMessage(chatId, sendText);
                    break;
            }
        } else if (update.hasCallbackQuery()) {
            String call_data = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            switch (call_data){
                case "Понедельник":
                    sendMessage(chatId, schedule.getDaySchedule("0"));
                    break;
                case "Вторник":
                    sendMessage(chatId, schedule.getDaySchedule("1"));
                    break;
                case "Среда":
                    sendMessage(chatId, schedule.getDaySchedule("2"));
                    break;
                case "Четверг":
                    sendMessage(chatId, schedule.getDaySchedule("3"));
                    break;
                case "Пятница":
                    sendMessage(chatId, schedule.getDaySchedule("4"));
                    break;
                case "Суббота":
                    sendMessage(chatId, schedule.getDaySchedule("5"));
                    break;
                case "Воскресенье":
                    sendMessage(chatId, schedule.getDaySchedule("6"));
                    break;
            }
        }
    }
    public static SendMessage daysInlineKeyboard (long chat_id) {
        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        message.setText("Выбери день недели: ");

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> rowInline1 = getDayInlineKeyboardButton("Понедельник");
        List<InlineKeyboardButton> rowInline2 = getDayInlineKeyboardButton("Вторник");
        List<InlineKeyboardButton> rowInline3 = getDayInlineKeyboardButton("Среда");
        List<InlineKeyboardButton> rowInline4 = getDayInlineKeyboardButton("Четверг");
        List<InlineKeyboardButton> rowInline5 = getDayInlineKeyboardButton("Пятница");
        List<InlineKeyboardButton> rowInline6 = getDayInlineKeyboardButton("Суббота");
        List<InlineKeyboardButton> rowInline7 = getDayInlineKeyboardButton("Воскресенье");

        rowsInline.add(rowInline1);
        rowsInline.add(rowInline2);
        rowsInline.add(rowInline3);
        rowsInline.add(rowInline4);
        rowsInline.add(rowInline5);
        rowsInline.add(rowInline6);
        rowsInline.add(rowInline7);

        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);

        return message;
    }

    private static List<InlineKeyboardButton> getDayInlineKeyboardButton(String text) {
        List<InlineKeyboardButton> list = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(text);
        list.add(button);
        return list;
    }
    public void startCommand(long chatId) {
        String sendText = "Здравствуйте, я подскажу вам расписание!\nВведите номер группы:";
        sendMessage(chatId, sendText);
    }

    public void sendMessage(long chatId, String messageText) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableHtml(true);
        sendMessage.setText(messageText);
        sendMessage.setChatId(String.valueOf(chatId));
        try {
            this.execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println("errorSend");
        }
    }

    @Override
    public String getBotUsername() {
        return "LETISchedule_bot";
    }

    @Override
    public String getBotToken() {
        return "5787190410:AAH8uVM6AX-ANumDNQkRBv6fOxHRbT4iNJg";
    }
}
