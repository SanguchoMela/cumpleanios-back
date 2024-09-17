package com.example.cumpleanios_back.domain.entities;

import java.util.Date;

public interface UserProjection {
    String getname();
    String getlastname();
    String getemailpersonal();
    Date getdateOfBirth();
}
