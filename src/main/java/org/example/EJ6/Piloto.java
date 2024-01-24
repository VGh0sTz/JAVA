package org.example.EJ6;

import lombok.*;

import java.sql.Connection;
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
