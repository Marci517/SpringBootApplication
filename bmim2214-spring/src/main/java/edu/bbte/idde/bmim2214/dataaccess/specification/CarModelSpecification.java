package edu.bbte.idde.bmim2214.dataaccess.specification;

import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.*;

public class CarModelSpecification {

    public static Specification<CarModel> filterBy(
            String brand, Integer minYear, Integer maxYear,
            Double minPrice, Double maxPrice) {

        return (root, cq, cb) -> buildPredicate(root, cb, brand, minYear, maxYear, minPrice, maxPrice);
    }

    private static Predicate buildPredicate(
            Root<CarModel> root, CriteriaBuilder cb,
            String brand, Integer minYear, Integer maxYear,
            Double minPrice, Double maxPrice) {

        Predicate predicate = cb.conjunction();

        if (brand != null && !brand.isEmpty()) {
            predicate = cb.and(predicate, cb.like(root.get("brand"), "%" + brand + "%"));
        }
        if (minYear != null) {
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("year"), minYear));
        }
        if (maxYear != null) {
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("year"), maxYear));
        }
        if (minPrice != null) {
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("price"), minPrice));
        }
        if (maxPrice != null) {
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("price"), maxPrice));
        }

        return predicate;
    }
}
