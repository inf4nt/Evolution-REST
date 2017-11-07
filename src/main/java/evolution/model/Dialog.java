package evolution.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import evolution.serialization.jackson.CustomDialogSerializerMessageList;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Infant on 03.10.2017.
 */
@Entity
@Table(name = "dialog")
@Data
@ToString(exclude = {"messageList"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
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

    @Column(name = "date_create", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @JsonSerialize(using = CustomDialogSerializerMessageList.class)
    @OneToMany(mappedBy = "dialog", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Message> messageList;
}
