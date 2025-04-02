package ba.spotlightsarajevo.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ss_user_preferences")
public class UserPreferencesEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "language")
    private String language;

    @Column(name = "favourites_id")
    private String favourites_id;

    @Column(name = "answer_01")
    private String answer_01;

    @Column(name = "answer_02")
    private String answer_02;

    @Column(name = "answer_03")
    private String answer_03;

    @Column(name = "answer_04")
    private String answer_04;
}
