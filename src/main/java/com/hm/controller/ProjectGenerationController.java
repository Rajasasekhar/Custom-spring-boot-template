package com.hm.controller;

import com.hm.utils.ProjectGenerator;
import com.hm.utils.ProjectRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;


@RestController
@RequestMapping("/api/create")
public class ProjectGenerationController {

    @PostMapping
    public ResponseEntity<byte[]> createProject(@RequestBody ProjectRequest projReq) {

        try {
            byte[] zipFile = ProjectGenerator.generateZipFile(projReq);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "project.zip");
            headers.setContentLength(zipFile.length);
            return new ResponseEntity<>(zipFile, headers, HttpStatus.OK);
        } catch (IOException ignored) {

        }

        return null;
    }

}
