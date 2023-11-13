package az.compar.blog.services.impl;

import az.compar.blog.entities.Comment;
import az.compar.blog.entities.Post;
import az.compar.blog.exceptions.ResourceNotFoundException;
import az.compar.blog.payloads.CommentDto;
import az.compar.blog.payloads.PostResponse;
import az.compar.blog.repositories.CommentRepository;
import az.compar.blog.repositories.PostRepository;
import az.compar.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl implements CommentService {


    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post id", postId));

        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);

        return modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "CommentId", commentId));

        commentRepository.delete(comment);

    }
}
