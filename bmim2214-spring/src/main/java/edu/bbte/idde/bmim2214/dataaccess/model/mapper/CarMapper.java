package edu.bbte.idde.bmim2214.dataaccess.model.mapper;

import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;
import edu.bbte.idde.bmim2214.dataaccess.model.dto.inDto.CarDtoIn;
import edu.bbte.idde.bmim2214.dataaccess.model.dto.outDto.CarDtoOut;
import edu.bbte.idde.bmim2214.dataaccess.model.dto.outDto.CarShortDtoOut;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class CarMapper {
    public abstract CarModel dtoToCar(CarDtoIn carDtoIn);

    public abstract CarDtoOut carToDto(CarModel car);

    @IterableMapping(elementTargetType = CarShortDtoOut.class)
    public abstract Collection<CarShortDtoOut> carsToDtos(Collection<CarModel> cars);
}
