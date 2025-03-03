package vn.edu.iuh.fit.product.repositories.specifications;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import vn.edu.iuh.fit.product.models.dtos.requests.MedicineSearchRequest;
import vn.edu.iuh.fit.product.models.entities.Brand;
import vn.edu.iuh.fit.product.models.entities.Category;
import vn.edu.iuh.fit.product.models.entities.Medicine;

import java.util.ArrayList;
import java.util.List;

public class MedicineFilterSpecification {
    public static Specification<Medicine> filter(MedicineSearchRequest request, String sortBy, String sortOrder ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Select distinct
            query.distinct(true);


            // Tìm kiếm theo key (name, brand title, category name)
            if (request.getKey() != null && !request.getKey().isEmpty()) {
                String searchPattern = "%" + request.getKey().toLowerCase() + "%";

                // Predicate tìm theo name của Medicine
                Predicate medicineNamePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")), searchPattern
                );

                // Join Brand để tìm theo title của Brand
                Join<Medicine, Brand> brandJoin = root.join("brand", JoinType.LEFT);
                Predicate brandTitlePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(brandJoin.get("title")), searchPattern
                );

                // Join Category để tìm theo name của Category
                Join<Medicine, Category> categoryJoin = root.join("categories", JoinType.LEFT);
                Predicate categoryNamePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(categoryJoin.get("title")), searchPattern
                );

                // Kết hợp các điều kiện bằng OR
                predicates.add(criteriaBuilder.or(medicineNamePredicate, brandTitlePredicate, categoryNamePredicate));
            }


            // Filter theo Categories (Nhiều - Nhiều)
            if (request.getCategories() != null && !request.getCategories().isEmpty()) {
                Join<Medicine, Category> categoryJoin = root.join("categories");
                predicates.add(categoryJoin.get("id").in(request.getCategories()));
            }

            // Filter theo Brands (Nhiều - Một)
            if (request.getBrands() != null && !request.getBrands().isEmpty()) {
                Join<Medicine, Brand> brandJoin = root.join("brand");
                predicates.add(brandJoin.get("id").in(request.getBrands()));
            }

            // Filter theo Objects (Nhiều - Nhiều)
            if (request.getObjects() != null && !request.getObjects().isEmpty()) {
                Join<Medicine, Category> objectJoin = root.join("tags");
                predicates.add(objectJoin.get("id").in(request.getObjects()));
            }

            // Tính finalPrice = price - (price * discount / 100)
            Expression<Double> finalPrice = criteriaBuilder.toDouble(
                    criteriaBuilder.diff(
                            root.get("price"),
                            criteriaBuilder.quot(
                                    criteriaBuilder.prod(root.get("price"), root.get("discount")),
                                    criteriaBuilder.literal(100)
                            )
                    )
            );

            // Filter theo giá
            if (request.getPriceFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(finalPrice, request.getPriceFrom()));
            }
            if (request.getPriceTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(finalPrice, request.getPriceTo()));
            }


            // Sắp xếp động
            if (sortBy != null) {
                Order order;
                if ("finalPrice".equalsIgnoreCase(sortBy)) {
                    order = "desc".equalsIgnoreCase(sortOrder)
                            ? criteriaBuilder.desc(finalPrice)
                            : criteriaBuilder.asc(finalPrice);
                } else {
                    order = "desc".equalsIgnoreCase(sortOrder)
                            ? criteriaBuilder.desc(root.get(sortBy))
                            : criteriaBuilder.asc(root.get(sortBy));
                }
                query.orderBy(order);
            }


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
