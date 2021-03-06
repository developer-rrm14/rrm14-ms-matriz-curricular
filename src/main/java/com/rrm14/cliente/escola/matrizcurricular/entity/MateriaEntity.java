package com.rrm14.cliente.escola.matrizcurricular.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tb_materia")
@Data
@NoArgsConstructor
public class MateriaEntity implements Serializable {

	private static final long serialVersionUID = 3689364809634054805L;
	
	@JsonInclude(Include.NON_NULL)
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy="increment")
	@Id
	@Column(name="id")
	private Long id;
	
	@JsonInclude(Include.NON_EMPTY)
	@Column(name="nome")
	private String nome;
	
	@Column(name="horas")
	private int horas;
	
	@JsonInclude(Include.NON_EMPTY)
	@Column(name="codigo")
	private String codigo;
	
	@Column(name="frequencia")
	private int frequencia;
	

}
