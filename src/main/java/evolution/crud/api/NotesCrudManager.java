package evolution.crud.api;

import evolution.dto.model.NotesDTO;
import evolution.dto.model.NotesSaveDTO;
import evolution.model.Notes;

public interface NotesCrudManager extends AbstractCrudManagerService<Notes, Long>{

    Notes create(NotesSaveDTO notesSaveDTO);

}
