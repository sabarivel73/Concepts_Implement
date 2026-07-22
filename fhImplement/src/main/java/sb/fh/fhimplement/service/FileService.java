package sb.fh.fhimplement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sb.fh.fhimplement.entity.FileEntity;
import sb.fh.fhimplement.repository.FileRepo;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileService {

    @Value("${spring.aws.bucket_name}") private String bucketName;
    @Autowired
    private FileRepo fileRepo;
    @Autowired
    private S3Client s3Client;

    public String saveFile(MultipartFile file) throws IOException {
        FileEntity data = new FileEntity();
        data.setFileName(file.getOriginalFilename());
        if(!file.isEmpty()) {
            String key = UUID.randomUUID() + "_" + file.getOriginalFilename();
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            String fileURL = "https://" + bucketName + ".s3." + s3Client.serviceClientConfiguration().region().id() + ".amazonaws.com/" + key;
            data.setFileURL(fileURL);
            data.setFileType(file.getContentType());
            data.setFileKey(key);
        }
        fileRepo.save(data);
        return "File uploaded successfully";
    }

    public FileEntity getData(String fileName) {
        Optional<FileEntity> data = fileRepo.findByFileName(fileName);
        if(data.isPresent()) {
            return data.get();
        }
        return null;
    }

    public byte[] getFile(String key) throws IOException {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        try(ResponseInputStream<GetObjectResponse> s3Object = s3Client.getObject(getObjectRequest)) {
            return s3Object.readAllBytes();
        }
    }

    @Transactional
    public String deleteFile(String key) {
        if(key == null || key.isEmpty()) return "Key is null or empty";
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
        fileRepo.deleteByFileKey(key);
        return "File deleted successfully";
    }

}
