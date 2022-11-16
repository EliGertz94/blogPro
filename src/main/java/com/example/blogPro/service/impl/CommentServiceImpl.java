package com.example.blogPro.service.impl;

import com.example.blogPro.DTO.CommentDTO;
import com.example.blogPro.entity.Comment;
import com.example.blogPro.entity.Post;
import com.example.blogPro.exception.BlogAPIException;
import com.example.blogPro.exception.ResourceNotFound;
import com.example.blogPro.repository.CommentRepository;
import com.example.blogPro.repository.PostRepository;
import com.example.blogPro.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

   private CommentRepository commentRepository;
   private PostRepository postRepository;
   private ModelMapper modelMapper;


    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository,ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository =postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {

        Comment comment = mapToEntity(commentDTO);

        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFound
                ("createComment","postId",postId));
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);
    }

    @Override
    public List<CommentDTO> getAllComments() {
        List<CommentDTO> commentDTO =new ArrayList<>();
       List<Comment> commentsEntity =commentRepository.findAll();
       for(Comment commentEntity: commentsEntity ) {
           commentDTO.add(mapToDto(commentEntity));
       }
        return commentDTO;

    }

    @Override
    public List<CommentDTO> getByPostID(long id) {

       List<Comment> commentList= commentRepository.findByPostId(id);
        List<CommentDTO> commentDTOList =new ArrayList<>();

        for (Comment comment:
        commentList) {
            commentDTOList.add(mapToDto(comment));
        }
        return commentDTOList;
    }

    @Override
    public List<CommentDTO> commentsByEmail(String email) {
        List<Comment> commentList= commentRepository.findByEmail(email);
        List<CommentDTO> commentDTOList =new ArrayList<>();

        for (Comment comment:
                commentList) {
            commentDTOList.add(mapToDto(comment));
        }
        return commentDTOList;
    }


    public CommentDTO getCommentByPostId(long postId,long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFound
                ("getCommentByPostId","post",postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFound
                ("getCommentByPostId","comment",commentId));

        if (!((Long)(comment.getPost().getId())).equals(post.getId())) {
                throw new BlogAPIException(HttpStatus.BAD_REQUEST
                , "The comment id "+comment.getId() +" does not belog to the post id "+ post.getId()+"!");
        }
        return mapToDto(comment);
    }
    @Override
    public CommentDTO updateCommentByPostId(long postID,long CommentId,CommentDTO commentDTO){

        Comment comment = commentRepository.findById(CommentId).orElseThrow(()-> new ResourceNotFound
                ("updateCommentByPostId","comment",CommentId));
        Post post = postRepository.findById(postID).orElseThrow(()-> new ResourceNotFound
                ("updateCommentByPostId","post",postID));
            if(comment.getPost().getId().equals(post.getId())) {
                return mapToDto(commentRepository.findById(CommentId).map(commentE -> {
                    System.out.println(commentE);

                    commentE.setEmail(commentDTO.getEmail());
                    commentE.setBody(commentDTO.getBody());
                    commentE.setName(commentDTO.getName());
                    System.out.println(commentE);
                    return commentRepository.save(commentE);
                }).orElseThrow(() ->
                        new ResourceNotFound("updateCommentByPostId", "CommentId", CommentId)));
            }
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"provided wrong information try again with new values");
    }

    @Override
    public String deleteCommentByPostId(long postID, long CommentId) {
        Comment comment = commentRepository.findById(CommentId).orElseThrow(()-> new ResourceNotFound
                ("deleteCommentByPostId","comment",CommentId));

        Post post = postRepository.findById(postID).orElseThrow(()-> new ResourceNotFound
                ("deleteCommentByPostId","post",postID));

        if(comment.getPost().getId().equals(post.getId())) {
             commentRepository.delete(comment);

             return "comment number " + CommentId +" was deleted";
        }
        throw new BlogAPIException(HttpStatus.BAD_REQUEST,"updateCommentByPostId");
    }



    private CommentDTO mapToDto(Comment comment){
        CommentDTO responseEntity = modelMapper.map(comment,CommentDTO.class);
//        responseEntity.setId( comment.getId());
//        responseEntity.setName( comment.getName());
//        responseEntity.setEmail( comment.getEmail());
//        responseEntity.setBody( comment.getBody());
        return responseEntity;
    }

    //map dto to entity
    private Comment mapToEntity(CommentDTO commentDTO){
        Comment comment = modelMapper.map(commentDTO,Comment.class);
//        comment.setName(commentDTO.getName());
//        comment.setEmail(commentDTO.getEmail());
//        comment.setBody(commentDTO.getBody());

        return comment;
    }


}
