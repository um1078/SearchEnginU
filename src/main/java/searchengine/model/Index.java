package searchengine.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "index_table") // "index" - зарезервированное слово в SQL, лучше назвать иначе
@Getter
@Setter
public class Index implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_id", nullable = false)
    private searchengine.model.PageModel pageModel; // Связь с сущностью PageModel

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lemma_id", nullable = false)
    private searchengine.model.Lemma lemma; // Связь с сущностью Lemma

    @Column(name = "rank_value", nullable = false)
    private float rank; // Вес (релевантность) слова на странице

    public Index() {
    }
}