package com.joypedze.assetmanager.controller;

import java.util.List;

import com.joypedze.assetmanager.model.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.joypedze.assetmanager.service.AssetService;

@Controller
public class AssetController {

    @Autowired
    private AssetService assetService;

    // display list of assets
    @GetMapping("/")
    public String viewHomePage(Model model) {
        return findPaginated(1, "name", "asc", model);
    }

    @GetMapping("/showNewAssetForm")
    public String showNewAssetForm(Model model) {
        // create model attribute to bind form data
        Asset asset = new Asset();
        model.addAttribute("asset", asset);
        return "new_asset";
    }

    @PostMapping("/saveAsset")
    public String saveAsset(@ModelAttribute("asset") Asset asset) {
        // save asset to database
        assetService.saveAsset(asset);
        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {

        // get asset from the service
        Asset asset = assetService.getAssetById(id);

        // set asset as a model attribute to pre-populate the form
        model.addAttribute("asset", asset);
        return "update_asset";
    }

    @GetMapping("/deleteAsset/{id}")
    public String deleteAsset(@PathVariable (value = "id") long id) {

        // call delete asset method
        this.assetService.deleteAssetById(id);
        return "redirect:/";
    }


    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;

        Page<Asset> page = assetService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Asset> listAssets = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listAssets", listAssets);
        return "index";
    }
}
