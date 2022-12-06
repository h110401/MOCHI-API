package com.mochi.repository.user;

import com.mochi.model.user.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProfileRepository extends JpaRepository<Profile, Long> {
}
