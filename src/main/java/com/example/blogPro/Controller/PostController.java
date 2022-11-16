package com.example.blogPro.Controller;

import com.example.blogPro.DTO.PostDTO;
import com.example.blogPro.DTO.PostResponse;
import com.example.blogPro.entity.Post;
import com.example.blogPro.service.PostService;
import com.example.blogPro.utils.APPConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {


    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    //create blog post

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO){
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.OK);
    }
    // add listing of all the records without RequestParam
    @GetMapping("/list")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "page-number", defaultValue = APPConstants.DEFAULT_PAGE_NUMBER ,required = false) int pageNumber,
            @RequestParam(value = "page-size", defaultValue = APPConstants.DEFAULT_PAGE_SIZE ,required = false) int pageSize,
            @RequestParam(value = "sort-by", defaultValue = APPConstants.DEFAULT_SORT_BY ,required = false) String  sortBy,
            @RequestParam(value = "sortDir", defaultValue = APPConstants.DEFAULT_SORT_DIRECTION ,required = false) String  sortDir)
        {

        return new ResponseEntity<>(postService.listPosts(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable long id){
        return new ResponseEntity<>(postService.getPostById(id),HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable Long id){
       PostDTO updatedPost =  postService.updatePost(postDTO,id);
       return new ResponseEntity<>(updatedPost,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable long id){
        postService.deletePost(id);
        return new ResponseEntity<>("post number "+id +" was deleted !",HttpStatus.OK);
    }

}
