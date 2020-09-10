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
public class NewPrChange extends PrChange {

    private final String description;
    private final String author;

    @Builder
    private NewPrChange(
            Set<Long> telegramIds,
            String title,
            String url,
            String description,
            String author) {
        super(ChangeType.NEW_PR, telegramIds, title, url);
        this.description = description;
        this.author = author;
    }

    @Override
    public String generateMessage() {
        return MessageFormat.format(
                "{0} *Новый Pull Request*\n" +
                        "[{1}]({2})" +
                        "{3}\n" +
                        "{4}" +
                        "{5}: {6}\n\n",
                Smile.FUN, title, url, Smile.HR,
                (description != null && !"".equals(description)) ? description + Smile.HR : "",
                Smile.AUTHOR, author
        );
    }
}
