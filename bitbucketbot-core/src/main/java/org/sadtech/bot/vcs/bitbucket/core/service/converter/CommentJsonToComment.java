package org.sadtech.bot.vcs.bitbucket.core.service.converter;

import org.sadtech.bot.vcs.bitbucket.sdk.domain.CommentJson;
import org.sadtech.bot.vcs.bitbucket.sdk.domain.Severity;
import org.sadtech.bot.vcs.core.utils.StringUtils;
import org.sadtech.bot.vsc.bitbucketbot.context.domain.entity.Comment;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CommentJsonToComment implements Converter<CommentJson, Comment> {

    @Override
    public Comment convert(CommentJson source) {
        final Comment comment = new Comment();
        comment.setId(source.getId());
        comment.setCreateDate(source.getCreatedDate());
        comment.setAuthor(source.getAuthor().getName());
        comment.setPullRequestId(source.getCustomPullRequestId());
        comment.setMessage(StringUtils.cutOff(source.getText(), 490));
        comment.setUrlApi(source.getCustomCommentApiUrl());
        comment.setBitbucketVersion(source.getVersion());
        comment.setAnswers(
                source.getComments().stream()
                        .filter(json -> Severity.NORMAL.equals(json.getSeverity()))
                        .map(CommentJson::getId)
                        .collect(Collectors.toSet())
        );
        return comment;

    }

}
