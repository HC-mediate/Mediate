package com.ko.mediate.HC;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.auth.infra.JpaAccountRepository;
import com.ko.mediate.HC.homework.infra.JpaHomeworkRepository;
import com.ko.mediate.HC.jwt.TokenProvider;
import com.ko.mediate.HC.tutee.infra.JpaTuteeRepository;
import com.ko.mediate.HC.tutee.domain.Tutee;
import com.ko.mediate.HC.tutor.infra.JpaTutorRepository;
import com.ko.mediate.HC.tutor.domain.Tutor;
import com.ko.mediate.HC.tutoring.domain.AcademicInfo;
import com.ko.mediate.HC.tutoring.domain.Curriculum;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class HcApplicationTests {
	@Autowired
	protected ObjectMapper objectMapper;
	@Autowired
	protected JpaTutorRepository tutorRepository;
	@Autowired
	protected JpaTuteeRepository tuteeRepository;
	@Autowired
	protected JpaHomeworkRepository homeworkRepository;
	@Autowired
	protected JpaAccountRepository accountRepository;
	@Autowired
	private TokenProvider tokenProvider;
	@Autowired
	private PasswordEncoder passwordEncoder;

	protected String token;
	protected final String BEARER = "Bearer ";
	@Test
	void contextLoads() {
	}

	@BeforeEach
	void saveAccount(){
		Account account = new Account("tutor1", passwordEncoder.encode("tutor"), "튜터", "010-1234-5678", "ROLE_TUTOR");
		Tutor tutor = new Tutor("tutor1", "튜터", "address_tutor", Curriculum.HIGH, new AcademicInfo("아무대학교", "컴공", "3학년"),
				null);
		Tutee tutee = new Tutee("tutee1", "튜티", "address_tutee", new AcademicInfo("아무고등학교", "인문계", "2학년"),
				null);

		accountRepository.saveAndFlush(account);
		tutorRepository.saveAndFlush(tutor);
		tuteeRepository.saveAndFlush(tutee);

		List<GrantedAuthority> authorityList = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken("tutor1", "tutor", authorityList);
		token = tokenProvider.createToken(authenticationToken);
	}

}
