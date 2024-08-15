package org.example.repository;

import org.example.entity.AuthorDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorDetailsRepository extends JpaRepository<AuthorDetails, Long> {
}
