package vn.edu.iuh.fit.inventory.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.iuh.fit.inventory.enums.SheftStatus;
import vn.edu.iuh.fit.inventory.exceptions.ShelfException;
import vn.edu.iuh.fit.inventory.mappers.ShelfMapper;
import vn.edu.iuh.fit.inventory.models.dtos.PageDTO;
import vn.edu.iuh.fit.inventory.models.dtos.requests.ShelfRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.ShelfResponse;
import vn.edu.iuh.fit.inventory.models.entities.Inventory;
import vn.edu.iuh.fit.inventory.models.entities.Shelf;
import vn.edu.iuh.fit.inventory.repositories.ShelfRepository;
import vn.edu.iuh.fit.inventory.services.ShelfService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShelfServiceImpl implements ShelfService {
    @Autowired
    private ShelfRepository sheltRepository;

    @Autowired
    private ShelfMapper sheftMapper;

    @Override
    public PageDTO<ShelfResponse> getPagesShelf(int page, int size, String sortBy, String sortName, String location) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortName), sortBy));

        Page<Shelf> shelfPage;
        if (location != null && !location.isEmpty()) {
            shelfPage = sheltRepository.findAllByLocation(location, pageable);
        } else {
            shelfPage = sheltRepository.findAll(pageable);
        }

        List<ShelfResponse> shelfResponses = shelfPage.getContent().stream()
                .map(sheftMapper::toDto)
                .collect(Collectors.toList());

        return PageDTO.<ShelfResponse>builder()
                .page(page)
                .size(size)
                .totalPage(shelfPage.getTotalPages())
                .sortBy(sortBy)
                .sortName(sortName)
                .data(shelfResponses)
                .build();
    }


    @Override
    public ShelfResponse save(ShelfRequest request) throws ShelfException {
        Shelf shelf = null;

        if(request.getId() == null) {
            shelf = sheftMapper.toEntity(request);
            shelf.setTotalProduct(0L);
            shelf.setStatus(SheftStatus.EMPTY);
            Inventory inventory = new Inventory();
            inventory.setId(1L);
            shelf.setInventory(inventory);
        } else {
            Optional<Shelf> oldShelf = sheltRepository.findById(request.getId());
            if(oldShelf.isEmpty()) {
                throw new ShelfException("Shelf not found");
            }
            shelf = sheftMapper.partialUpdate(request, oldShelf.get());
        }

        shelf = sheltRepository.save(shelf);
        return sheftMapper.toDto(shelf);
    }

    @Override
    public ShelfResponse getShelfById(Long id) {
        Optional<Shelf> shelf = sheltRepository.findById(id);
        if(shelf.isEmpty()) {
            throw new ShelfException("Shelf not found");
        }
        return sheftMapper.toDto(shelf.get());
    }

    @Override
    public void deleteShelfById(Long id) {
        sheltRepository.deleteById(id);
    }

    @Override
    public List<ShelfResponse> findShelfsWithCapacityGreaterThan(int threshold) {
        List<Shelf> shelves = sheltRepository.findShelfsWithCapacityGreaterThan(threshold);
        return shelves.stream().map(shelf -> sheftMapper.toDto(shelf)).collect(Collectors.toList());
    }

    //Cập nhật (thêm) số lượng sản phẩm của shelf khi nhập hàng hoặc hủy đơn(thêm lại vào kệ)
    @Override
    @Transactional
    public void updateTotalProduct(Long id, int quantity) {
        Optional<Shelf> shelf = sheltRepository.findById(id);
        if (shelf.isEmpty()) {
            throw new ShelfException("Shelf not found");
        }
        Shelf shelfEntity = shelf.get();
        // Cập nhật số lượng sản phẩm
        sheltRepository.updateAddTotalProduct(id, quantity);
        // Lưu lại kệ đã cập nhật
        sheltRepository.save(shelfEntity);
    }

    // Cập nhật (trừ) số lượng sản phẩm của shelf khi đặt hàng
    @Override
    @Transactional
    public void updateSubtractTotalProduct(Long id, int quantity) {
        Optional<Shelf> shelf = sheltRepository.findById(id);
        if (shelf.isEmpty()) {
            throw new ShelfException("Shelf not found");
        }
        Shelf shelfEntity = shelf.get();
        // Cập nhật số lượng sản phẩm
        sheltRepository.updateSubtractTotalProduct(id, quantity);
        // Lưu lại kệ đã cập nhật
        sheltRepository.save(shelfEntity);
    }

    public PageDTO<ShelfResponse> findLocationPage(int page, int size, String sortBy, String sortName, String location) {
        Pageable pageable = PageRequest.of(page, size);
        if(sortBy != null && sortName != null) {
            pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortName), sortBy));
        }

        Page<Shelf> shelfPage = sheltRepository.findByLocation(location, pageable);
        List<Shelf> shelves = shelfPage.getContent();
        List<ShelfResponse> shelfResponses = shelves.stream()
                .map(shelf -> sheftMapper.toDto(shelf))
                .collect(Collectors.toList());

        return PageDTO.<ShelfResponse>builder()
                .page(page)
                .size(size)
                .totalPage(shelfPage.getTotalPages())
                .sortBy(sortBy)
                .sortName(sortName)
                .data(shelfResponses)
                .build();
    }
}
