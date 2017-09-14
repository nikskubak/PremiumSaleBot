package com.premiumsale;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;

import java.util.Comparator;
import java.util.List;

public class MessageHandler {
    private static MessageHandler ourInstance;

    public static MessageHandler getInstance() {
        if (ourInstance == null) {
            ourInstance = new MessageHandler();
            return ourInstance;
        }
        return ourInstance;
    }

    private MessageHandler() {
    }

    public SendMessage handleIncomingMessage(Update update) {
        System.out.print("\n" + update.toString());
        if (messageIsValid(update)) {
            if (update.getMessage().getText().equals(ButtonActions.Names.ORDER_BUTTON)){

            }
            return createDefaultSendMessage(update);
        } else {
            return null;
        }
    }

    //    private SendMessage createStartBotMessage(Update update) {
//        SendMessage sendMessage = new SendMessage()
//                .setChatId(update.getMessage().getChatId())
//                .setText("Вы кто такие? Я вас не звал, идите нахуй");
//        addCustomKeyboard(sendMessage);
//        if (update.getMessage().getText().equals(ButtonActions.Names.FIRST_BUTTON))
//            sendMessage.setText("Сам иди нахуй");
//        System.out.print(update.getMessage().getText());
//        return sendMessage;
//    }
//
    private SendMessage createDefaultSendMessage(Update update) {
        return new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText("PremiumSale для умных харьковчан");
    }

    private boolean messageIsValid(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }
//
//    public void addCustomKeyboard(SendMessage message) {
//        // Create ReplyKeyboardMarkup object
//        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
//        // Create the keyboard (list of keyboard rows)
//        List<KeyboardRow> keyboard = new ArrayList<>();
//        // Create a keyboard row
//        KeyboardRow row = new KeyboardRow();
//        // Set each button, you can also use KeyboardButton objects if you need something else than text
//        row.add(ButtonActions.Names.FIRST_BUTTON);
//        row.add(ButtonActions.Names.SECOND_BUTTON);
//        row.add(ButtonActions.Names.THIRD_BUTTON);
//        // Add the first row to the keyboard
//        keyboard.add(row);
//        // Create another keyboard row
////        row = new KeyboardRow();
////        // Set each button for the second line
////        row.add("Row 2 Button 1");
////        row.add("Row 2 Button 2");
////        row.add("Row 2 Button 3");
//        // Add the second row to the keyboard
////        keyboard.add(row);
//        // Set the keyboard to the markup
//        keyboardMarkup.setKeyboard(keyboard);
//        // Add it to the message
//        message.setReplyMarkup(keyboardMarkup);
//    }

    public PhotoSize getPhoto(Update update) {
        // Check that the update contains a message and the message has a photo
        if (update.hasMessage() && update.getMessage().hasPhoto()) {
            // When receiving a photo, you usually get different sizes of it
            List<PhotoSize> photos = update.getMessage().getPhoto();

            // We fetch the bigger photo
            return photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null);
        }

        // Return null if not found
        return null;
    }
}
