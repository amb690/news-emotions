package com.markettrender.newsemotions.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.markettrender.newsemotions.models.entity.Asset;
import com.markettrender.newsemotions.service.AssetService;

@RestController
@RequestMapping("/asset")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE})
public class AssetController {

	@Autowired
	public AssetService assetService;

	@GetMapping("/{ticker}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getAssetFromTicker(@PathVariable @NotBlank String ticker) throws Exception {

		Asset response = assetService.findByTicker1(ticker);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
	@GetMapping("")
	public ResponseEntity<?> list() {
		
		List<Asset> result = assetService.findAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {
			assetService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el activo en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente ha sido eliminado con éxito!");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
