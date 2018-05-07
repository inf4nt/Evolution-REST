package evolution.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notes")
@Data
public class Notes {

    @Id
    @Column(columnDefinition = "bigint")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_notes")
    @SequenceGenerator(name = "seq_notes", sequenceName = "seq_notes_id", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id", columnDefinition = "bigint", nullable = false)
    private User sender;

    @Column(columnDefinition = "text", nullable = false)
    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "timestamp default current_timestamp")
    private Date datePost;

    @Column(columnDefinition = "boolean default true")
    private boolean isActive;

    @Version
    @Column(columnDefinition = "bigint default 0")
    private Long version;
}
