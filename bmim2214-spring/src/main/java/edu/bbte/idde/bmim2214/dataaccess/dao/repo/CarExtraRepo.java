package edu.bbte.idde.bmim2214.dataaccess.dao.repo;

import edu.bbte.idde.bmim2214.dataaccess.model.CarExtra;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface CarExtraRepo extends JpaRepository<CarExtra, Long> {
}
