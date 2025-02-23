package vn.edu.iuh.fit.post.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    private Long authorId; // Tác giả bài viết (chính là userId)

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "post_id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
}
