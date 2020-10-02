package org.sadtech.bot.vcs.telegram.service.unit;

import lombok.RequiredArgsConstructor;
import org.sadtech.bot.vcs.core.domain.entity.Person;
import org.sadtech.bot.vcs.core.exception.NotFoundException;
import org.sadtech.bot.vcs.core.service.PersonService;
import org.sadtech.bot.vcs.core.service.RatingService;
import org.sadtech.social.bot.service.usercode.ProcessingData;
import org.sadtech.social.core.domain.BoxAnswer;
import org.sadtech.social.core.domain.content.Message;
import org.springframework.stereotype.Component;

/**
 * // TODO: 01.10.2020 Добавить описание.
 *
 * @author upagge 01.10.2020
 */
@Component
@RequiredArgsConstructor
public class RatingTopProcessing implements ProcessingData<Message> {

    private final RatingService ratingService;
    private final PersonService personService;

    @Override
    public BoxAnswer processing(Message content) {
        final Person person = personService.getByTelegramId(content.getPersonId())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        return BoxAnswer.builder()
                .message(ratingService.getRatingTop(person.getLogin()))
                .build();
    }
}