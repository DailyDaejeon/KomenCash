package com.komencash.backend.service;


import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Service
public class DownloadService {

    public File executeFile(){

        String filePath = "/home/ubuntu/client-download-file/MoneyJam.zip";
        return new File(filePath);
    }
}
