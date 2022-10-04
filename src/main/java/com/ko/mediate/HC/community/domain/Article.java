package com.ko.mediate.HC.community.domain;

import com.ko.mediate.HC.auth.domain.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_article")
public class Article extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "email")
    private Account account;

    @Column(name = "view_count")
    private Long viewCount = 0L;

    @Column(name = "like_count")
    private Long likeCount = 0L;

    @Column(name = "reply_count")
    private Long replyCount = 0L;

    @OneToMany(
            mappedBy = "article",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ArticleImage> articleImageList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Category category;

    @Builder
    public Article(String title, String content, Account account, Category category) {
        this.title = title;
        this.content = content;
        this.account = account;
        this.category = category;
    }

    public void addArticleImage(ArticleImage articleImage) {
        if (Objects.nonNull(articleImage)) {
            articleImage.changeArticleImage(this);
            this.articleImageList.add(articleImage);
        }
    }

    public boolean isAuthorByEmail(String email){
        return this.account.getEmail().equals(email);
    }
}
