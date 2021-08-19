package com.rrm14.cliente.escola.matrizcurricular.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rrm14.cliente.escola.matrizcurricular.constantes.Queries;
import com.rrm14.cliente.escola.matrizcurricular.entity.CursoEntity;

@Repository
public interface ICursoRepository extends JpaRepository<CursoEntity, Long> {
	
	   @Query(Queries.QUERY_CURSO_POR_CODIGO)
	   public CursoEntity findCursoByCodigo(@Param("codigo") String codigo);
	 	
}
