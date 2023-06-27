package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class UserFull {

    public class Location{
        private String street;
        private String city;
        private String state;
        private String country;
        private String timezone;
    }

    public class Root{
        private String id;
        private String title;
        private String firstName;
        private String lastName;
        private String picture;
        private String gender;
        private String email;
        private String dateOfBirth;
        private String phone;
        private Location location;
        private String registerDate;
        private String updatedDate;
    }
}
