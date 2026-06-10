package sb.cache.cacheImplement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "User_Table")
public class User_Table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Value")
    private Integer id;
    @Column(name = "User_Name")
    private String name;
    @Column(name = "User_Email")
    private String email;
}
