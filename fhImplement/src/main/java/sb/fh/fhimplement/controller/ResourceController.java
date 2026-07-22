package sb.fh.fhimplement.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sb.fh.fhimplement.entity.ResourceEntity;
import sb.fh.fhimplement.service.ResourceService;

import java.io.IOException;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping("/post")
    public ResponseEntity<String> post(@RequestParam MultipartFile file) throws IOException {
        return new ResponseEntity<>(resourceService.saveFile(file), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam String fileName) throws IOException {
        ResourceEntity data = resourceService.getPath(fileName);
        Resource file = resourceService.getFile(data.getFilePath());
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(data.getFileType()))
                .body(file);
    }

    @GetMapping("/download")
    public ResponseEntity<?> download(@RequestParam String fileName) throws IOException {
        ResourceEntity data = resourceService.getPath(fileName);
        Resource file = resourceService.getFile(data.getFilePath());
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+file.getFilename())
                .contentType(MediaType.valueOf(data.getFileType()))
                .body(file);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam String fileName) throws IOException {
        ResourceEntity data = resourceService.getPath(fileName);
        return new ResponseEntity<>(resourceService.deleteFile(data.getFilePath()), HttpStatus.OK);
    }

}
