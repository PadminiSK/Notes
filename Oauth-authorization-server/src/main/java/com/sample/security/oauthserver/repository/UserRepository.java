package com.sample.security.oauthserver.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.security.oauthserver.entity.NoteUser;

@Repository
public interface UserRepository extends JpaRepository<NoteUser,Long> {
	NoteUser findByEmail(String email);
}
