package org.sadtech.bot.bitbucketbot.domain.change;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Set;

@Getter
@EqualsAndHashCode(callSuper = true)
public abstract class PrChange extends Change {

    private final String name;
    private final String url;

    protected PrChange(ChangeType type, Set<Long> telegramIds, String name, String url) {
        super(type, telegramIds);
        this.name = name;
        this.url = url;
    }

}