package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class UnsuccessfulUserCreateResponse {
    private String lastName;
    private String firstName;
    private String email;
}