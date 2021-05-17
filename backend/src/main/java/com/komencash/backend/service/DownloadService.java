package com.komencash.backend.service;


import org.apache.commons.io.FileUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;

@Service
public class DownloadService {

    public Resource executeFile() throws IOException {

        String filePath = "/home/ubuntu/client-download-file/MoneyJam.zip";
        File file = new File(filePath);
        InputStream is = FileUtils.openInputStream(file);
        return new InputStreamResource(is);
    }
}
