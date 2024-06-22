package com.example.hitproduct.service;
/*
 * @author HongAnh
 * @created 21 / 06 / 2024 - 10:56 AM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CloudinaryService {
    String uploadFile(MultipartFile file);
    String uploadImage(byte[] bytes);
    void destroyFileWithUrl(String url);
}
