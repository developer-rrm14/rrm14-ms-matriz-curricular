package com.rrm14.cliente.escola.matrizcurricular.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rrm14.cliente.escola.matrizcurricular.constantes.Queries;
import com.rrm14.cliente.escola.matrizcurricular.entity.MateriaEntity;

@Repository
public interface IMateriaRepository extends JpaRepository<MateriaEntity, Long> {
	
	@Query(Queries.QUERY_MATERIA_HORA_MINIMA)
	public List<MateriaEntity> findByHoraMinima(@Param("horaMinima")int horaMinima);
	
	@Query(Queries.QUERY_MATERIA_FREQUENCIA)
	public List<MateriaEntity> findByFrequencia(@Param("frequencia")int frequencia);

}
