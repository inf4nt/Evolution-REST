package evolution.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import evolution.service.serialization.CustomMessageSerializerDialog;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Infant on 03.10.2017.
 */
//@Entity
//@Table(name = "message")
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class NewMessage {

    @Id
    @Column(name = "id", columnDefinition = "bigint")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_message")
    @SequenceGenerator(name = "seq_message", sequenceName = "seq_message_id", allocationSize = 1)
    private Long id;

    @JsonSerialize(using = CustomMessageSerializerDialog.class)
    @ManyToOne
    @JoinColumn(name = "dialog_id", updatable = false, nullable = false, columnDefinition = "bigint")
    private NewDialog dialog;

    @ManyToOne
    @JoinColumn(name = "sender_id", columnDefinition = "bigint")
    private NewUser sender;

    @Column(name = "message", nullable = false, columnDefinition = "text")
    private String message;

    @Column(name = "date_dispatch", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDispatch;

    @Column(name = "is_active")
    private boolean isActive;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewMessage that = (NewMessage) o;

        if (isActive != that.isActive) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (dialog != null ? !dialog.equals(that.dialog) : that.dialog != null) return false;
        if (sender != null ? !sender.equals(that.sender) : that.sender != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        return dateDispatch != null ? dateDispatch.equals(that.dateDispatch) : that.dateDispatch == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dialog != null ? dialog.hashCode() : 0);
        result = 31 * result + (sender != null ? sender.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (dateDispatch != null ? dateDispatch.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        return result;
    }
}
