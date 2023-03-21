package shoppingMall.project_2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shoppingMall.project_2.domain.member.Member;
import shoppingMall.project_2.repository.member.MemberRepository;

import java.util.Optional;

@Service
@Slf4j
public class LoginService {

    private final MemberRepository memberRepository;

    @Autowired
    public LoginService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member login(String userId, String password) {
        Optional<Member> byLoginId = memberRepository.findByLoginId(userId);

        return byLoginId.filter(member -> member.getPassword().equals(password)).orElse(null);
    }



}
