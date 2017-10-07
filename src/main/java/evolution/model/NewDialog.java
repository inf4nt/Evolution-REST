package evolution.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import evolution.service.serialization.CustomDialogSerializerMessageList;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Infant on 03.10.2017.
 */
//@Entity
//@Table(name = "dialog")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"messageList"})
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NewDialog {

    @Id
    @Column(name = "id", columnDefinition = "bigint")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dialog")
    @SequenceGenerator(name = "seq_dialog", sequenceName = "seq_dialog_id", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "first", updatable = false, nullable = false, columnDefinition = "bigint")
    private NewUser first;

    @ManyToOne
    @JoinColumn(name = "second", updatable = false, nullable = false, columnDefinition = "bigint")
    private NewUser second;

    @Column(name = "date_create", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "is_active")
    private boolean isActive;

    @JsonSerialize(using = CustomDialogSerializerMessageList.class)
    @OneToMany(mappedBy = "dialog", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<NewMessage> messageList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewDialog newDialog = (NewDialog) o;

        if (isActive != newDialog.isActive) return false;
        if (id != null ? !id.equals(newDialog.id) : newDialog.id != null) return false;
        if (first != null ? !first.equals(newDialog.first) : newDialog.first != null) return false;
        if (second != null ? !second.equals(newDialog.second) : newDialog.second != null) return false;
        if (createDate != null ? !createDate.equals(newDialog.createDate) : newDialog.createDate != null) return false;
        return messageList != null ? messageList.equals(newDialog.messageList) : newDialog.messageList == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (first != null ? first.hashCode() : 0);
        result = 31 * result + (second != null ? second.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (messageList != null ? messageList.hashCode() : 0);
        return result;
    }
}
