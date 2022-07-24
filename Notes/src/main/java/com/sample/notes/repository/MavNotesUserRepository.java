package com.sample.notes.repository;

import com.sample.notes.model.MavNotesUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MavNotesUserRepository extends JpaRepository<MavNotesUser, Long> {
    @Query(value = "SELECT * from notes.note_users where email_id=?1 OR user_name=?1", nativeQuery = true)
    List<MavNotesUser> findUserByIdentifier(String identifier);
}
