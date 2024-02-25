package org.example.todolist.repository;

import jakarta.transaction.Transactional;
import org.example.todolist.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {

    @Modifying
    @Transactional
    @Query("UPDATE Note n SET n.title = :title, n.content = :content WHERE n.id = :id")
    void updateNoteById(@Param("id") UUID id, @Param("title") String title, @Param("content") String content);

    @Query("SELECT n FROM Note n ORDER BY n.createdOn")
    List<Note> findAllInCreationOrder();
}
