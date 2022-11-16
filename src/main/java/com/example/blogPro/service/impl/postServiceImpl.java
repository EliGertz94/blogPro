package com.example.blogPro.service.impl;

import com.example.blogPro.DTO.PostDTO;
import com.example.blogPro.DTO.PostResponse;
import com.example.blogPro.entity.Post;
import com.example.blogPro.exception.ResourceNotFound;
import com.example.blogPro.repository.PostRepository;
import com.example.blogPro.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class postServiceImpl implements PostService {

   private PostRepository postRepository;


    public postServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }



    @Override
    public PostDTO createPost(PostDTO postDTO) {

        Post newPost =  postRepository.save(mapToEntity(postDTO));

        PostDTO responseEntity = mapToDto(newPost);

        return responseEntity;
    }

    @Override
    public PostResponse listPosts(int pageNumber,int pageSize,String sortBy,String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        Pageable pageable =  PageRequest.of(pageNumber,pageSize, sort);

        Page<Post> allPosts=  postRepository.findAll(pageable);

        List<Post> listOfPosts = allPosts.getContent();


       List<PostDTO> content =listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNumber(allPosts.getNumber());
        postResponse.setPageSize(allPosts.getSize());
        postResponse.setTotalElements(allPosts.getTotalElements());
        postResponse.setTotalPages(allPosts.getTotalPages());
        postResponse.setLast(allPosts.isLast());

        return postResponse;

    }

    @Override
    public PostDTO getPostById(long id) {
           Post post=  postRepository.findById(id).orElseThrow(()-> new ResourceNotFound
                   ("Blog","id",id));

          return mapToDto(post);
    }

    //returns an postDTO that was provided
    @Override
    public PostDTO updatePost(PostDTO postDTO, long id) {

      return   postRepository.findById(id).map(post -> {
                    post.setTitle(postDTO.getTitle());
                    post.setDescription(postDTO.getDescription());
                    post.setContent(postDTO.getContent());

                    return mapToDto(postRepository.save(post));
                }
                ).orElseThrow(()-> new ResourceNotFound("Post", "id",id));
    }

    @Override
    public void deletePost(long id) {
        if(!postRepository.existsById(id)){
            throw new ResourceNotFound("Post","id",id);
        }
        postRepository.deleteById(id);
    }

    //convert entity to DTO
    private PostDTO mapToDto(Post post){
        PostDTO responseEntity = new PostDTO();
        responseEntity.setId( post.getId());
        responseEntity.setContent( post.getContent());
        responseEntity.setDescription( post.getDescription());
        responseEntity.setTitle( post.getTitle());
        return responseEntity;
    }

    //map dto to entity
    private Post mapToEntity(PostDTO postDTO){
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        return post;
    }

}
