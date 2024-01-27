package com.redocode.backend.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name ="Excersize_Diffuculty_Ratings")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ExcersizeDiffucultyRating {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Id
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "excersize_id")
    @Id
    private Excersize excersize;

    @Column(
            name = "rating",
            columnDefinition = "INT CHECK (rating >= 1 AND rating <= 5)")
    private int rating;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Excersize getExcersize() {
        return excersize;
    }

    public void setExcersize(Excersize excersize) {
        this.excersize = excersize;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
