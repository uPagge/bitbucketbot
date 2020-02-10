package com.tsc.bitbucketbot.domain.util;

import com.tsc.bitbucketbot.domain.ReviewerStatus;
import lombok.Getter;
import lombok.NonNull;

/**
 * TODO: Добавить описание класса.
 *
 * @author upagge [07.02.2020]
 */
@Getter
public class ReviewerChange {

    private String name;
    private Type type;
    private ReviewerStatus status;
    private ReviewerStatus oldStatus;

    private ReviewerChange(String name, Type type, ReviewerStatus status) {
        this.name = name;
        this.type = type;
        this.status = status;
    }

    private ReviewerChange(String name, Type type, ReviewerStatus status, ReviewerStatus oldStatus) {
        this.name = name;
        this.type = type;
        this.status = status;
        this.oldStatus = oldStatus;
    }

    private ReviewerChange(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @NonNull
    public static ReviewerChange ofNew(String name, ReviewerStatus reviewerStatus) {
        return new ReviewerChange(name, Type.NEW, reviewerStatus);
    }

    @NonNull
    public static ReviewerChange ofOld(String name, ReviewerStatus oldStatus, ReviewerStatus newStatus) {
        return new ReviewerChange(name, Type.OLD, newStatus, oldStatus);
    }

    @NonNull
    public static ReviewerChange ofDeleted(String name) {
        return new ReviewerChange(name, Type.DELETED);
    }

    public enum Type {
        NEW, DELETED, OLD
    }

}
