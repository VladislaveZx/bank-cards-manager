package ru.vldaislab.bekrenev.bankcardsmanager.DTO.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserDTO {
    private String email;
    private String password;
}
