package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.domain.Comment;
import org.example.domain.Great;
import org.example.domain.board.*;
import org.example.domain.User.User;
import org.example.domain.enums.BoardCategory;
import org.example.repository.BoardRepository;
import org.example.repository.CommentRepository;
import org.example.repository.GreatRepository;
import org.example.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final GreatRepository greatRepository;
    private final CommentRepository commentRepository;

    public Page<Board> getBoardList(BoardCategory boardCategory, PageRequest request, String keyword){
        if (keyword != null && boardCategory != BoardCategory.none){
            return boardRepository.findByBoardCategoryAndTitleOrAuthor(boardCategory, keyword, request);
        } else if (boardCategory != BoardCategory.none) {  // 검색 키워드가 없으면
            return boardRepository.findAllByBoardCategory(boardCategory, request);
        } else if (keyword != null) {  // 카테고리가 없으면
            return boardRepository.findAllByTitleContainingOrAuthorContaining(keyword, keyword, request);
        } else { // 둘 다 없으면
            return boardRepository.findAll(request);
        }
    }
    @Transactional
    public Long writeBoard(BoardCreateRequest request, String email, Authentication authentication) throws IOException{
        User loginUser = userRepository.findByEmail(email).orElseThrow(NullPointerException::new);
        Board savedBoard = boardRepository.save(request.toEntity(loginUser));

        return savedBoard.getId();
    }

    @Transactional
    public Long editBoard(Long boardId, String category, BoardDto dto) throws IOException {
        Optional<Board> optBoard = boardRepository.findById(boardId);

        // id에 해당하는 게시글이 없거나 카테고리가 일치하지 않으면 null return
        if (optBoard.isEmpty() || !optBoard.get().getBoardCategory().toString().equalsIgnoreCase(category)) {
            return null;
        }

        Board board = optBoard.get();

        board.update(dto);

        return board.getId();
    }

    public List<Board> getNotice(BoardCategory boardCategory){
        return boardRepository.findAllByBoardCategory(boardCategory);
    }

    public BoardDto getBoard(Long boardId, String category){
        Board optBoard = boardRepository.findById(boardId).orElseThrow(NullPointerException::new);

        if (!optBoard.getBoardCategory().toString().equalsIgnoreCase(category)){
            return null;
        }

        return BoardDto.of(optBoard);
    }

    public Long deleteBoard(Long boardId, String category){
        Optional<Board> optBoard = boardRepository.findById(boardId);

        if (optBoard.isEmpty() || !optBoard.get().getBoardCategory().toString().equalsIgnoreCase(category)){
            return null;
        }

        Board board = optBoard.get();
        User boardUser = board.getUser();

        boardRepository.deleteById(boardId);
        return boardId;
    }

    public List<BookNaverResult> getBookTitle(String searchKeyword){
        String clientId = "kgfYX80R2EPQMATW3UFu";
        String clientSecret = "sNmYx0eTmc";

        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/book.json")
                .queryParam("query", searchKeyword)
                .queryParam("display", 10)
                .queryParam("start", 1)
                .queryParam("sort", "sim")
                .encode()
                .build()
                .toUri();

        RequestEntity<Void> request = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .build();

        // RestTemplate : Rest 방식 API 를 호출
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(request, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        BookNaverSearch search = null;

        try {
            search = objectMapper.readValue(responseEntity.getBody(), BookNaverSearch.class);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }

        if (search != null) {
            return search.getItems();
        }
        return null;
    }

    public List<Board> findMyBoard(String email){
        return boardRepository.findAllByUserEmail(email);
    }

    public String getCategory(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(NullPointerException::new);
        return board.getBoardCategory().toString();
    }

    public int getCommentCount(Long boardId) {
        Board board= boardRepository.findById(boardId).orElseThrow(NullPointerException::new);
        return board.getCommentCount();
    }
}
