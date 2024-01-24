package org.example.EJ7;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Piloto {
    private int driverid;
    private String code;
    private String forename;
    private String surname ;
    private String dob ;
    private String nationality ;
    private int constructorid ;
    private String url;
}
