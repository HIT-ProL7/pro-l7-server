//package com.example.hitproduct.security.user;
///*
// * @author HongAnh
// * @created 14 / 06 / 2024 - 6:40 PM
// * @project HitProduct
// * @social Github: https://github.com/lehonganh0201
// * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
// */
//
//import com.example.hitproduct.domain.entity.User;
//import com.example.hitproduct.repository.UserRepository;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//public class UserDetailsServiceImpl implements UserDetailsService {
//    UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Not found user with "+username));
//        return UserDetailsImpl.buildUserDetails(user);
//    }
//}
