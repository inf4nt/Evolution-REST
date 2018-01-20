package evolution.model;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Infant on 03.10.2017.
 */
@Entity
@Table(name = "dialog")
@Data
@ToString(exclude = {"messageList"})
@Deprecated // todo create dialog by composite foreign key ! or create new entity message
public class Dialog {

    @Id
    @Column(name = "id", columnDefinition = "bigint")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dialog")
    @SequenceGenerator(name = "seq_dialog", sequenceName = "seq_dialog_id", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "first", updatable = false, nullable = false, columnDefinition = "bigint")
    private User first;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "second", updatable = false, nullable = false, columnDefinition = "bigint")
    private User second;

    @Column(name = "date_create", columnDefinition = "timestamp", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @OneToMany(mappedBy = "dialog", fetch = FetchType.LAZY, cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<Message> messageList = new ArrayList<>();
}
