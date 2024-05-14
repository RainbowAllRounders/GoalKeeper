package com.allrounders.goalkeeper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default        // 빌더 패턴으로 사용 시, 기본값 설정
    private int page = 1;   // 페이지 번호, 기본값 1

    @Builder.Default
    private int size = 8;  // 페이지 당 데이터 개수, 기본값 8

    private String type;    // 검색의 종류 t 제목, c 내용, w 작성자, tc 제목+내용, tw 제목+작성자, twc 제목+작성자+내용

    private String keyword; // 검색 키워드

    private String link;    // 페이지 url

    public int getSkip() {
        return (page - 1) * 8;
    }

    // 검색 종류를 배열로 반환하는 메서드
    public String[] getTypes() {
        if(type == null || type.isEmpty()) {    // type이 비어있으면
            return null;    // null 반환
        }
        return type.split("");  // type을 문자열 배열로 분할하여 반환
    }

    // 페이지 요청 객체를 생성하여 반환하는 메서드
    public Pageable getPageable(String...props) {
        return PageRequest
                .of(this.page-1, this.size, Sort.by(props).descending());
        // page, size, 정렬 속성(props)을 사용하여 Pageable 객체 생성하여 반환
        // 페이지 번호는 일반적으로 0부터 시작. 그런데 page 필드는 1부터 시작하므로 1 뺌
        // this.size는 페이지 당 항목 수
        // Sort.by(props).descending(): props 배열에 지정된 속성을 기반으로 Sort 객체를 생성하여 결과 내림차순 정렬
    }

    public String getLink() {

        if(link == null) {
            StringBuilder builder = new StringBuilder();
            builder.append("page=" + this.page);
            builder.append("&size=" + this.size);
            link = builder().toString();
        }
        return link;
    }
}
