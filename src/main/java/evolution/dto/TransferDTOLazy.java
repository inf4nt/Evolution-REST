package evolution.dto;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface TransferDTOLazy<DTO, DTOLazy, Model> extends TransferDTO<DTO, Model> {

    DTOLazy modelToDTOLazy(Model model);

    default List<DTOLazy> modelToDTOLazy(List<Model> list) {
        return list
                .stream()
                .map(this::modelToDTOLazy)
                .collect(Collectors.toList());
    }

    default Page<DTOLazy> modelToDTOLazy(Page<Model> page) {
        return page.map(this::modelToDTOLazy);
    }

    default Optional<DTOLazy> modelToDTOLazy(Optional<Model> optionalModel) {
        return optionalModel.map(this::modelToDTOLazy);
    }
}
