package searchengine.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "page", indexes = @jakarta.persistence.Index(name = "path_idx", columnList = "path, site_id", unique = true))
@ToString
public class PageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false)
    private searchengine.model.SiteModel site;

    @Column(length = 200)
    private String path;

    @Column(nullable = false)
    private Integer code;

    @Column(nullable = false, columnDefinition = "mediumtext")
    private String content;

    @OneToMany(mappedBy = "pageModel", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<searchengine.model.Index> indices = new HashSet<>();
}