package com.komencash.backend.controller;

import com.komencash.backend.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/download")
public class DownloadController {

    @Autowired
    DownloadService downloadService;

    @GetMapping
    public Resource executeFile() throws IOException {
        return downloadService.executeFile();
    }


}
