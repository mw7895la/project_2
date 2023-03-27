package shoppingMall.project_2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import shoppingMall.project_2.domain.member.Member;
import shoppingMall.project_2.repository.member.MemberRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public MemberService(MemberRepository memberRepository, DataSource dataSource) {
        this.memberRepository = memberRepository;
        this.transactionManager = new DataSourceTransactionManager(dataSource);
    }

    public void save(Member member){

        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        log.info("transaction ={}", TransactionSynchronizationManager.isActualTransactionActive());

        try{
            memberRepository.save(member);
            transactionManager.commit(status);
        }catch(DataAccessException e){
            log.info("트랜잭션 처리중 예외 발생");
            transactionManager.rollback(status);
        }
    }

    public Member findById(String userId) {
        return memberRepository.findById(userId);

    }

    /**
     * 예외 터질 수 있는 곳.
     */
    public void update(String userId,String newPassword) {

        memberRepository.update(userId, newPassword);
    }

    public Optional<Member> findByloginId(String loginId) {
        return memberRepository.findByLoginId(loginId);
    }

    public void deleteAll() {
        memberRepository.deleteAll();
    }

    public void deleteById(String userId, String password) {
        memberRepository.deleteById(userId, password);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();

    }


}
