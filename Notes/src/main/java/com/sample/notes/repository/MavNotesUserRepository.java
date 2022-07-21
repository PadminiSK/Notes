package com.sample.notes.repository;

import com.sample.notes.model.MavNotesUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MavNotesUserRepository extends JpaRepository<MavNotesUser, Long> {
}
