package evolution.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import evolution.service.serialization.CustomMessageSerializerDialog;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Infant on 03.10.2017.
 */
public class NewMessage {

    @Id
    @Column(name = "id", columnDefinition = "bigint")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_message")
    @SequenceGenerator(name = "seq_message", sequenceName = "seq_message_id", allocationSize = 1)
    private Long id;

    @JsonSerialize(using = CustomMessageSerializerDialog.class)
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "dialog_id", updatable = false, nullable = false, columnDefinition = "bigint")
    private NewDialog dialog;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id", columnDefinition = "bigint")
    private NewUserLight sender;

    @Column(name = "message", nullable = false, columnDefinition = "text")
    private String message;

    @Column(name = "date_dispatch", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDispatch;

    @Column(name = "date_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdate;

    @Column(name = "is_active")
    private boolean isActive;

}
