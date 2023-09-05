package com.rocket.front.project.domain.introduction.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CommentResponse {
    private Long commentSeq;
    private Long memberSeq;
    private Long recruitSeq;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createAt;
    private String content;
    private List<CommentResponse> ReplyList;
}
