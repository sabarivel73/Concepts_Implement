package sb.fh.fhimplement.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sb.fh.fhimplement.entity.ResourceEntity;
import sb.fh.fhimplement.repository.ResourceRepo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResourceService {

    private final Path path;
    private final ResourceRepo resourceRepo;

    public ResourceService(@Value("${spring.file.upload.path}") String path,  ResourceRepo resourceRepo) {
        this.path = Paths.get(path);
        this.resourceRepo = resourceRepo;
    }

    public String saveFile(MultipartFile file) throws IOException {
        if(Files.notExists(path)) {
            Files.createDirectories(path);
        }
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename()); // This gets the file name from the uploaded file and cleans it to remove risky path characters like ../
        if(originalFilename.contains("..")) return "Invalid file name";
        String storeName = UUID.randomUUID()+"_"+originalFilename;
        Path filePath = path.resolve(storeName); // This combines the upload folder path with the new unique file name to form the full destination path
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        ResourceEntity data = new ResourceEntity();
        data.setOriginalName(originalFilename);
        data.setStoredName(storeName);
        data.setFilePath(filePath.toString());
        data.setFileType(file.getContentType());
        resourceRepo.save(data);
        return "File saved successfully";
    }

    public ResourceEntity getPath(String fileName) {
        Optional<ResourceEntity> data = resourceRepo.findByOriginalName(fileName);
        if(data.isEmpty()) return null;
        return data.get();
    }

    public Resource getFile(String file) throws IOException {
        Path filePath = Paths.get(file);
        Resource resource = new UrlResource(filePath.toUri());
        if(!resource.exists() || !resource.isReadable()) return null;
        return resource;
    }

    @Transactional
    public String deleteFile(String file) throws IOException {
        Path filePath = Paths.get(file);
        if(!Files.exists(filePath)) return "File not found";
        Files.delete(filePath);
        resourceRepo.deleteByFilePath(file);
        return "File deleted successfully";
    }
}
