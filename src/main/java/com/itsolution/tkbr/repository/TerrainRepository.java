/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.repository;

import com.itsolution.tkbr.domain.Terrain;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Terrain entity.
 */
public interface TerrainRepository extends JpaRepository<Terrain, Long> {

    public List<Terrain> findByTerrainParentId(Long id);

    

}




