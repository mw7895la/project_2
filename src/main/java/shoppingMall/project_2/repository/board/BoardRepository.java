package shoppingMall.project_2.repository.board;

import org.springframework.stereotype.Repository;
import shoppingMall.project_2.domain.board.Board;
import shoppingMall.project_2.domain.board.BoardUpdateForm;

import java.util.List;


public interface BoardRepository {

    Board save(Board board);

    void delete(long id);

    Board findById(long id);

    List<Board> findAll();

    void update(long id, BoardUpdateForm updateForm);
}
