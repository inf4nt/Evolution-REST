package evolution.dto.transfer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Infant on 11.11.2017.
 */
public interface TransferDTO<Model, DtoForSave, DtoForUpdate, DtoFull, Dto> {

    DtoForSave modelToDtoSave(Model model);

    DtoForUpdate modelToDtoUpdate(Model model);

    DtoFull modelToDtoFull(Model model);

    Dto modelToDto(Model model);

    Model dtoSaveToModel(DtoForSave dtoForSave);

    Model dtoUpdateToModel(DtoForUpdate dtoForUpdate, Model original);

    Model dtoFullToModel(DtoFull dtoFull);

    Model dtoToModel(Dto dto);

    default List<Model> dtoSaveToModelList(List<DtoForSave> dtoForSave) {
        return dtoForSave.stream().map(o -> dtoSaveToModel(o)).collect(Collectors.toList());
    }

    default List<Model> dtoUpdateToModelList(List<DtoForUpdate> dtoForUpdate, List<Model> original) {
        if (dtoForUpdate.isEmpty() || original.isEmpty())
            return new ArrayList<>();

        if (dtoForUpdate.size() != original.size()) {
            throw new UnsupportedOperationException();
        }
        List<Model> list = new ArrayList<>();
        for (int i = 0; i < dtoForUpdate.size(); i++) {
            list.add(dtoUpdateToModel(dtoForUpdate.get(i), original.get(i)));
        }
        return list;
    }

    default List<Model> dtoFullToModelList(List<DtoFull> dtoFull) {
        return dtoFull.stream().map(o -> dtoFullToModel(o)).collect(Collectors.toList());
    }

    default List<Model> dtoToModelList(List<Dto> dto) {
        return dto.stream().map(o -> dtoToModel(o)).collect(Collectors.toList());
    }

    default List<DtoForSave> modelToDtoSaveList(List<Model> model) {
        return model.stream().map(o -> modelToDtoSave(o)).collect(Collectors.toList());
    }

    default List<DtoForUpdate> modelToDtoUpdateList(List<Model> model) {
        return model.stream().map(o -> modelToDtoUpdate(o)).collect(Collectors.toList());
    }

    default List<DtoFull> modelToDtoFullList(List<Model> model) {
        return model.stream().map(o -> modelToDtoFull(o)).collect(Collectors.toList());
    }

    default List<Dto> modelToDtoList(List<Model> model) {
        return model.stream().map(o -> modelToDto(o)).collect(Collectors.toList());
    }
}
