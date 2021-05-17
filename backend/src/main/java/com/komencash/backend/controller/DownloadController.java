package com.komencash.backend.controller;

import com.komencash.backend.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/download")
public class DownloadController {

    @Autowired
    DownloadService downloadService;

    @GetMapping
    public InputStreamResource executeFile() throws FileNotFoundException {
        return downloadService.executeFile();
    }


}
