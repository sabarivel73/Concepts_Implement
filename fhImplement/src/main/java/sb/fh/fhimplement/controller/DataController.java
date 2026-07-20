package sb.fh.fhimplement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sb.fh.fhimplement.entity.EntityData;
import sb.fh.fhimplement.service.DataService;

import java.io.IOException;

@RestController
public class DataController {
    @Autowired
    private DataService dataService;

    @PostMapping("/post")
    public ResponseEntity<String> post(@RequestParam MultipartFile file) throws IOException {
        return new ResponseEntity<>(dataService.postData(file), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam String fileName) throws IOException {
        EntityData data = dataService.getData(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(data.getType()))
                .body(data.getFile());
    }
}
