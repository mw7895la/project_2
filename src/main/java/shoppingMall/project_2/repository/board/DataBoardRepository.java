package shoppingMall.project_2.repository.board;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import shoppingMall.project_2.domain.board.Board;
import shoppingMall.project_2.domain.board.BoardUpdateForm;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
@Slf4j
public class DataBoardRepository implements BoardRepository{

    private final DataSource dataSource;
    private final SQLExceptionTranslator exTranslator;      //스프링 예외 변환을 위한 인터페이스  체크 예외 -> 언체크 예외

    public DataBoardRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        this.exTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
    }

    @Override
    public Board save(Board board) {
        String sql = "insert into Board(title,content,userId,viewCount) values(?,?,?,?)";

        Connection con= null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, board.getTitle());
            pstmt.setString(2, board.getContent());
            pstmt.setString(3, board.getUserId());
            pstmt.setLong(4, board.getViewCount());
            pstmt.executeUpdate();
            return board;
        } catch (SQLException e) {
            DataAccessException ex = exTranslator.translate("save", sql, e);
            throw ex;
        }finally{
            close(con, pstmt, null);
        }
    }

    @Override
    public void delete(long id) {
        String sql = "delete from Board where id=?";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            DataAccessException ex = exTranslator.translate("delete", sql, e);
            throw ex;
        }finally{
            close(con, pstmt, null);
        }
    }

    @Override
    public Board findById(long id) {
        String sql = "select id,title,content,userId,viewCount,regDate from Board where id=?";
        Connection con =null;
        PreparedStatement pstmt= null;
        ResultSet rs= null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs= pstmt.executeQuery();

            if (rs.next()) {
                Board board = new Board();
                board.setId(rs.getLong("id"));
                board.setTitle(rs.getString("title"));
                board.setContent(rs.getString("content"));
                board.setUserId(rs.getString("userId"));
                board.setViewCount(rs.getInt("viewCount"));
                board.setRegDate(rs.getString("regDate"));
                return board;
            }else{
                throw new NoSuchElementException("board not found .... board id = " + id);
            }

        } catch (SQLException e) {
            DataAccessException ex = exTranslator.translate("select", sql, e);
            throw ex;
        }finally{
            close(con, pstmt, rs);
        }

    }

    @Override
    public List<Board> findAll() {
        String sql = "select id,title,content,userId,viewCount,regDate from Board";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Board> list = new ArrayList<>();
            while (rs.next()) {
                Board board = new Board();
                board.setId(rs.getLong("id"));
                board.setTitle(rs.getString("title"));
                board.setContent(rs.getString("content"));
                board.setUserId(rs.getString("userId"));
                board.setViewCount(rs.getInt("viewCount"));
                board.setRegDate(rs.getString("regDate"));
                list.add(board);
            }

            return list;
        } catch (SQLException e) {
            DataAccessException ex = exTranslator.translate("select", sql, e);
            throw ex;
        }finally{
            close(con, pstmt, rs);
        }
    }

    @Override
    public void update(long id, BoardUpdateForm updateForm) {
        String sql = "update Board set title=?, content=? where id=?";

        Connection con = null;
        PreparedStatement pstmt= null;

        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, updateForm.getTitle());
            pstmt.setString(2, updateForm.getContent());
            pstmt.setLong(3, id);
            pstmt.executeUpdate();
        }catch(SQLException e){
            DataAccessException ex = exTranslator.translate("update", sql, e);
            throw ex;
        }finally{
            close(con, pstmt, null);
        }
    }

    /**
     * 리소스 반환
     */
    public void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        DataSourceUtils.releaseConnection(con, dataSource);
    }

    private Connection getConnection() {
        Connection con = DataSourceUtils.getConnection(dataSource);
        return con;
    }
}
