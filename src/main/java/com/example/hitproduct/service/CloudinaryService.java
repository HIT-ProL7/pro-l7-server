package com.example.hitproduct.service;
/*
 * @author HongAnh
 * @created 19 / 06 / 2024 - 10:15 AM
 * @project HitProduct
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CloudinaryService {
    Map upload(MultipartFile file);
}
