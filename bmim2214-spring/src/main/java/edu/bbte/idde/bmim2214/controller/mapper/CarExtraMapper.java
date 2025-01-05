package edu.bbte.idde.bmim2214.controller.mapper;

import edu.bbte.idde.bmim2214.controller.dto.indto.CarExtraDtoIn;
import edu.bbte.idde.bmim2214.controller.dto.outdto.CarExtraDtoOut;
import edu.bbte.idde.bmim2214.dataaccess.model.CarExtra;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class CarExtraMapper {
    public abstract CarExtra dtoToExtra(CarExtraDtoIn carExtraDtoIn);

    public abstract CarExtraDtoOut extraToDto(CarExtra carExtra);

    @IterableMapping(elementTargetType = CarExtraDtoOut.class)
    public abstract Collection<CarExtraDtoOut> carExtrasToDtos(Collection<CarExtra> carExtras);
}
