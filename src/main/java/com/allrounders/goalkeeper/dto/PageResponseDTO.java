package com.allrounders.goalkeeper.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageResponseDTO<E> {

    private int page;           // 몇 페이지(현재 페이지 번호)
    private int size;           // 한개 페이지의 크기(한 페이지에 표시되는 항목 수)
    private int total;          // 전체 row 갯수

    private int start;          // 현재 페이지에서 page 시작숫자
    private int end;            // 현재 페이지에서 page 끝숫자

    private boolean prev;       // 앞에 이동할 부분이 있는지
    private boolean next;       // 뒤에 이동할 부분이 있는지

    private List<E> dtoList;    // 이 페이지에 보여줄 row 리스트

    // Builder 패턴을 사용해 객체를 생성하는 메서드
    @Builder(builderMethodName = "withAll") // 메서드 이름을 withAll로
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total){
        if(total <= 0){
            return;
        }

        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        this.total = total;
        this.dtoList = dtoList;

        this.end = (int)(Math.ceil(this.page / 5.0)) * 5; // 현재 페이지 범위의 끝 페이지 번호 계산

        this.start = this.end - 4;  // 현재 페이지 범위의 시작 페이지 번호 계산

        int last = (int)(Math.ceil((total/(double)size)));  // 마지막 페이지 번호 계산

        this.end = end > last ? last : end; // 현재 페이지 범위의 끝 페이지 번호가 마지막 페이지를 초과하는 경우 보정

        this.prev = this.start > 1; // 이전 페이지로 이동 가능 여부 설정

        this.next = total > this.end * this.size;   // 다음 페이지로 이동 가능 여부 설정
    }
}
