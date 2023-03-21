package shoppingMall.project_2.repository.member;

import shoppingMall.project_2.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    void update(String userId, String newPassword);     //비밀번호 변경.

    Member findById(String userId);

    List<Member> findAll();

    Optional<Member> findByLoginId(String loginId);

    void deleteAll();

    void deleteById(String userId, String password);
}
