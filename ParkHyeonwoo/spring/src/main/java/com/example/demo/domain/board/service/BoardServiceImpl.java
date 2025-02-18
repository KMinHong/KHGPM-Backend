package com.example.demo.domain.board.service;

import com.example.demo.domain.board.controller.request.BoardRequest;
import com.example.demo.domain.board.entity.Board;
import com.example.demo.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    final private BoardRepository boardRepository;

    // @RequiredArgsConstructor 가 대신 해줌(초기화 되지 않은 final 필드에 대해 생성자 생성해 준다)
//    public BoardServiceImpl(BoardRepository boardRepository) {
//        this.boardRepository = boardRepository;
//    }

    @Override
    public void register(BoardRequest boardRequest) {
        Board board = new Board();
        board.setTitle(boardRequest.getTitle());
        board.setWriter(boardRequest.getWriter());
        board.setContent(boardRequest.getContent());

        boardRepository.save(board);
    }
    @Override
    public List<Board> list() {
        return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardId")); // 있는거 다 찾아서 오름차순 나열
    }

    @Override
    public Board read(Long boardId) {
        // Optional -> 일 수도 있고 아닐 수도 있고
        Optional<Board> maybeBoard = boardRepository.findById(boardId);

        if(maybeBoard.isEmpty()) {
            log.info("읽을 수가 없음");
            return null;
        }

        return maybeBoard.get();
    }

    @Override
    public void remove(Long boardId) {
        boardRepository.deleteById(boardId);
    }

    @Override
    public Board modify(Long boardId, BoardRequest boardRequest) {
        Optional<Board> maybeBoard = boardRepository.findById(boardId);

        if (maybeBoard.isEmpty()) {
            System.out.println("Board 정보를 찾지 못했습니다: " + boardId);
            return null;
        }

        Board board = maybeBoard.get();
        board.setTitle(boardRequest.getTitle());
        board.setContent(boardRequest.getContent());

        boardRepository.save(board);

        return board;
    }
}