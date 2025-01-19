package edu.bbte.idde.bmim2214.dataaccess.dao.repo;

import edu.bbte.idde.bmim2214.dataaccess.dao.CarDao;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Profile("jpa")
@Repository
public interface CarModelRepo extends JpaRepository<CarModel, Long>, CarDao, JpaSpecificationExecutor<CarModel> {
    @Override
    @Modifying
    @Transactional
    default void updateCar(CarModel car) {
        save(car);
    }

    @Override
    @Modifying
    @Transactional
    default void createCar(CarModel car) {
        save(car);
    }

}
