package com.gsc.programaavisos.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsLetterDTO {

    private String operation;
    private String message;
}
