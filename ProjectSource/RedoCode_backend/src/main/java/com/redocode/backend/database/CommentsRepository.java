package com.redocode.backend.database;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findAllByOrderByDateAsc();
    public List<Comment>  findAllByExcersizeIdOrderByDateAsc(Long excersizeId);
    public List<Comment>   findAllByOrderByDateDesc();
}

