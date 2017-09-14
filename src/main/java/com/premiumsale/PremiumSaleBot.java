package com.premiumsale;

import com.premiumsale.commands.StartCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

import static com.premiumsale.commands.StartCommand.LOGTAG;

public class PremiumSaleBot extends TelegramLongPollingCommandBot {

    public PremiumSaleBot() {
        register(new StartCommand());
//        HelpCommand helpCommand = new HelpCommand(this);
//        register(helpCommand);

        registerDefaultAction((absSender, message) -> {
            SendMessage commandUnknownMessage = new SendMessage();
            commandUnknownMessage.setChatId(message.getChatId());
            commandUnknownMessage.setText("The command '" + message.getText() + "' is not known by this bot. Here comes some help " + Emoji.AMBULANCE);
            try {
                absSender.sendMessage(commandUnknownMessage);
            } catch (TelegramApiException e) {
                BotLogger.error(LOGTAG, e);
            }
        });
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                sendMessage(MessageHandler.getInstance().handleIncomingMessage(update)); // Call method to send the message
            } else if (update.hasMessage() && update.getMessage().hasPhoto()) {
                sendPhoto(PhotoHandler.createPhotoMessage(update));
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return Constants.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return Constants.BOT_TOKEN;
    }
}
