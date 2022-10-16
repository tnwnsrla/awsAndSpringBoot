package com.jojoldu.book.springboot.web.dto;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void 롬복_기능_테스트() {
        //given
        String name = "test";
        int amount = 1000;

        //when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        //then
        /**
         * Junit 의 기본 assertThat 이 아닌 assertj 의 assertThat 을 사용한다.
         * CoreMatchers 와 달리 추가적으로 라이브러라기 필요하지 않고
         * 자동완성이 좀 더 확실하기 지원된다.
         */
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);


    }
}