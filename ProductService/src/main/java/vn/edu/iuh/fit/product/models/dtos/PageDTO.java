package vn.edu.iuh.fit.product.models.dtos;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PageDTO <T>{
    private int page;
    private int size;
    private int totalPage;
    private String sortBy;
    private String sortName;
    private Set<T> data;
}
