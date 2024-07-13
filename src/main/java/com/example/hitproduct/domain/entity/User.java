package com.example.hitproduct.domain.entity;
/*
 * @author HongAnh
 * @created 14 / 06 / 2024 - 5:55 PM
 * @project HitProduct
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(insertable = false, updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    String id;

    @Column(nullable = false, unique = true)
    String studentCode;

    @Column(nullable = false)
    @JsonIgnore
    String password;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    String fullName;

    String avatarUrl;
    String bannerUrl;

    String githubUrl;
    String facebookUrl;


    @CreationTimestamp
    Timestamp createdAt;

    @UpdateTimestamp
    Timestamp updatedAt;

    @ManyToOne(
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.DETACH
            }, fetch = FetchType.EAGER
    )
    @JoinColumn(name = "role_id")
    Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Position> positions;

    Integer resetPasswordCount;

    // khoá 16 -> cohort 16 :v
    Integer cohort;

    String description;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(this.getRole().getName());
        return Collections.singleton(grantedAuthority);
    }

    @Override
    public String getUsername() {
        return studentCode;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
