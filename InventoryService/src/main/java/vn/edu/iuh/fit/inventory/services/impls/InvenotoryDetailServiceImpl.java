package vn.edu.iuh.fit.inventory.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.iuh.fit.inventory.clients.CategoryClient;
import vn.edu.iuh.fit.inventory.clients.ProductServiceClient;
import vn.edu.iuh.fit.inventory.exceptions.InventoryDetailException;
import vn.edu.iuh.fit.inventory.mappers.InventoryDetailMapper;
import vn.edu.iuh.fit.inventory.models.dtos.PageDTO;
import vn.edu.iuh.fit.inventory.models.dtos.requests.InventoryDetailRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.*;
import vn.edu.iuh.fit.inventory.models.entities.InventoryDetail;
import vn.edu.iuh.fit.inventory.repositories.InventoryDetailRepository;
import vn.edu.iuh.fit.inventory.services.InventoryDetailService;
import vn.edu.iuh.fit.inventory.utils.SystemConstraints;

import java.util.List;
import java.util.Optional;
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
        if (sortBy != null && sortName != null) {
            pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortName), sortBy));
        }

        // Lấy dữ liệu phân trang từ repository
        Page<InventoryDetail> inventoryDetailsPage = inventoryDetailRepository.findAll(pageable);
        List<InventoryDetail> inventoryDetails = inventoryDetailsPage.getContent();

        List<InventoryDetailResponse> inventoryDetailResponses = inventoryDetails.stream()
                .map(inventoryDetail -> inventoryDetailMapper.toDto(inventoryDetail))
                .collect(Collectors.toList());

        // Tạo và trả về PageDTO với thông tin phân trang
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

    //Thêm một medicine vào kho
    @Override
    public InventoryDetailResponse save(InventoryDetailRequest request) throws InventoryDetailException {
        InventoryDetail inventoryDetail = null;

        if (request.getId() == null) {
            inventoryDetail = inventoryDetailMapper.toEntity(request);
        }else{
            Optional<InventoryDetail> oldInventoryDetail = inventoryDetailRepository.findById(request.getId());
            if(oldInventoryDetail.isEmpty()){
                throw new InventoryDetailException(SystemConstraints.INVENTORYDETAIL_NOT_FOUND);
            }else{
                inventoryDetail = inventoryDetailMapper.partialUpdate(request, oldInventoryDetail.get());

            }
        }

        inventoryDetail = inventoryDetailRepository.save(inventoryDetail);
        return inventoryDetailMapper.toDto(inventoryDetail);
    }

    @Override
    public PageDTO<InventoryDetailResponse> getMedicineByCategory(Long id, int page, int size, String sortBy, String sortName) {
        Pageable pageable = PageRequest.of(page, size);
        if (sortBy != null && sortName != null) {
            pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortName), sortBy));
        }

        // Lấy dữ liệu phân trang từ repository
        Page<InventoryDetail> inventoryDetailPage = inventoryDetailRepository.findAllByCategoryId(id, pageable);
        List<InventoryDetail> inventoryDetails = inventoryDetailPage.getContent();

        List<InventoryDetailResponse> inventoryDetailResponses = inventoryDetails.stream()
                .map(inventoryDetail -> inventoryDetailMapper.toDto(inventoryDetail))
                .collect(Collectors.toList());

        // Tạo và trả về PageDTO với thông tin phân trang
        return PageDTO.<InventoryDetailResponse>builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortName(sortName)
                .data(inventoryDetailResponses)
                .totalPage(inventoryDetailPage.getTotalPages())
                .build();
    }

    @Override
    public Long getTotalQuantity() {
        return inventoryDetailRepository.getTotalQuantity();
    }

//    //Cập nhật (thêm) số lượng của một medicine khi hủy đơn (thêm lại vào kho)
//    @Override
//    @Transactional
//    public void updateAddTotalProduct(Long medicineId, int quantity) {
//        inventoryDetailRepository.updateAddTotalProduct(medicineId, quantity);
//    }
//
//    //Cập nhật (trừ) số lượng của một medicine trong kho khi đặt hàng
//    @Override
//    @Transactional
//    public void updateSubtractTotalProduct(Long medicineId, int quantity) {
//        inventoryDetailRepository.updateSubtractTotalProduct(medicineId, quantity);
//    }

    // Tìm chi tiết kho theo medicine và shelfId
    @Override
    public InventoryDetailResponse findInventoryDetailByMedicineAndShelf(Long medicineId, Long shelfId) {
        InventoryDetail inventoryDetail = inventoryDetailRepository.findInventoryDetailByMedicineAndShelf(medicineId, shelfId);
        return (inventoryDetail != null) ? inventoryDetailMapper.toDto(inventoryDetail) : null;
    }

}
