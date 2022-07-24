package com.sample.notes.repository;

import com.sample.notes.model.MavNotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface MavNotesRepository extends JpaRepository<MavNotes, Long> {
    @Query(value = "SELECT * FROM notes.notes where user_id=?1", nativeQuery = true)
    List<MavNotes> findAllByUserId(int user_id);
}
