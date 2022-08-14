package com.joypedze.assetmanager.service;

import java.util.List;

import com.joypedze.assetmanager.model.Asset;
import org.springframework.data.domain.Page;

public interface AssetService {
    List<Asset> getAllAssets();
    void saveAsset(Asset asset);
    Asset getAssetById(long id);
    void deleteAssetById(long id);
    Page<Asset> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
