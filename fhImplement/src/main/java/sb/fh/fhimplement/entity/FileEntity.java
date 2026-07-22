package sb.fh.fhimplement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "table_2")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fileName")
    private String fileName;
    @Column(name = "fileURL")
    private String fileURL;
    @Column(name = "fileType")
    private String fileType;
    @Column(name = "fileKey")
    private String fileKey;
}
