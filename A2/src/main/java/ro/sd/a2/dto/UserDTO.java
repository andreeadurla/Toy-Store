package ro.sd.a2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ro.sd.a2.entity.User;

@Data
@Builder
@AllArgsConstructor
public class UserDTO {

    private String id;

    private String name;

    private String email;

    public static UserDTO fromEntity(User user) {
        return UserDTO.builder()
                    .id(user.getId())
                    .name(user.getFullName())
                    .email(user.getEmail())
                    .build();
    }
}
