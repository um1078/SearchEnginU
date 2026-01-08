package searchengine.model;


import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;


import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "lemma") // Убрал лишние индексы, которые относились к другой таблице
@ToString
public class Lemma implements Comparable<Lemma> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false)
    private SiteModel site;

    @Column(nullable = false, length = 200)
    private String lemma;

    @Column(nullable = false)
    private Integer frequency;

    // ЗАМЕНИТЬ @ManyToMany НА ЭТО:
    @OneToMany(mappedBy = "lemma", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<searchengine.model.Index> indices = new HashSet<>();

    @Transient
    private Float rank;

    @Override
    public int compareTo(@NotNull Lemma o) {
        return Integer.compare(this.frequency, o.frequency);
    }
}