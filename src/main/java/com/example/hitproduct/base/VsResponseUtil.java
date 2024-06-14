package com.example.hitproduct.base;
/*
 * @author HongAnh
 * @created 14 / 06 / 2024 - 5:52 PM
 * @project HitProduct
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class VsResponseUtil {

    public static ResponseEntity<RestData<?>> success(Object data) {
        return success(HttpStatus.OK, data);
    }

    public static ResponseEntity<RestData<?>> success(HttpStatus status, Object data) {
        RestData<?> response = new RestData<>(data);
        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<RestData<?>> success(MultiValueMap<String, String> header, Object data) {
        return success(HttpStatus.OK, header, data);
    }

    public static ResponseEntity<RestData<?>> success(HttpStatus status, MultiValueMap<String, String> header, Object data) {
        RestData<?> response = new RestData<>(data);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.addAll(header);
        return ResponseEntity.ok().headers(responseHeaders).body(response);
    }

    public static ResponseEntity<RestData<?>> error(HttpStatus status, Object message) {
        RestData<?> response = RestData.error(message);
        return new ResponseEntity<>(response, status);
    }

}