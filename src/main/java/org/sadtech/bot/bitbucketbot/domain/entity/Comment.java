package org.sadtech.bot.bitbucketbot.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Comment {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "pr_url")
    private String prUrl;

    @Column(name = "telegram")
    private Long telegram;

    @Column(name = "date")
    private LocalDateTime date;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "comment_tree", joinColumns = @JoinColumn(name = "parent_id"))
    @Column(name = "child_id")
    private Set<Long> answers;

}