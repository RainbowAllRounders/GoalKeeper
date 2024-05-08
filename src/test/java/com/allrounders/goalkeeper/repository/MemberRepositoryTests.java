package com.allrounders.goalkeeper.repository;

import com.allrounders.goalkeeper.domain.Member;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            Member member = Member.builder()
                    .email("suhyeon"+i+"@naver.com")
                    .password("000"+i)
                    .nickname("수현짱"+i)
                    .curPoint(1000)
                    .build();

            Member result = memberRepository.save(member);
            log.info("member_id" + result.getMemberId());
        });
    }
}
