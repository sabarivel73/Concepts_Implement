package sb.fh.fhimplement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "table_1")
public class EntityData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;
    @Lob
    @Column(name = "file") //@Column(name = "file", columnDefinition = "bytea") for PSQL
    private byte[] file;
}
