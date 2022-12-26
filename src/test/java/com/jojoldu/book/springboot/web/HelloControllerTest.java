package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
/**
 *  Web(SpringMvc) 에 집중할 수 있는 어노테이션
 *  선언할 경우 @Controller, @ControllerAdvice 등을 사용할 수 있다.
 *  단, @Service, @Component, @Repository  등은 사용할 수 없다.
 *  여기서는 컨트롤러만 사용하기 때문에 사용한다.
 */
@WebMvcTest(controllers = HelloController.class
            ,excludeFilters = {
                //  SecurityConfig 는 읽었지만, SecurityConfig를 생성하기 위해 필요한 CustomOAtuh2UserService 는 읽을 수가 없어서 스캔 대상에서 SecurityConfig 제거
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
            }
)
public class HelloControllerTest {

    /**
     * 웹 API 를 테스트할 때 사용한다.
     * 이 클래스를 통해 HTTP, GET, POST 등에 대한 API 테스트를 할 수 있다.
     */
    @Autowired
    private MockMvc mvc;

    @WithMockUser(roles="USER")
    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello")) /** MockMvc 를 통해 /hello 주소로 HTTP GET 요청을 한다.*/
                .andExpect(status().isOk()) /** HTTP Header 의 Status 를 검증한다.*/
                .andExpect(content().string(hello));
    }

    @WithMockUser(roles="USER")
    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                .param("name", name) /** API 테스트할 때 사용될 요청 파라미터 설정, 단 값은 String 만 허용 */
                .param("amount", String.valueOf(amount))) /** 숫자/날짜 등의 데이터를 등록할 때는 문자열로 변경해야 가능 */
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name))) /** JSON 응답 값을 필드별로 검증할 수 있는 메소드, $를 기준으로 필드명을 명시*/
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}