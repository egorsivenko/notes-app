package org.example.todolist.repository;

import org.example.todolist.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    Optional<Category> findByName(String name);

    @Query("FROM Category c WHERE c.user.username = :username ORDER BY c.createdOn")
    List<Category> findByUsernameInCreationOrder(@Param("username") String username);
}
