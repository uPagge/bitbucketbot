package org.sadtech.bot.bitbucketbot.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.sadtech.bot.bitbucketbot.domain.IdAndStatusPr;
import org.sadtech.bot.bitbucketbot.domain.Pagination;
import org.sadtech.bot.bitbucketbot.domain.PullRequestStatus;
import org.sadtech.bot.bitbucketbot.domain.ReviewerStatus;
import org.sadtech.bot.bitbucketbot.domain.entity.PullRequest;
import org.sadtech.bot.bitbucketbot.repository.jpa.PullRequestsRepository;
import org.sadtech.bot.bitbucketbot.service.PullRequestsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PullRequestsServiceImpl implements PullRequestsService {

    private final PullRequestsRepository pullRequestsRepository;

    @Override
    public boolean existsByBitbucketIdAndReposId(@NonNull Long bitbucketId, @NonNull Long repositoryId) {
        return pullRequestsRepository.existsByBitbucketIdAndRepositoryId(bitbucketId, repositoryId);
    }

    @Override
    public Set<PullRequest> getAllById(@NonNull Set<Long> pullRequestJsonId) {
        return pullRequestsRepository.findAllByIdIn(pullRequestJsonId);
    }

    @Override
    @Transactional
    public List<PullRequest> addAll(@NonNull Collection<PullRequest> pullRequests) {
        return pullRequestsRepository.saveAll(pullRequests);
    }

    @Override
    public List<PullRequest> updateAll(@NonNull Collection<PullRequest> pullRequests) {
        final List<PullRequest> updatePullRequests = pullRequests.stream()
                .filter(pullRequest -> pullRequestsRepository.existsById(pullRequest.getId()))
                .collect(Collectors.toList());
        return pullRequestsRepository.saveAll(updatePullRequests);
    }

    @Override
    public Optional<Long> getIdByBitbucketIdAndReposId(@NonNull Long bitbucketId, @NonNull Long repositoryId) {
        return pullRequestsRepository.findIdByBitbucketIdAndRepositoryId(bitbucketId, repositoryId);
    }

    @Override
    @Transactional
    public void deleteAll(@NonNull Set<Long> id) {
        pullRequestsRepository.deleteAllByIdIn(id);
    }

    @NonNull
    @Override
    public List<PullRequest> getAllByReviewerAndStatuses(String login, ReviewerStatus reviewerStatus, Set<PullRequestStatus> statuses) {
        return pullRequestsRepository.findAllByReviewerAndStatuses(login, reviewerStatus, statuses);
    }

    @Override
    public List<PullRequest> getAllByAuthorAndReviewerStatus(@NonNull String login, @NonNull ReviewerStatus status) {
        return pullRequestsRepository.findAllByAuthorAndReviewerStatus(login, status);
    }

    @Override
    public Set<Long> getAllId() {
        return pullRequestsRepository.findAllIds();
    }

    @Override
    public Set<IdAndStatusPr> getAllId(Set<PullRequestStatus> statuses) {
        return pullRequestsRepository.findAllIdByStatusIn(statuses);
    }

    @Override
    public Page<PullRequest> getAll(@NonNull Pagination pagination) {
        return pullRequestsRepository.findAll(PageRequest.of(pagination.getPage(), pagination.getSize()));
    }

    @Override
    public List<PullRequest> getAllByAuthor(@NonNull String login, @NonNull LocalDateTime dateFrom, @NonNull LocalDateTime dateTo) {
        return pullRequestsRepository.findAllByAuthorAndDateBetween(login, dateFrom, dateTo);
    }


}