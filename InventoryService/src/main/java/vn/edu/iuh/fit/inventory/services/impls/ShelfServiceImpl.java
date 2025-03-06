package vn.edu.iuh.fit.inventory.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.inventory.exceptions.ShelfException;
import vn.edu.iuh.fit.inventory.mappers.ShelfMapper;
import vn.edu.iuh.fit.inventory.models.dtos.PageDTO;
import vn.edu.iuh.fit.inventory.models.dtos.requests.ShelfRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.ShelfResponse;
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
    public PageDTO<ShelfResponse> getPagesSheft(int page, int size, String sortBy, String sortName) {
        Pageable pageable = PageRequest.of(page, size);
        if(sortBy != null && sortName != null) {
            pageable = PageRequest.of(page, size);
        }
        Set<Shelf> shelfResponsestResponses = sheltRepository.findAll(pageable).toSet();
        Set<ShelfResponse> shelfResponses = shelfResponsestResponses.stream().map(shelf -> sheftMapper.toDto((Shelf) shelf)).collect(Collectors.toSet());
        return PageDTO.<ShelfResponse>builder()
                .page(page)
                .size(size)
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
        } else {
            Optional<Shelf> oldShelf = sheltRepository.findById(request.getId());
            if(oldShelf.isEmpty()) {
                throw new ShelfException("Shelf not found");
            } else {
                shelf = sheftMapper.partialUpdate(request, oldShelf.get());
            }
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
    public List<ShelfResponse> findShelfByLocation(String location) {
        List<Shelf> shelves = sheltRepository.findShelfByLocation(location);
        return shelves.stream().map(sheftMapper::toDto).collect(Collectors.toList());
    }
}
