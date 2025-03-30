package vn.edu.iuh.fit.inventory.services.impls;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import vn.edu.iuh.fit.inventory.models.dtos.requests.InventoryDetailRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.*;
import vn.edu.iuh.fit.inventory.models.entities.InventoryDetail;
import vn.edu.iuh.fit.inventory.repositories.InventoryDetailRepository;
import vn.edu.iuh.fit.inventory.services.InventoryDetailService;
import vn.edu.iuh.fit.inventory.utils.SystemConstraints;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    @Override
    public Long getTotalQuantityMedicine(Long medicineId) {
        return inventoryDetailRepository.getTotalQuantityMedicine(medicineId);
    }

    @Override
    @Transactional
    public int updateQuantityByMedicine(Long medicineId, Long quantity) {
        //lay danh sach medicine, sap xep tang theo so luong
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.ASC, "quantity"));
        Page<InventoryDetail> inventoryDetailPage = inventoryDetailRepository.getInventoryDetailsSortedByQuantity(medicineId, pageable);

        Long quantityToSell = quantity;
        int updated = 0;

        //lap qua cac shelf de tru so luong
        for (InventoryDetail inventoryDetail : inventoryDetailPage.getContent()){
            if(quantityToSell <= 0) break;

            long quantityInStock = inventoryDetail.getQuantity();
            long quantityToSubtract = Math.min(quantityInStock, quantityToSell);

            int result = inventoryDetailRepository.updateQuantityByShelfAndMedicine(quantityToSubtract, inventoryDetail.getShelf().getId(), medicineId);
            updated += result;

            quantityToSell -= quantityToSubtract;
        }

        if (quantityToSell > 0) {
            throw new InventoryDetailException(SystemConstraints.NOT_ENOUGH_MEDICINE);
        }

        return updated;
    }

    @Override
    public PageDTO<InventoryDetailResponse> getMedicinesNearExpiration(int page, int size, String sortBy, String sortName) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationDate = now.plusMonths(6);

        Timestamp expirationTimestamp = Timestamp.valueOf(expirationDate);

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.asc(sortBy))); // Hoặc desc nếu cần

        Page<InventoryDetail> pageInventoryDetails = inventoryDetailRepository.findMedicinesExpirationDate(expirationTimestamp, pageRequest);

        List<InventoryDetailResponse> inventoryDetailResponses = pageInventoryDetails.getContent().stream()
                .map(inventoryDetail -> {
                    InventoryDetailResponse inventoryDetailResponse = inventoryDetailMapper.toDto(inventoryDetail);
                    inventoryDetailResponse.setNearExpiration(true);
                    return inventoryDetailResponse;
                })
                .collect(Collectors.toList());

        return PageDTO.<InventoryDetailResponse>builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortName(sortName)
                .data(inventoryDetailResponses)
                .totalPage(pageInventoryDetails.getTotalPages())
                .build();

    }

    @Override
    public PageDTO<InventoryDetailResponse> getMedicinesExpired(int page, int size, String sortBy, String sortName) {
        LocalDateTime now = LocalDateTime.now();

        Timestamp nowTimestamp = Timestamp.valueOf(now);

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.asc(sortBy))); // Hoặc desc nếu cần

        Page<InventoryDetail> pageInventoryDetails = inventoryDetailRepository.findMedicinesExpired(pageRequest);

        List<InventoryDetailResponse> inventoryDetailResponses = pageInventoryDetails.getContent().stream()
                .map(inventoryDetail -> {
                    InventoryDetailResponse inventoryDetailResponse = inventoryDetailMapper.toDto(inventoryDetail);
                    inventoryDetailResponse.setExpired(true);
                    return inventoryDetailResponse;
                })
                .collect(Collectors.toList());

        return PageDTO.<InventoryDetailResponse>builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortName(sortName)
                .data(inventoryDetailResponses)
                .totalPage(pageInventoryDetails.getTotalPages())
                .build();
    }
}
