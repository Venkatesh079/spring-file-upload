package com.spring.springfileuploading.controller;

import com.spring.springfileuploading.entity.Attachment;
import com.spring.springfileuploading.model.ResponseData;
import com.spring.springfileuploading.service.AttachmentService;
import jakarta.servlet.Servlet;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

@RestController
public class AttachmentController {

    private AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping("/upload")
    public ResponseData upload(@RequestParam("file") MultipartFile file) throws Exception {
        Attachment attachment = new Attachment();
        String downloadURL = "";
        attachment = attachmentService.saveAttachment(file);
        downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(attachment.getId()).toUriString();
        return new ResponseData(attachment.getFileName(), downloadURL, file.getContentType(), file.getSize());
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws Exception {
        Attachment attachment;
        attachment = attachmentService.getAttachment(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment: filename=\""+ attachment.getFileName()+"\"")
                .body(new ByteArrayResource(attachment.getFileData()));
    }
}
