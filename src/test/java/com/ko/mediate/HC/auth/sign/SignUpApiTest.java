package com.ko.mediate.HC.auth.sign;

import com.ko.mediate.HC.auth.application.AccountService;
import com.ko.mediate.HC.auth.application.dto.request.SignUpDto;
import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.common.BaseApiTest;
import com.ko.mediate.HC.common.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.ko.mediate.HC.auth.AccountFactory.createSignUpDto;
import static com.ko.mediate.HC.auth.AccountFactory.createSignUpDtoWithNickName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SignUpApiTest extends BaseApiTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    AccountService accountService;
    final String existEmail = "test123@naver.com";

    @Test
    void 회원가입_성공시_엔티티를_확인하고_201을_반환한다() throws Exception {
        // given
        SignUpDto dto = createSignUpDto("test@naver.com");

        // when, then
        mvc.perform(
                        post("/api/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        Account account = accountRepository.findAccountByEmail(dto.getEmail()).get();
        assertThat(account.getEmail()).isEqualTo(dto.getEmail());
        assertThat(account.getName()).isEqualTo(dto.getName());
        assertThat(account.getNickname()).isEqualTo(dto.getNickname());
        assertThat(account.getPhoneNum()).isEqualTo(dto.getPhoneNum());
    }

    @Test
    void 회원가입시_이메일이_중복되면_400을_반환한다() throws Exception {
        // given
        SignUpDto dto = createSignUpDto(existEmail);
        accountService.saveAccount(dto);

        // when, then
        mvc.perform(
                        post("/api/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(containsString(ErrorCode.EMAIL_ALREADY_EXIST.getCode())))
                .andDo(print());
    }

    @Test
    void 회원가입_닉네임_중복시_400에러를_던진다() throws Exception {
        //given
        SignUpDto dto = createSignUpDto(existEmail);
        SignUpDto existNickNameDto = createSignUpDtoWithNickName("test12@naver.com", dto.getNickname());
        accountService.saveAccount(dto);
        //when, then
        mvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(existNickNameDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(containsString(ErrorCode.NICKNAME_ALREADY_EXIST.getCode())))
                .andDo(print());
    }
}
