package shoppingMall.project_2.repository.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.stereotype.Repository;
import shoppingMall.project_2.domain.member.Grade;
import shoppingMall.project_2.domain.member.Member;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;


@Repository
@Slf4j
public class MemoryMemberRepository implements MemberRepository {

    private final DataSource dataSource;
    private final SQLExceptionTranslator exTranslator;

    @Autowired
    public MemoryMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        this.exTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into member(username,userId,password,grade) values(?,?,?,?)";

        Connection con = null;
        PreparedStatement pstmt = null;
        try{
            con=getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getUsername());
            pstmt.setString(2, member.getUserId());
            pstmt.setString(3, member.getPassword());
            pstmt.setString(4, member.getGrade().name());
            pstmt.executeUpdate();
            return member;

        }catch(SQLException e){
            DataAccessException ex = exTranslator.translate("insert", sql, e);
            throw ex;
        }finally{
            close(con, pstmt, null);
        }


    }

    @Override
    public void update(String userId,String newPassword) {

        //Member findMember = findById(id);     //처음엔 해당 id를 찾아와서 그 데이터를 변경하려 했는데 필요없다.
        String sql = "update member set password=? where userId=?";
        Connection con = null;
        PreparedStatement pstmt =null;


        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, newPassword);
            pstmt.setString(2, userId);
            pstmt.executeUpdate();

        }catch(SQLException e){
            DataAccessException ex = exTranslator.translate("update",sql ,e);
        }
    }

    @Override
    public Member findById(String userId) {
        String sql = "select * from member where userId=?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                Member member =new Member();
                member.setUsername(rs.getString("username"));
                member.setPassword(rs.getString("password"));
                member.setGrade(Grade.valueOf(rs.getString("grade")));

                return member;
            }else{
                throw new NoSuchElementException("not found user ID ");
            }
        } catch (SQLException e) {
            DataAccessException ex = exTranslator.translate("select", sql, e);
            throw ex;
        }finally{
            close(con, pstmt, rs);
        }
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from member";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs =null;
        List<Member> members = new ArrayList<>();

        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setUserId(rs.getString("userId"));
                member.setUsername(rs.getString("username"));
                member.setPassword(rs.getString("password"));
                member.setGrade(Grade.valueOf(rs.getString("grade")));
                members.add(member);
            }
            return members;


        }catch(SQLException e){
            DataAccessException ex = exTranslator.translate("select", sql, e);
            throw ex;
        }finally{
            close(con, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        List<Member> members = findAll();

        for (Member member : members) {
            if (member.getUserId().equals(loginId)) {
                return Optional.of(member);
            }
        }
        return Optional.empty();

    }

    @Override
    public void deleteAll() {
        String sql = "delete from member";

        Connection con = null;
        PreparedStatement pstmt = null;

        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.executeUpdate();

        }catch(SQLException e){
            DataAccessException ex = exTranslator.translate("delete", sql, e);
            throw ex;
        }finally{
            close(con, pstmt, null);
        }
    }

    @Override
    public void deleteById(String userId,String password) {
        String sql = "delete from member where userId=? and password=?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            DataAccessException ex = exTranslator.translate("delete", sql, e);
            throw ex;
        }finally{
            close(con, pstmt, null);
        }

    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        DataSourceUtils.releaseConnection(con, dataSource);
    }

    private Connection getConnection(){
        Connection con = DataSourceUtils.getConnection(dataSource);

        log.info("get Connection ={}, classs={}", con, con.getClass());
        return con;
    }
}
