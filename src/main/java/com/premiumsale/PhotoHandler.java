package com.premiumsale;

import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;

import java.util.Comparator;
import java.util.List;

public class PhotoHandler {
    public static SendPhoto createPhotoMessage(Update update) {
        // Array with photos
        List<PhotoSize> photos = update.getMessage().getPhoto();
        // Get largest photo's file_id
        String f_id = photos.stream()
                .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                .findFirst()
                .orElse(null).getFileId();
        // Send photo by file_id we got before
        SendPhoto msg = new SendPhoto()
                .setChatId(update.getMessage().getChatId())
                .setPhoto(f_id)
                .setCaption("Иди нахуй со своей фоткой");
        return msg;

    }
}
