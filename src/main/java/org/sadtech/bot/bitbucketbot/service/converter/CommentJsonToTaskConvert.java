package org.sadtech.bot.bitbucketbot.service.converter;

import org.sadtech.basic.context.exception.ConvertException;
import org.sadtech.bot.bitbucketbot.domain.TaskStatus;
import org.sadtech.bot.bitbucketbot.domain.entity.Task;
import org.sadtech.bot.bitbucketbot.dto.bitbucket.CommentJson;
import org.sadtech.bot.bitbucketbot.dto.bitbucket.CommentState;
import org.sadtech.bot.bitbucketbot.dto.bitbucket.Severity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CommentJsonToTaskConvert implements Converter<CommentJson, Task> {

    @Override
    public Task convert(CommentJson source) {
        final Task task = new Task();
        task.setId(source.getId());
        task.setAuthor(source.getAuthor().getName());
        task.setDescription(source.getText());
        task.setCreateDate(source.getCreatedDate());
        task.setBitbucketVersion(source.getVersion());
        task.setPullRequestId(source.getCustomPullRequestId());
        task.setStatus(convertState(source.getState()));
        task.setUrlApi(source.getCustomCommentApiUrl());
        task.setAnswers(
                source.getComments().stream()
                        .filter(json -> Severity.NORMAL.equals(json.getSeverity()))
                        .map(CommentJson::getId)
                        .collect(Collectors.toSet())
        );
        return task;
    }

    private TaskStatus convertState(CommentState state) {
        switch (state) {
            case RESOLVED:
                return TaskStatus.RESOLVED;
            case OPEN:
                return TaskStatus.OPEN;
            default:
                throw new ConvertException("Неподдерживаемый тип задачи");
        }
    }

}