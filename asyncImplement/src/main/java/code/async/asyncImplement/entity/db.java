package code.async.asyncImplement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table
public class db {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
}
