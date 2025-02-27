package vn.edu.iuh.fit.product.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.product.mappers.MedicineMapper;
import vn.edu.iuh.fit.product.mappers.TagMapper;
import vn.edu.iuh.fit.product.models.dtos.responses.MedicineResponse;
import vn.edu.iuh.fit.product.models.dtos.responses.TagByObjectResponse;
import vn.edu.iuh.fit.product.models.entities.Medicine;
import vn.edu.iuh.fit.product.models.entities.Tag;
import vn.edu.iuh.fit.product.repositories.MedicineRepository;
import vn.edu.iuh.fit.product.repositories.TagRepository;
import vn.edu.iuh.fit.product.services.TagService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private MedicineMapper medicineMapper;

    @Override
    public Set<TagByObjectResponse> getObject() {

        Set<Tag> tags = tagRepository.findByGroupId(1L);
        Pageable pageable = PageRequest.of(0, 8);

        return tags.stream().map(t -> {
            TagByObjectResponse tagByObjectResponse = tagMapper.toDtoByObject(t);
            Set<Medicine> medicines = medicineRepository.findAllByTags_Id(t.getId(), pageable).get().collect(Collectors.toSet());
            Set<MedicineResponse> medicineResponses = medicines.stream().map(medicineMapper::toDto).collect(Collectors.toSet());
            tagByObjectResponse.setMedicines(medicineResponses);
            return tagByObjectResponse;
        }).collect(Collectors.toSet());

    }
}
