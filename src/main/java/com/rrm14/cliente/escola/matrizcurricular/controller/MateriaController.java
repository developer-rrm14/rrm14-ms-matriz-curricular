package com.rrm14.cliente.escola.matrizcurricular.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/materia")
public class MateriaController {

	@GetMapping("/")
	public ResponseEntity<String> init(){
		return ResponseEntity.status(HttpStatus.OK).body("Inicializando Rest Materia");
	}
}
