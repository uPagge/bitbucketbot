package org.sadtech.bot.bitbucketbot.domain.change.pullrequest;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.sadtech.bot.bitbucketbot.domain.change.ChangeType;
import org.sadtech.bot.bitbucketbot.utils.Smile;

import java.text.MessageFormat;
import java.util.Set;

@Getter
@EqualsAndHashCode(callSuper = true)
public class UpdatePrChange extends PrChange {

    private final String author;

    @Builder
    private UpdatePrChange(
            Set<Long> telegramIds,
            String name,
            String url, String author) {
        super(ChangeType.UPDATE_PR, telegramIds, name, url);
        this.author = author;
    }

    @Override
    public String generateMessage() {
        return MessageFormat.format(
                "{0} *Обновление Pull Request*\n" +
                        "[{1}]({2})" +
                        "{3}" +
                        "{4}: {5}\n\n",
                Smile.UPDATE, title, url, Smile.HR, Smile.AUTHOR, author
        );
    }

}