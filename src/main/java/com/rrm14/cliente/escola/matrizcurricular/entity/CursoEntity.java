package com.rrm14.cliente.escola.matrizcurricular.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tb_curso")
@Data
@NoArgsConstructor
public class CursoEntity implements Serializable {

	private static final long serialVersionUID = 5711748273812967415L;

	@JsonInclude(Include.NON_NULL)
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy="increment")
	@Id
	@Column(name="id")
	private Long id;
	
	@JsonInclude(Include.NON_EMPTY)
	@Column(name="nome")
	private String nome;
	
	@JsonInclude(Include.NON_EMPTY)
	@Column(name="codigo")
	private String codigo;
	

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="curso_id")
	private List<MateriaEntity> materias;
	

}
