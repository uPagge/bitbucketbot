package org.sadtech.bot.vcs.telegram.service.unit;

import lombok.RequiredArgsConstructor;
import org.sadtech.bot.vcs.core.domain.PullRequestStatus;
import org.sadtech.bot.vcs.core.domain.ReviewerStatus;
import org.sadtech.bot.vcs.core.domain.entity.Person;
import org.sadtech.bot.vcs.core.domain.entity.PullRequest;
import org.sadtech.bot.vcs.core.exception.NotFoundException;
import org.sadtech.bot.vcs.core.service.PersonService;
import org.sadtech.bot.vcs.core.service.PullRequestsService;
import org.sadtech.bot.vcs.core.utils.Smile;
import org.sadtech.social.bot.service.usercode.ProcessingData;
import org.sadtech.social.core.domain.BoxAnswer;
import org.sadtech.social.core.domain.content.Message;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * // TODO: 17.09.2020 Добавить описание.
 *
 * @author upagge 17.09.2020
 */
@Component
@RequiredArgsConstructor
public class PullRequestProcessing implements ProcessingData<Message> {

    private final PersonService personService;
    private final PullRequestsService pullRequestsService;

    @Override
    public BoxAnswer processing(Message message) {
        final Person person = personService.getByTelegramId(message.getPersonId())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        final List<PullRequest> pullRequests = pullRequestsService.getAllByReviewerAndStatuses(person.getLogin(), ReviewerStatus.NEEDS_WORK, Collections.singleton(PullRequestStatus.OPEN));
        String messageAnswer;
        if (pullRequests.isEmpty()) {
            messageAnswer = "Все ПР проверены :)";
        } else {
            final String prAnswer = pullRequests.stream()
                    .map(this::convert)
                    .collect(Collectors.joining("\n"));
            messageAnswer = MessageFormat.format(
                    "Вам необходимо посмотреть следующие ПР:{0}{1}",
                    Smile.HR, prAnswer
            );
        }
        return BoxAnswer.of(messageAnswer);
    }

    private String convert(PullRequest pullRequest) {
        return MessageFormat.format(
                "- [{0}]({1})",
                pullRequest.getTitle(),
                pullRequest.getUrl()
        );
    }

}
