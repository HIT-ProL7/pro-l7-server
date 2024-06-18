//package com.example.hitproduct.security.user;
///*
// * @author HongAnh
// * @created 14 / 06 / 2024 - 6:33 PM
// * @project HitProduct
// * @social Github: https://github.com/lehonganh0201
// * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
// */
//
//import com.example.hitproduct.domain.entity.User;
//import lombok.*;
//import lombok.experimental.FieldDefaults;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.Collections;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class UserDetailsImpl implements UserDetails {
//    String id;
//    String username;
//    String password;
//    GrantedAuthority authority;
//
//    public static UserDetailsImpl buildUserDetails(User user){
//        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole().getName());
//        return new UserDetailsImpl(user.getId(), user.getUsername(), user.getPassword(), grantedAuthority);
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.singleton(authority);
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
