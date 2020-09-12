package org.sadtech.bot.bitbucketbot.domain.change.comment;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.sadtech.bot.bitbucketbot.domain.Answer;
import org.sadtech.bot.bitbucketbot.domain.change.Change;
import org.sadtech.bot.bitbucketbot.domain.change.ChangeType;
import org.sadtech.bot.bitbucketbot.utils.Smile;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode(callSuper = true)
public class AnswerCommentChange extends Change {

    private final String youMessage;
    private final String url;
    private final List<Answer> answers;

    @Builder
    protected AnswerCommentChange(
            Set<Long> telegramIds,
            String youMessage,
            String url,
            List<Answer> answers
    ) {
        super(ChangeType.NEW_ANSWERS_COMMENT, telegramIds);
        this.youMessage = youMessage;
        this.url = url;
        this.answers = answers;
    }

    @Override
    public String generateMessage() {
        final String answerText = answers.stream()
                .map(answer -> answer.getAuthorName() + ": " + answer.getMessage().substring(0, Math.min(answer.getMessage().length(), 500)))
                .collect(Collectors.joining("\n\n"));
        return MessageFormat.format(
                "{0} *Новые ответы на ваш комментарий* | [ПР]({1}){2}" +
                        "{3}{4}" +
                        "{5}",
                Smile.BELL, url, Smile.HR, youMessage.substring(0, Math.min(youMessage.length(), 180)), Smile.HR, answerText
        );
    }
}