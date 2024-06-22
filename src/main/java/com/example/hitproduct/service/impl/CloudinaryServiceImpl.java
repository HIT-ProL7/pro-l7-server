package com.example.hitproduct.service.impl;
/*
 * @author HongAnh
 * @created 21 / 06 / 2024 - 10:56 AM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.hitproduct.exception.UploadFileException;
import com.example.hitproduct.service.CloudinaryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CloudinaryServiceImpl implements CloudinaryService {
    Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            String resourceType = getResourceType(file);
            Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type",
                    resourceType));
            return result.get("secure_url").toString();
        } catch (IOException e) {
            throw new UploadFileException("Upload file failed!");
        }
    }

    @Override
    public String uploadImage(byte[] bytes) {
        try {
            Map<?, ?> result = cloudinary.uploader().upload(bytes, ObjectUtils.asMap("resource_type", "image"));
            return result.get("secure_url").toString();
        } catch (IOException e) {
            throw new UploadFileException("Upload image failed!");
        }
    }

    @Override
    public void destroyFileWithUrl(String url) {
        int startIndex = url.lastIndexOf("/") + 1;
        int endIndex = url.lastIndexOf(".");
        String publicId = url.substring(startIndex, endIndex);
        try {
            Map<?, ?> result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            log.info(String.format("Destroy image public id %s %s", publicId, result.toString()));
        } catch (IOException e) {
            throw new UploadFileException("Remove file failed!");
        }
    }

    private static String getResourceType(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType != null) {
            if (contentType.startsWith("image/")) {
                return "image";
            } else if (contentType.startsWith("video/")) {
                return "video";
            } else {
                return "auto";
            }
        } else {
            throw new UploadFileException("Invalid file!");
        }
    }
}
