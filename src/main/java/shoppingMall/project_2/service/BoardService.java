package shoppingMall.project_2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingMall.project_2.domain.board.Board;
import shoppingMall.project_2.domain.board.BoardUpdateForm;
import shoppingMall.project_2.repository.board.BoardRepository;

import java.util.List;

@Service
@Slf4j
public class BoardService {

    private final BoardRepository repository;

    @Autowired
    public BoardService(BoardRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Board save(Board board) {
        return repository.save(board);
    }

    @Transactional
    public void update(Long id, BoardUpdateForm updateForm) {
        repository.update(id, updateForm);
    }

    public void delete(Long id) {
        repository.delete(id);

    }

    public Board findById(Long id) {
        return repository.findById(id);
    }

    public List<Board> findAll() {
        return repository.findAll();
    }

}
