package sb.fh.fhimplement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sb.fh.fhimplement.entity.ByteEntity;
import sb.fh.fhimplement.repository.ByteRepo;
import sb.fh.fhimplement.util.Compress;

import java.io.IOException;
import java.util.Optional;

@Service
public class ByteService {
    @Autowired
    private ByteRepo dataRepo;

    public String postData(MultipartFile file) throws IOException {
        ByteEntity data = new ByteEntity();
        data.setName(file.getOriginalFilename());
        data.setType(file.getContentType());
        data.setFile(Compress.compress(file.getBytes()));
        dataRepo.save(data);
        return "File Saved";
    }

    @Transactional(readOnly = true)
    public ByteEntity getData(String fileName) throws IOException {
        Optional<ByteEntity> value = dataRepo.findByName(fileName);
        if(value.isEmpty()) return null;
        ByteEntity data = value.get();
        data.setFile(Compress.decompress(data.getFile()));
        return data;
    }

    @Transactional
    public String deleteData(String fileName) {
        dataRepo.deleteByName(fileName);
        return "File deleted successfully";
    }
}
