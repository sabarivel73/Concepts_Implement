package sb.fh.fhimplement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sb.fh.fhimplement.entity.EntityData;
import sb.fh.fhimplement.repository.DataRepo;
import sb.fh.fhimplement.util.Compress;

import java.io.IOException;
import java.util.Optional;

@Service
public class DataService {
    @Autowired
    private DataRepo dataRepo;

    public String postData(MultipartFile file) throws IOException {
        EntityData data = new EntityData();
        data.setName(file.getOriginalFilename());
        data.setType(file.getContentType());
        data.setFile(Compress.compress(file.getBytes()));
        dataRepo.save(data);
        return "File Saved";
    }

    @Transactional(readOnly = true)
    public EntityData getData(String fileName) throws IOException {
        Optional<EntityData> value = dataRepo.findByName(fileName);
        if(value.isEmpty()) return null;
        EntityData data = value.get();
        data.setFile(Compress.decompress(data.getFile()));
        return data;
    }
}
