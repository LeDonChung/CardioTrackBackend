package vn.edu.iuh.fit.inventory.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.inventory.clients.CategoryClient;
import vn.edu.iuh.fit.inventory.clients.ProductServiceClient;
import vn.edu.iuh.fit.inventory.exceptions.InventoryDetailException;
import vn.edu.iuh.fit.inventory.mappers.InventoryDetailMapper;
import vn.edu.iuh.fit.inventory.models.dtos.PageDTO;
import vn.edu.iuh.fit.inventory.models.dtos.responses.BaseResponse;
import vn.edu.iuh.fit.inventory.models.dtos.responses.CategoryResponse;
import vn.edu.iuh.fit.inventory.models.dtos.responses.InventoryDetailResponse;
import vn.edu.iuh.fit.inventory.models.dtos.responses.MedicineResponse;
import vn.edu.iuh.fit.inventory.models.entities.InventoryDetail;
import vn.edu.iuh.fit.inventory.repositories.InventoryDetailRepository;
import vn.edu.iuh.fit.inventory.services.InventoryDetailService;
import vn.edu.iuh.fit.inventory.utils.SystemConstraints;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InvenotoryDetailServiceImpl implements InventoryDetailService {
    @Autowired
    private InventoryDetailRepository inventoryDetailRepository;

    @Autowired
    private InventoryDetailMapper inventoryDetailMapper;

    @Autowired
    private ProductServiceClient productServiceClient;

    @Autowired
    private CategoryClient categoryClient;

    //get all inventory detail
    @Override
    public PageDTO<InventoryDetailResponse> getPagesInventoryDetail(int page, int size, String sortBy, String sortName) {
        Pageable pageable = PageRequest.of(page, size);
        if(sortBy != null && sortName != null) {
            pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortName), sortBy));
        }

        Set<InventoryDetail> inventoryDetails = inventoryDetailRepository.findAll(pageable).toSet();

        Set<InventoryDetailResponse> inventoryDetailResponses = inventoryDetails.stream().map(inventoryDetail -> inventoryDetailMapper.toDto(inventoryDetail)).collect(Collectors.toSet());

        return PageDTO.<InventoryDetailResponse>builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortName(sortName)
                .data(inventoryDetailResponses)
                .build();
    }

    // inventorydetail tương ứng với mỗi medicine trong kho
    // get mediceine by id
    @Override
    public InventoryDetailResponse getMedicineById(Long id) throws InventoryDetailException {
        Optional<InventoryDetail> inventoryDetail = inventoryDetailRepository.findById(id);
        if(inventoryDetail.isEmpty()) {
            throw new InventoryDetailException(SystemConstraints.MEDICINE_NOT_FOUND);
        }
        return inventoryDetailMapper.toDto(inventoryDetail.get());
    }

    //Lấy thông tin chi tiết của medicine từ product-service
    public MedicineResponse getMedicineDetails(Long id) {
        // Gọi Feign Client để lấy chi tiết medicine từ product-service
        ResponseEntity<BaseResponse<MedicineResponse>> response = productServiceClient.getMedicineById(id);

        // Kiểm tra xem phản hồi có thành công và dữ liệu có hợp lệ không
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody().getData();
        } else {
            //ném exception
            throw new InventoryDetailException(SystemConstraints.MEDICINE_NOT_FOUND);
        }
    }

    //Lấy thông tin chi tiết của category từ product-service
    public CategoryResponse getCategoryDetails(Long id) {
        ResponseEntity<BaseResponse<CategoryResponse>> response = categoryClient.getCategoryById(id);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody().getData();
        } else {
            //ném exception
            throw new InventoryDetailException(SystemConstraints.CATEGORY_NOT_FOUND);
        }
    }

    //Lấy số lượng sản phẩm theo categoryId
    @Override
    public int getQuantityCategoryProductInventory(Long categoryId) {
        return inventoryDetailRepository.getQuantityCategoryProductInventory(categoryId);
    }
}
