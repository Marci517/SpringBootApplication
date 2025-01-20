package edu.bbte.idde.bmim2214.dataaccess.repos;

import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Profile("jpa")
@Repository
public interface CarModelRepo extends JpaRepository<CarModel, Long>, JpaSpecificationExecutor<CarModel> {
    @Query("SELECT COUNT(c) FROM CarModel c WHERE c.year < 1950")
    long countCarsBefore1950();

    @Query("SELECT COUNT(c) FROM CarModel c WHERE c.price < 5000")
    long countCarsPriceLT5K();


}
