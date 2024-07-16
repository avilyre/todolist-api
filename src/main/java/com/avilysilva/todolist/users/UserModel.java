package com.avilysilva.todolist.users;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data // Or @Getter and @Setter
@Entity(name = "tb_users")
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    // @Column(name = "<custom-column-name>")
    private String name;
    private String username;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt; // will be automatically understood by database and converted to created_at

}
