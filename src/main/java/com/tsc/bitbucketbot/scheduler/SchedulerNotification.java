package com.tsc.bitbucketbot.scheduler;

import com.tsc.bitbucketbot.domain.MessageSend;
import com.tsc.bitbucketbot.domain.ReviewerStatus;
import com.tsc.bitbucketbot.domain.entity.PullRequest;
import com.tsc.bitbucketbot.domain.entity.User;
import com.tsc.bitbucketbot.service.MessageSendService;
import com.tsc.bitbucketbot.service.PullRequestsService;
import com.tsc.bitbucketbot.service.UserService;
import com.tsc.bitbucketbot.utils.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerNotification {

    private final UserService userService;
    private final PullRequestsService pullRequestsService;
    private final MessageSendService messageSendService;

    @Scheduled(cron = "0 9 * * 1-5")
    public void goodMorning() {
        List<User> allRegister = userService.getAllRegister();
        for (User user : allRegister) {
            List<PullRequest> pullRequests = pullRequestsService.getAllByReviewerAndStatuses(
                    user.getLogin(),
                    ReviewerStatus.NEEDS_WORK
            );
            messageSendService.add(
                    MessageSend.builder()
                            .telegramId(3000811L)
                            .message(Message.goodMorningStatistic(user.getFullName(), pullRequests))
                            .build()
            );
        }
    }

}
