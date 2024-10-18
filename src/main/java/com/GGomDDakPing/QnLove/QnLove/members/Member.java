package com.GGomDDakPing.QnLove.QnLove.members;

import com.GGomDDakPing.QnLove.QnLove.posts.Post;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true , nullable = false , length = 30)
    private String name;

    @Column(unique = true , nullable = false , length = 50)
    private String email;

    @Column(unique = true , nullable = false , length = 50)
    private String instagramId;

    @Column(nullable = false , length = 100)
    private String password;

    private Long sex;

    private Long age;

    private boolean isConnected = false;

    private boolean isDeleted = false;

    @LastModifiedDate
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "member" , cascade = CascadeType.ALL , orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Post> post;

}
