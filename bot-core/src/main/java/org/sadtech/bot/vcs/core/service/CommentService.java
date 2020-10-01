package org.sadtech.bot.vcs.core.service;

import lombok.NonNull;
import org.sadtech.basic.context.service.SimpleManagerService;
import org.sadtech.bot.vcs.core.domain.entity.Comment;
import org.sadtech.bot.vcs.core.domain.entity.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface CommentService extends SimpleManagerService<Comment, Long> {

    Long getLastCommentId();

    List<Comment> getAllBetweenDate(@NonNull LocalDateTime dateFrom, @NonNull LocalDateTime dateTo);

    List<Comment> getAllById(@NonNull Set<Long> ids);

    Comment convert(@NonNull Task task);

    Set<Long> existsById(@NonNull Set<Long> ids);

}