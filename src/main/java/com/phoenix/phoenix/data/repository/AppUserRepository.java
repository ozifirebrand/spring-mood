package com.phoenix.phoenix.data.repository;

import com.phoenix.phoenix.data.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

}
