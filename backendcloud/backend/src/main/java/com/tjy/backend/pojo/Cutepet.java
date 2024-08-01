package com.tjy.backend.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cutepet {
    private String id;
    private String url;
    private String author;
    private String type;
    private String source;
    private String content;
}
