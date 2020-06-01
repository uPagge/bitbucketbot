package org.sadtech.bot.bitbucketbot.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.sadtech.bot.bitbucketbot.domain.PullRequestStatus;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

/**
 * TODO: Добавить описание класса.
 *
 * @author upagge [31.01.2020]
 */
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class PullRequest {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bitbucket_pr_id")
    private Long bitbucketId;

    @Column(name = "repository_id")
    private Long repositoryId;

    @Column(name = "project_key")
    private String projectKey;

    @Column(name = "repository_slug")
    private String repositorySlug;

    @Column(name = "version")
    private Integer version;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_login")
    private User author;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "pull_request_id")
    private List<Reviewer> reviewers;

    @Column(name = "url")
    private String url;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PullRequestStatus status;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "conflict")
    private boolean conflict;

}