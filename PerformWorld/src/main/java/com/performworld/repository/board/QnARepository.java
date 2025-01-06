package com.performworld.repository.board;

import com.performworld.domain.QnA;
import com.performworld.dto.board.QnARequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface QnARepository extends JpaRepository<QnA, Long> {

    // QnA 업데이트(필요한 데이터만 업데이트)
    @Modifying
    @Query("UPDATE QnA q SET q.title = :#{#qnaRequestDTO.title}, " +
            "q.content = :#{#qnaRequestDTO.content} where q.qnaId=:qnaId")
    void update(Long qnaId, QnARequestDTO qnaRequestDTO);

    // QnA에 답변 추가
    @Modifying
    @Query("UPDATE QnA q SET q.response = :response, q.responseDatetime = CURRENT_TIMESTAMP where q.qnaId = :qnaId")
    void respond(Long qnaId, String response);

    // QnA 삭제
    void deleteById(Long qnaId);

}



