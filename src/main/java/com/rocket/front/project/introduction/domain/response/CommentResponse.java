package com.rocket.front.project.introduction.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class CommentResponse {
    private Long commentSeq;
    private Long memberSeq;
    private Long recruitSeq;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createAt;
//    private LocalDateTime createAt;

    private String content;
    private List<CommentResponse> replyList;
}
