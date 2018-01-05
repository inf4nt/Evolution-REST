package evolution.dto.transfer;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface TransferDTO<DTO, Model> {

    DTO modelToDTO(Model model);

    default List<DTO> modelToDTO(List<Model> list) {
        return list
                .stream()
                .map(this::modelToDTO)
                .collect(Collectors.toList());
    }

    default Page<DTO> modelToDTO(Page<Model> page) {
        return page.map(this::modelToDTO);
    }

    default Optional<DTO> modelToDTO(Optional<Model> optionalModel) {
        return optionalModel.map(this::modelToDTO);
    }

}
