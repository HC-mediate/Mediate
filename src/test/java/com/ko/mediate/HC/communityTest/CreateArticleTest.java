package com.ko.mediate.HC.communityTest;

import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.aws.domain.ArticleImageStorage;
import com.ko.mediate.HC.common.BaseApiTest;
import com.ko.mediate.HC.community.application.CommunityService;
import com.ko.mediate.HC.community.application.dto.request.CreateArticleDto;
import com.ko.mediate.HC.community.domain.Article;
import com.ko.mediate.HC.community.domain.ArticleImage;
import com.ko.mediate.HC.community.domain.Category;
import com.ko.mediate.HC.community.infra.JpaArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.ko.mediate.HC.factory.dto.ArticleDtoFactory.createRequestArticleDto;
import static com.ko.mediate.HC.factory.dto.UserInfoFactory.createUserInfo;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateArticleTest extends BaseApiTest {
    @Autowired
    CommunityService communityService;

    @Autowired
    JpaArticleRepository articleRepository;

    @Autowired
    ArticleImageStorage articleImageStorage;

    @Value("${cloud.aws.cloud_front.domain_name}")
    String cloudfront;

    UserInfo userInfo;

    @BeforeEach
    void initUserInfo() {
        userInfo = createUserInfo();
    }

    @DisplayName("글 생성 시 아이디를 반환한다")
    @Test
    void 글_생성_성공시_아이디를_반환한다() throws IOException {
        //given
        CreateArticleDto dto = createRequestArticleDto("title", "", Category.STUDY_QUESTION, null);
        //when
        Long id = communityService.createArticle(userInfo, dto);
        //then
        assertThat(articleRepository.findById(id).isPresent()).isTrue();
    }

    @DisplayName("글 이미지 첨부 시 S3 key와 CDN url을 저장한다.")
    @Test
    void 글_이미지_첨부시_S3_Key와_CDN_URL을_저장한다() throws IOException {
        //given
        CreateArticleDto dto = createRequestArticleDto("title", "", Category.TROUBLE_COUNSEL,
                new MultipartFile[]{new MockMultipartFile("image1.jpg", new byte[]{1}),
                        new MockMultipartFile("image2.jpg", new byte[]{1})});
        //when
        Long id = communityService.createArticle(userInfo, dto);
        //then
        Article result = articleRepository.findArticleByIdFetch(id).get();
        ArticleImage articleImage = result.getArticleImageList().get(0);
        assertThat(articleImage.getAttachedImage().getImageKey().startsWith(ArticleImageStorage.UPLOAD_DIRECTORY)).isTrue();
        assertThat(articleImage.getAttachedImage().getImageUrl().startsWith(cloudfront + "/" +ArticleImageStorage.UPLOAD_DIRECTORY)).isTrue();
    }
}
