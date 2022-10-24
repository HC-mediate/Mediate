package com.ko.mediate.HC.auth.sign;

import static com.ko.mediate.HC.auth.AccountFactory.createAccount;
import static com.ko.mediate.HC.auth.AccountFactory.createSignInDto;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ko.mediate.HC.auth.AccountFactory;
import com.ko.mediate.HC.auth.application.dto.request.SignInDto;
import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.common.BaseApiTest;
import com.ko.mediate.HC.common.ErrorCode;
import com.ko.mediate.HC.jwt.TokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("로그인 api 테스트")
public class SignInApiTest extends BaseApiTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    TokenProvider tokenProvider;

    @Test
    void 로그인_성공시_리프레시와_액세스토큰을_반환한다() throws Exception {
        // given
        SignInDto dto = createSignInDto(saveEmail, "1234");

        // when, then
        mvc.perform(
                        post("/api/signin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.refreshToken").exists())
                .andExpect(jsonPath("$.accessToken").exists())
                .andDo(print());
    }

    @Test
    void 리프레쉬_성공시_액세스토큰을_반환한다() throws Exception {
        mvc.perform(post("/api/refresh").header("Refresh", refreshToken))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accessToken").isString())
                .andDo(print());
    }

    @Test
    void 비밀번호가_틀리면_예외_메시지를_보낸다() throws Exception {
        // given
        SignInDto dto = createSignInDto(saveEmail, "아무비빌번호");

        // when, then
        mvc.perform(
                        post("/api/signin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(jsonPath("$.code").value(ErrorCode.INCORRECT_PASSWORD.getCode()))
                .andExpect(jsonPath("$.description").value(
                        ErrorCode.INCORRECT_PASSWORD.getDescription()))
                .andDo(print());
    }

    @Test
    void 이메일이_없으면_400을_던진다() throws Exception {
        // given
        SignInDto dto = createSignInDto("qwer@gmail.com", "1234");

        // when, then
        mvc.perform(
                        post("/api/signin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(jsonPath("$.code").value(ErrorCode.NO_ACCOUNT_EXISTS.getCode()))
                .andExpect(jsonPath("$.description").value(
                        ErrorCode.NO_ACCOUNT_EXISTS.getDescription()))
                .andDo(print());
    }

    @Test
    void 활성화되지_않은_계정은_예외를_던진다() throws Exception {
        //given
        Account account = createAccount("deactivated@naver.com",
                passwordEncoder.encode(AccountFactory.password));
        account.deactivate();
        accountRepository.saveAndFlush(account);
        SignInDto dto = createSignInDto(account.getEmail(), AccountFactory.password);
        //when, then
        mvc.perform(post("/api/signin").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(jsonPath("$.code").value(ErrorCode.DEACTIVATED_ACCOUNT.getCode()))
                .andExpect(jsonPath("$.description").value(
                        ErrorCode.DEACTIVATED_ACCOUNT.getDescription()))
                .andDo(print());
    }
}
