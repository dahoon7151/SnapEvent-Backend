package com.example.snapEvent.follow.dto;

import lombok.Data;

@Data
public class FollowSimpleListDto {

     private String username;
     private String nickname;

     public FollowSimpleListDto(String username, String nickname) {
          this.username = username;
          this.nickname = nickname;
     }
}
