package evolution.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import evolution.model.Message;
import evolution.model.User;
import evolution.service.serialization.CustomDialogSerializerMessageList;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
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

    @ManyToOne
    @JoinColumn(name = "first", updatable = false, nullable = false, columnDefinition = "bigint")
    private User first;

    @ManyToOne
    @JoinColumn(name = "second", updatable = false, nullable = false, columnDefinition = "bigint")
    private User second;

    @Column(name = "date_create", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @JsonSerialize(using = CustomDialogSerializerMessageList.class)
    @OneToMany(mappedBy = "dialog", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<Message> messageList;
}
