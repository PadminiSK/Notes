package com.sample.notes.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="notes")
public class MavNotes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String title;
    public String note;
    public Long user_id;
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id", insertable = false, updatable = false)
    public NoteUsers note_users;
    public Date created_at;
    public Date updated_at;
}
