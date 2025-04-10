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
    private Integer userId;

    @Column(name = "language")
    private String language;

    @Column(name = "answer_01")
    private Boolean answer01;

    @Column(name = "answer_02")
    private Boolean answer02;

    @Column(name = "answer_03")
    private Boolean answer03;

    @Column(name = "answer_04")
    private Boolean answer04;
}
