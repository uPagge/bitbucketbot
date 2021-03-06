package org.sadtech.bot.vcs.telegram.unit;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.sadtech.bot.vsc.bitbucketbot.context.domain.entity.NotifySetting;
import org.sadtech.bot.vsc.bitbucketbot.context.domain.entity.Person;
import org.sadtech.bot.vsc.bitbucketbot.context.exception.NotFoundException;
import org.sadtech.bot.vsc.bitbucketbot.context.service.NotifyService;
import org.sadtech.bot.vsc.bitbucketbot.context.service.PersonService;
import org.sadtech.social.bot.domain.unit.AnswerProcessing;
import org.sadtech.social.bot.domain.unit.AnswerText;
import org.sadtech.social.core.domain.BoxAnswer;
import org.sadtech.social.core.domain.content.Message;
import org.sadtech.social.core.utils.KeyBoards;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * // TODO: 20.09.2020 Добавить описание.
 *
 * @author upagge 20.09.2020
 */
@Configuration
@RequiredArgsConstructor
public class NotifySettingUnit {

    private final PersonService personService;
    private final NotifyService notifyService;

    @Bean
    public AnswerText notifySetting(
            AnswerProcessing<Message> disableNotifications
    ) {
        return AnswerText.builder()
                .boxAnswer(
                        BoxAnswer.builder()
                                .message("Вы можете полностью остановить уведомления от бота")
                                .keyBoard(
                                        KeyBoards.verticalDuoMenuString(
                                                Arrays.stream(DisableMenu.values())
                                                        .map(DisableMenu::getName)
                                                        .collect(Collectors.toList())
                                        )
                                )
                                .build()
                )
                .phrase("Уведомления")
                .nextUnit(disableNotifications)
                .build();
    }

    @Bean
    public AnswerProcessing<Message> disableNotifications() {
        return AnswerProcessing.builder()
                .processingData(
                        message -> {
                            final Person person = personService.getByTelegramId(message.getPersonId())
                                    .orElseThrow(() -> new NotFoundException("Не найдено"));
                            final NotifySetting notifySetting = notifyService.getSetting(person.getLogin())
                                    .orElseThrow(() -> new NotFoundException("Не найдено"));
                            notifySetting.setStartReceiving(
                                    LocalDateTime.now().plusMinutes(DisableMenu.from(message.getText()).getMinutes())
                            );
                            notifyService.saveSettings(notifySetting);
                            return BoxAnswer.of("Настройки сохранены");
                        }
                )
                .build();
    }

    @Getter
    @RequiredArgsConstructor
    private enum DisableMenu {

        TURN_ON("Включить", 0),
        TURN_OFF("Выключить", 525600),
        DISABLE_15_MIN("15 мин", 15),
        DISABLE_2_HOUR("2 часа", 120),
        DISABLE_30_MIN("30 мин", 30),
        DISABLE_4_HOUR("4 часа", 240),
        DISABLE_60_MIN("60 мин", 60),
        DISABLE_8_HOUR("8 часов", 480);

        private final String name;
        private final int minutes;

        public static DisableMenu from(@NonNull String name) {
            return Arrays.stream(DisableMenu.values())
                    .filter(disableMenu -> disableMenu.getName().equals(name))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("Не найдено"));
        }

    }

}
