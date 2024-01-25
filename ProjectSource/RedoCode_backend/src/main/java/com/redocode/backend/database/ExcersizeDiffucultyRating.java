package com.redocode.backend.database;

import jakarta.persistence.*;

@Entity
@Table(name ="Excersize_Diffuculty_Ratings")
public class ExcersizeDiffucultyRating {

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Id
    private User user;


    @ManyToOne
    @JoinColumn(name = "excersize_id")
    @Id
    private Excersize excersize;

    @Column(
            name = "rating",
            columnDefinition = "INT CHECK (rating >= 1 AND rating <= 5)")
    private int rating;


}
