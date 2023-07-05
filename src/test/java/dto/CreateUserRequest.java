package dto;

import lombok.*;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@Builder
public class CreateUserRequest {
    private String email;
    private String firstName;
    private String lastName;
}
