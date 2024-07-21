package com.yaliny.book.springboot.web;

import com.yaliny.book.springboot.service.posts.PostsService;
import com.yaliny.book.springboot.web.dto.PostsResponseDto;
import com.yaliny.book.springboot.web.dto.PostsSaveRequestDto;
import com.yaliny.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long Save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable(name = "id") Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable(name = "id") Long id) {
        return postsService.findById(id);
    }
}
