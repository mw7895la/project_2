package shoppingMall.project_2.web.board;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import shoppingMall.project_2.domain.board.Board;
import shoppingMall.project_2.service.BoardService;

@Controller
@Slf4j
@RequestMapping("/board")
public class BoardController {

    private final BoardService service;

    @Autowired
    public BoardController(BoardService service) {
        this.service = service;
    }


    @GetMapping("/add")
    public String addForm(@Validated @ModelAttribute Board board) {
        return "boards/writeForm";
    }
}
