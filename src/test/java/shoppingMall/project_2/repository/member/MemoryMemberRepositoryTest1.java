package shoppingMall.project_2.repository.member;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import shoppingMall.project_2.domain.member.Grade;
import shoppingMall.project_2.domain.member.Member;
import shoppingMall.project_2.service.MemberService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static shoppingMall.project_2.domain.member.Grade.*;

@SpringBootTest
@Slf4j
class MemoryMemberRepositoryTest1 {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;



    @Test
    @Commit
    void save(){
        Member memberA = new Member("kim", "mw1", "12345");
        memberA.setGrade(VIP);
        memberService.save(memberA);

        Member memberB = new Member("kim", "mw2", "12345");
        memberB.setGrade(VIP);
        memberService.save(memberB);

        Member memberC = new Member("kim", "mw3", "12345");
        memberC.setGrade(VIP);
        memberService.save(memberC);
    }

    @Test
    void findAll() {


        List<Member> all = memberService.findAll();

        for (Member member : all) {
            log.info("member = {}", member.toString());
        }
    }

    @Test
    void findById() {

        Member findMember = memberService.findById("mw");
        assertThat(findMember.getPassword()).isEqualTo("12345");
        log.info("grade ={}", findMember.getGrade());
    }

    @Test
    void deleteById(){

        memberService.deleteById("mw1","12345");

    }
}