package evolution.model;

import evolution.model.user.UserLight;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Infant on 03.10.2017.
 */
public class NewDialog {

    @Id
    @Column(name = "id", columnDefinition = "bigint")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dialog")
    @SequenceGenerator(name = "seq_dialog", sequenceName = "seq_dialog_id", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "first", updatable = false, nullable = false, columnDefinition = "bigint")
    private NewUserLight first;

    @ManyToOne
    @JoinColumn(name = "second", updatable = false, nullable = false, columnDefinition = "bigint")
    private NewUserLight second;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_dialog_user_id", updatable = false, nullable = false, columnDefinition = "bigint")
    private NewUserLight createdDialogUser;

    @Column(name = "date_create", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "is_active")
    private boolean isActive;
}
