package sb.fh.fhimplement.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sb.fh.fhimplement.entity.FileEntity;
import sb.fh.fhimplement.service.FileService;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/post")
    public ResponseEntity<String> postFile(@RequestParam MultipartFile file) throws IOException {
        return new ResponseEntity<>(fileService.saveFile(file), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getFile(@RequestParam String fileName) throws IOException {
        FileEntity fileEntity = fileService.getData(fileName);
        byte[] file = fileService.getFile(fileEntity.getFileKey());
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(fileEntity.getFileType()))
                .body(file);
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadFile(@RequestParam String fileName) throws IOException {
        FileEntity fileEntity = fileService.getData(fileName);
        byte[] file = fileService.getFile(fileEntity.getFileKey());
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName)
                .contentType(MediaType.valueOf(fileEntity.getFileType()))
                .body(file);

        //       HttpHeaders headers = new HttpHeaders();
        //       headers.setContentType(MediaType.valueOf(fileEntity.getFileType()));
        //       headers.setContentDispositionFormData("attachment", fileName);
        //       return new ResponseEntity<>(file, headers, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam String fileName) {
        FileEntity fileEntity = fileService.getData(fileName);
        return new ResponseEntity<>(fileService.deleteFile(fileEntity.getFileKey()), HttpStatus.OK);
    }
}