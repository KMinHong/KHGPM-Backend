package com.example.demo.forTest.boardComment;

import com.example.demo.domain.forTest.board.controller.response.CommentResponse;
import com.example.demo.domain.forTest.board.entity.Comment;
import com.example.demo.domain.forTest.board.entity.TestBoard;
import com.example.demo.domain.forTest.board.repository.CommentRepository;
import com.example.demo.domain.forTest.board.repository.TestBoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class BoardCommentTest {

    @Autowired
    private TestBoardRepository testBoardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void 게시물_저장() {
        TestBoard board = new TestBoard("테스트", "뭐");
        testBoardRepository.save(board);
    }

    @Test
    public void 덧글_저장() {
        Optional<TestBoard> mayTestBoard = testBoardRepository.findById(3L);
        TestBoard testBoard = mayTestBoard.get();

        Comment comment = new Comment("시작");

        testBoard.setComment(comment);
        testBoardRepository.save(testBoard);

        commentRepository.save(comment);
    }

    @Test
    public void 게시물_덧글_출력() {
        List<Comment> commentList = commentRepository.findAllCommentsByBoardId(2L);
        List<CommentResponse> commentResponses = new ArrayList<>();

        for (Comment comment: commentList) {
            commentResponses.add(new CommentResponse(comment.getContent()));
        }

        System.out.println(commentResponses);
    }

    @Test
    public void 덧글_수정() {
        Optional<Comment> maybeComment = commentRepository.findById(4L);
        Comment comment = maybeComment.get();

        comment.changeContent("이러쿵 저러쿵 요러쿵");
        commentRepository.save(comment);
    }

    @Test
    public void 덧글_삭제() {
        commentRepository.deleteById(5L);
    }

    @Test
    public void 게시글_삭제() {
        final Long boardId = 3L;
        List<Comment> commentList = commentRepository.findAllCommentsByBoardId(boardId);

        for (Comment comment: commentList) {
            System.out.println("comment 내용: " + comment.getContent());
            commentRepository.delete(comment);
        }

        testBoardRepository.deleteById(boardId);
    }
}