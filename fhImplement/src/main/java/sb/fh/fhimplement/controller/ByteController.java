package sb.fh.fhimplement.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sb.fh.fhimplement.entity.ByteEntity;
import sb.fh.fhimplement.service.ByteService;

import java.io.IOException;

@RestController
@RequestMapping("/byte")
public class ByteController {

    private final ByteService dataService;

    public ByteController(ByteService dataService) {
        this.dataService = dataService;
    }

    @PostMapping("/post")
    public ResponseEntity<String> post(@RequestParam MultipartFile file) throws IOException {
        return new ResponseEntity<>(dataService.postData(file), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam String fileName) throws IOException {
        ByteEntity data = dataService.getData(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(data.getType()))
                .body(data.getFile());
    }

    @GetMapping("/download")
    public ResponseEntity<?> download(@RequestParam String fileName) throws IOException {
        ByteEntity data = dataService.getData(fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(data.getType()));
        headers.setContentDispositionFormData("attachment", fileName);
        return new ResponseEntity<>(data.getFile(), headers, HttpStatus.OK);
        //        return ResponseEntity.status(HttpStatus.OK)
        //                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName)
        //                .contentType(MediaType.valueOf(data.getType()))
        //                .body(data.getFile());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam String fileName) {
        return new ResponseEntity<>(dataService.deleteData(fileName), HttpStatus.OK);
    }
}
