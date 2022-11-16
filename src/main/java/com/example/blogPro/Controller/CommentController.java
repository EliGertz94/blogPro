package com.example.blogPro.Controller;

import com.example.blogPro.DTO.CommentDTO;
import com.example.blogPro.entity.Comment;
import com.example.blogPro.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable long id,
                                     @RequestBody CommentDTO commentDTO){
        return new ResponseEntity<>
                (commentService.createComment(id,commentDTO), HttpStatus.OK);
    }
    @GetMapping("/comment/all")
    public ResponseEntity<List<CommentDTO>> getAllComments(){
        return new ResponseEntity<>(commentService.getAllComments(),HttpStatus.OK);
    }
    @GetMapping("/comment/list/{id}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable long id){
        return new ResponseEntity<>(commentService.getByPostID(id),HttpStatus.OK);
    }

    @GetMapping("/comment/list")
    public ResponseEntity<List<CommentDTO>> getCommentsByEmail(@RequestParam(required = false) String email){
        return new ResponseEntity<>(commentService.commentsByEmail(email),HttpStatus.OK);

    }
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> getCommentIfOfPostId(@PathVariable long postId,@PathVariable long id){
        return new ResponseEntity<>(commentService.getCommentByPostId(postId,id),HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateCommentByPostId(
            @PathVariable long postId,
            @PathVariable long commentId,
            @RequestBody CommentDTO commentDTO
    ){
     return new ResponseEntity<>(commentService.updateCommentByPostId(postId,commentId,commentDTO),HttpStatus.OK);

    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentByPostId(
            @PathVariable long postId,
            @PathVariable long commentId
    ){
        return new ResponseEntity<>(commentService.deleteCommentByPostId(postId,commentId),HttpStatus.OK);
    }


}
