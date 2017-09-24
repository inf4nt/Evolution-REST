package evolution.model.dialog;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import evolution.model.message.Message;
import evolution.model.user.UserLight;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Admin on 09.06.2017.
 */
@Entity
@Table(name = "dialog")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"messageList"})
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Dialog {

    @Id
    @Column(name = "id")
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "seq_dialog")
    @SequenceGenerator(name = "seq_dialog", sequenceName = "seq_dialog_id", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "first", updatable = false)
    private UserLight first;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "second", updatable = false)
    private UserLight second;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dialog", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    private List<Message> messageList = new ArrayList<>();

    public Dialog(Long id) {
        this.id = id;
    }

    public Dialog(Long id, UserLight second) {
        this.id = id;
        this.second = second;
    }

    public Dialog(UserLight first, UserLight second) {
        this.first = first;
        this.second = second;
    }
}
