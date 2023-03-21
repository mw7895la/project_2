package shoppingMall.project_2.repository.member;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shoppingMall.project_2.domain.member.Grade;
import shoppingMall.project_2.domain.member.Member;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
class MemoryMemberRepositoryTest {

    MemoryMemberRepository memberRepository;

    /*@AfterEach
    void clear(){
        memberRepository.clear();
    }


    @Test
    void save() {
        Member memberA = new Member("kimwoojin", "mw7895la", "kimwoo12", Grade.ADMIN);

        Member savedMember = memberRepository.save(memberA);

        Member findMember = memberRepository.findById(savedMember.getId());
        Assertions.assertThat(savedMember.getId()).isEqualTo(findMember.getId());

    }


    @Test
    void findAll() {
        Member memberA = new Member("kimwoojin1", "mw7895la", "kimwoo12", Grade.ADMIN);
        Member memberB = new Member("kimwoojin2", "mw7895la", "kimwoo12", Grade.VIP);
        Member memberC = new Member("kimwoojin3", "mw7895la", "kimwoo12", Grade.VIP);
        memberRepository.save(memberA);
        memberRepository.save(memberB);
        memberRepository.save(memberC);

        List<Member> all = memberRepository.findAll();
        Assertions.assertThat(all.size()).isEqualTo(3);

    }

    @Test
    void findByLoginId() {
        Member memberA = new Member("kimwoojin1", "mw7895la", "kimwoo12", Grade.ADMIN);
        Member savedMember = memberRepository.save(memberA);

        Optional<Member> byLoginId = memberRepository.findByLoginId(savedMember.getUserId());
        Member findMember = byLoginId.get();
        log.info("findMember ={}", findMember.toString());

    }*/
}