package com.spring.springfileuploading.service;

import com.spring.springfileuploading.entity.Attachment;
import com.spring.springfileuploading.repository.AttachmentRepository;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;

    @Autowired
    public AttachmentServiceImpl(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    @Transactional
    public Attachment saveAttachment(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            if (fileName.contains(".."))
            {
                throw new FileUploadException("Sorry! Filename contains invalid path sequence" + fileName);
            }
            Attachment attachment = new Attachment(fileName, file.getContentType(),file.getBytes());
            return attachmentRepository.save(attachment);
        }
        catch (IOException e){
            throw new Exception("Could not save file"+ fileName);
        }
    }

    @Override
    public Attachment getAttachment(String fileId) {
        return attachmentRepository.findById(fileId).orElseThrow();
    }
}
