package com.sample.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.notes.model.NoteUsers;

@Repository
public interface NoteUsersRepository extends JpaRepository<NoteUsers, Long> {

}
