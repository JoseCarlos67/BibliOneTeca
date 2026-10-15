package com.jcarlos67.biblioneteca.controller;

import com.jcarlos67.biblioneteca.service.PhysicalCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/physical-copies")
public class PysicalCopyController {

  @Autowired
  private PhysicalCopyService service;
//
//  @PostMapping
//  public ResponseEntity<PhysicalCopyResponseDTO> createPhysicalCopy(@RequestBody PhysicalCopyCreateDTO dto) {
//    PhysicalCopy savedCopy = service.create(dto);
//
//    PhysicalCopyResponseDTO response = new PhysicalCopyResponseDTO(
//            savedCopy.getId(),
//            savedCopy.getAssetCode(),
//            savedCopy.getStatus().name()
//    );
//    return ResponseEntity.status(HttpStatus.CREATED).body(response);
//  }

}
