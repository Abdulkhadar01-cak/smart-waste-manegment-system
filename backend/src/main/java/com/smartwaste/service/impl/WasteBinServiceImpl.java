package com.smartwaste.service.impl;

import com.smartwaste.dto.WasteBinDto;
import com.smartwaste.entity.WasteBin;
import com.smartwaste.exception.ResourceNotFoundException;
import com.smartwaste.repository.WasteBinRepository;
import com.smartwaste.service.WasteBinService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * WasteBinServiceImpl
 * 
 * Default implementation of WasteBinService.
 * Contains business logic, entity-DTO mapping, and transaction management.
 */
@Service
@Transactional
public class WasteBinServiceImpl implements WasteBinService {

    private final WasteBinRepository wasteBinRepository;

    public WasteBinServiceImpl(WasteBinRepository wasteBinRepository) {
        this.wasteBinRepository = wasteBinRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WasteBinDto> getAllBins() {
        return wasteBinRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public WasteBinDto getBinById(Long id) {
        WasteBin bin = wasteBinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WasteBin", "id", id));
        return mapToDto(bin);
    }

    @Override
    @Transactional(readOnly = true)
    public WasteBinDto getBinByCode(String binCode) {
        WasteBin bin = wasteBinRepository.findByBinCode(binCode)
                .orElseThrow(() -> new ResourceNotFoundException("WasteBin", "binCode", binCode));
        return mapToDto(bin);
    }

    @Override
    public WasteBinDto createBin(WasteBinDto binDto) {
        if (wasteBinRepository.existsByBinCode(binDto.getBinCode())) {
            throw new IllegalArgumentException("Bin code already in use: " + binDto.getBinCode());
        }

        WasteBin bin = mapToEntity(binDto);
        WasteBin savedBin = wasteBinRepository.save(bin);
        return mapToDto(savedBin);
    }

    @Override
    public WasteBinDto updateFillLevel(Long id, Double fillLevel) {
        WasteBin bin = wasteBinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WasteBin", "id", id));

        bin.setFillLevelPercentage(fillLevel);
        if (fillLevel >= 85.0) {
            bin.setStatus("FULL");
        } else {
            bin.setStatus("ACTIVE");
        }

        WasteBin updatedBin = wasteBinRepository.save(bin);
        return mapToDto(updatedBin);
    }

    @Override
    public void deleteBin(Long id) {
        if (!wasteBinRepository.existsById(id)) {
            throw new ResourceNotFoundException("WasteBin", "id", id);
        }
        wasteBinRepository.deleteById(id);
    }

    /**
     * Maps a WasteBin JPA Entity to a WasteBinDto.
     */
    private WasteBinDto mapToDto(WasteBin entity) {
        return new WasteBinDto(
                entity.getId(),
                entity.getBinCode(),
                entity.getWasteType(),
                entity.getCapacityLiters(),
                entity.getFillLevelPercentage(),
                entity.getStatus(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getAddress()
        );
    }

    /**
     * Maps a WasteBinDto to a WasteBin JPA Entity.
     */
    private WasteBin mapToEntity(WasteBinDto dto) {
        WasteBin bin = new WasteBin(
                dto.getBinCode(),
                dto.getWasteType(),
                dto.getCapacityLiters(),
                dto.getLatitude(),
                dto.getLongitude(),
                dto.getAddress()
        );
        if (dto.getFillLevelPercentage() != null) {
            bin.setFillLevelPercentage(dto.getFillLevelPercentage());
        }
        if (dto.getStatus() != null) {
            bin.setStatus(dto.getStatus());
        }
        return bin;
    }
}
