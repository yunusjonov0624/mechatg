package mechat.group.controller;

import mechat.group.entity.FileStorage;
import mechat.group.model.ResultSucces;
import mechat.group.repository.FileStorageRepo;
import mechat.group.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;

import static com.sun.deploy.util.BufferUtil.MB;

@RestController
@RequestMapping("/api/file")
public class FileStorageController {

    private final FileStorageService fileStorageService;

    private  final FileStorageRepo fileStorageRepository;
    @Value("${upload.folder}")
    private String uploadFolder;

    public FileStorageController(FileStorageService fileStorageService, FileStorageRepo fileStorageRepository) {
        this.fileStorageService = fileStorageService;
        this.fileStorageRepository = fileStorageRepository;
    }

    @GetMapping("/preview/{hashId}")
    public ResponseEntity preview(@PathVariable String hashId) throws IOException {
        FileStorage fileStorage =  fileStorageService.findByHashId(hashId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; fileName=\""+ URLEncoder.encode(fileStorage.getName()))
                .contentType(MediaType.parseMediaType(fileStorage.getContentType()))
                .body(new FileUrlResource(String.format("%s/%s",
                        uploadFolder,
                        fileStorage.getUploadPath())));
    }

    @GetMapping("/download/{hashId}")
    public ResponseEntity download(@PathVariable String hashId) throws IOException {
        FileStorage fileStorage =  fileStorageService.findByHashId(hashId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\""+ URLEncoder.encode(fileStorage.getName()))
                .contentType(MediaType.parseMediaType(fileStorage.getContentType()))
                .body(new FileUrlResource(String.format("%s/%s",
                        uploadFolder,
                        fileStorage.getUploadPath())));
    }
}