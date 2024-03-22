package com.inhabas.api.domain.project.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaveProjectBoardDto {

  @NotBlank private String title;

  @NotBlank private String content;

  private List<String> files = new ArrayList<>();

  private Integer pinOption;

  @Builder
  public SaveProjectBoardDto(String title, String content, List<String> files, Integer pinOption) {
    this.title = title;
    this.content = content;
    this.files = files == null ? new ArrayList<>() : files;
    this.pinOption = pinOption;
  }
}
