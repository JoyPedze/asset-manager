package com.joypedze.assetmanager.service;

import java.util.List;
import java.util.Optional;

import com.joypedze.assetmanager.model.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.joypedze.assetmanager.repository.AssetRepository;

@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetRepository assetRepository;

    @Override
    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    @Override
    public void saveAsset(Asset asset) {
        this.assetRepository.save(asset);
    }

    @Override
    public Asset getAssetById(long id) {
        Optional<Asset> optional = assetRepository.findById(id);
        Asset asset = null;
        if (optional.isPresent()) {
            asset = optional.get();
        } else {
            throw new RuntimeException(" Asset not found for id :: " + id);
        }
        return asset;
    }

    @Override
    public void deleteAssetById(long id) {
        this.assetRepository.deleteById(id);
    }

    @Override
    public Page<Asset> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.assetRepository.findAll(pageable);
    }
}
