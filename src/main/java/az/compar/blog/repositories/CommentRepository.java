package az.compar.blog.repositories;

import az.compar.blog.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository  extends JpaRepository<Comment, Integer> {
}
