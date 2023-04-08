package uplus.hackerton.barogo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name="d_grades")
@NoArgsConstructor
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Deliver deliver;

    private int score;

    @Column(length = 200)
    private String content;

    @Builder
    public Grade(User user, Deliver deliver, int score, String content){
        this.user = user;
        this.content = content;
        this.deliver = deliver;
        this.score = score;
    }

}
