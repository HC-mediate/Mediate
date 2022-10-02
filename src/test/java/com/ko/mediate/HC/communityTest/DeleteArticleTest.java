package com.ko.mediate.HC.communityTest;

import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.common.BaseApiTest;
import com.ko.mediate.HC.common.exception.MediateUnAuthorizedException;
import com.ko.mediate.HC.community.application.CommunityService;
import com.ko.mediate.HC.community.application.dto.request.CreateArticleDto;
import com.ko.mediate.HC.community.domain.Category;
import com.ko.mediate.HC.community.infra.JpaArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static com.ko.mediate.HC.factory.dto.ArticleDtoFactory.createRequestArticleDto;
import static com.ko.mediate.HC.factory.dto.UserInfoFactory.createUserInfo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("글 삭제 테스트")
public class DeleteArticleTest extends BaseApiTest {
    @Autowired
    CommunityService communityService;
    @Autowired
    JpaArticleRepository articleRepository;
    UserInfo userInfo;
    CreateArticleDto createArticleDto;

    @BeforeEach
    void init() {
        userInfo = createUserInfo();
        createArticleDto = createRequestArticleDto("title", "content", Category.STUDY_QUESTION, null);
    }

    @Test
    void 글_제거_성공시_레포지토리_조회가_안돼야_한다() throws IOException {
        //given
        Long articleId = communityService.createArticle(userInfo, createArticleDto);
        //when
        communityService.deleteArticle(userInfo, articleId);
        //then
        assertThat(articleRepository.existsById(articleId)).isFalse();
    }

    @Test
    void 작성자가_아니면_글_삭제시_예외를_던진다() throws IOException {
        //given
        Long articleId = communityService.createArticle(userInfo, createArticleDto);
        userInfo = new UserInfo(2L, "1234", "1234", "ROLE_USER");
        //when, then
        assertThatThrownBy(() -> communityService.deleteArticle(userInfo, articleId))
                .isInstanceOf(MediateUnAuthorizedException.class);
    }
}
