package com.spring.springfileuploading.service;

import com.spring.springfileuploading.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface AttachmentService {
    Attachment saveAttachment(MultipartFile file) throws Exception;

    Attachment getAttachment(String fileId);
}
