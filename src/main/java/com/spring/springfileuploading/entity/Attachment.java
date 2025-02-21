package com.spring.springfileuploading.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@NoArgsConstructor
public class Attachment {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String fileName;

    private String fileType;

    public Attachment(String fileName, String fileType, byte[] fileData) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileData = fileData;
    }

    @Lob
    private byte[] fileData;
}
