package com.ko.mediate.HC.communityTest;

import static com.ko.mediate.HC.auth.AccountFactory.createSignUpDto;

import com.ko.mediate.HC.auth.application.AccountService;
import com.ko.mediate.HC.common.BaseApiTest;
import com.ko.mediate.HC.community.infra.JpaArticleRepository;
import com.ko.mediate.HC.factory.dto.UserInfoFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseArticleApiTest extends BaseApiTest {

    @Autowired
    AccountService accountService;

    @Autowired
    JpaArticleRepository articleRepository;

    @BeforeEach
    void beforeEach() {
        accountService.saveAccount(createSignUpDto(UserInfoFactory.email));
    }

    @AfterEach
    void afterEach(){
        articleRepository.deleteAll();
        accountRepository.deleteAll();
    }
}
