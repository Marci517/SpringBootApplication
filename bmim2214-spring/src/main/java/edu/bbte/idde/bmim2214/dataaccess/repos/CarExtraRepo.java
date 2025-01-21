package edu.bbte.idde.bmim2214.dataaccess.repos;

import edu.bbte.idde.bmim2214.dataaccess.model.CarExtra;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface CarExtraRepo extends JpaRepository<CarExtra, Long> {
    Page<CarExtra> findByCar_Id(Long carId, Pageable pageable);
}
