package org.sadtech.bot.bitbucketbot.domain.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sadtech.bot.bitbucketbot.domain.ReviewerStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TODO: Добавить описание класса.
 *
 * @author upagge [01.02.2020]
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviewer")
@EqualsAndHashCode(of = "id")
public class Reviewer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_login")
    private String user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReviewerStatus status;

}