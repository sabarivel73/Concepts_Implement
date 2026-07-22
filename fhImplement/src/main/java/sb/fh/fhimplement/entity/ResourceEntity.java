package sb.fh.fhimplement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "table_3")
public class ResourceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "originalName")
    private String originalName;
    @Column(name = "storedName")
    private String storedName;
    @Column(name = "filePath")
    private String filePath;
    @Column(name = "fileType")
    private String fileType;
}
