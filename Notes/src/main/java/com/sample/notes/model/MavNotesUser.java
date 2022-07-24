package com.sample.notes.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;
@Getter
@Setter
@Entity
@Table(name="note_users")

public class MavNotesUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long user_id;
    public String user_name;
    public String password;
    public String email_id;
    public Date created_at;
    public Date updated_at;
}
