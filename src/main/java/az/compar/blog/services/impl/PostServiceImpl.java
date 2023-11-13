package az.compar.blog.services.impl;

import az.compar.blog.entities.Category;
import az.compar.blog.entities.Post;
import az.compar.blog.entities.User;
import az.compar.blog.exceptions.ResourceNotFoundException;
import az.compar.blog.payloads.PostDto;
import az.compar.blog.payloads.PostResponse;
import az.compar.blog.repositories.CategoryRepository;
import az.compar.blog.repositories.PostRepository;
import az.compar.blog.repositories.UserRepository;
import az.compar.blog.services.FileService;
import az.compar.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {


    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id",userId));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id",categoryId));

        Post post = modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        postRepository.save(post);

        return modelMapper.map(post,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post  = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "post id", postId));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        Post updatedPost = postRepository.save(post);
        return modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post deletedPost  = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "post id", postId));
        postRepository.delete(deletedPost);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize,  String sortBy, String sortDir) {

        Sort sort = null;
        if (!sortDir.equalsIgnoreCase("asc"))
        {
            sort = Sort.by(sortBy).ascending();
        }else
        {
            sort = Sort.by(sortBy).descending();
        }
        Pageable p = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> pagePost = postRepository.findAll(p);
        List<Post> allPosts = pagePost.getContent();


        List<PostDto> postDtos = allPosts.stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

       PostResponse postResponse = new PostResponse();

       postResponse.setContent(postDtos);
       postResponse.setPageNumber(pagePost.getNumber());
       postResponse.setPageSize(pagePost.getSize());
       postResponse.setTotalElements(pagePost.getTotalElements());
       postResponse.setTotalPages(pagePost.getTotalPages());
       postResponse.setLastPage(pagePost.isLast());


        return postResponse;

    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id",postId));

        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id",categoryId));
        List<Post> posts = postRepository.findByCategory(category);

        List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id",userId));
        List<Post> posts = postRepository.findByUser(user);

        List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;

    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = postRepository.findByTitleContaining("%"+keyword+"%");
        List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }



}
