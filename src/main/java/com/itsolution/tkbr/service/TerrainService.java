/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.service;

import com.itsolution.tkbr.domain.Terrain;
import com.itsolution.tkbr.repository.TerrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tchipi
 */
@Service
@Transactional
public class TerrainService {

    @Autowired
    TerrainRepository terrainRepository;

    public Terrain update(Terrain terrain) throws Exception {
        if (terrain.getTerrainParent() != null) {
            //update lotissement
            Terrain parent = terrainRepository.findOne(terrain.getTerrainParent().getId());
            Terrain oldValue = terrainRepository.findOne(terrain.getId());
            parent.setSurfaceMorcellee(parent.getSurfaceMorcellee() + terrain.getSurface() - oldValue.getSurface());
            terrainRepository.save(parent);
        }
        return terrainRepository.save(terrain);

    }

    public Terrain create(Terrain terrain) throws Exception {
        if (terrain.getTerrainParent() != null) {
            //creation lotissement
            Terrain parent = terrainRepository.findOne(terrain.getTerrainParent().getId());
            parent.setSurfaceMorcellee(parent.getSurfaceMorcellee() + terrain.getSurface());
            terrainRepository.save(parent);
        }
        return terrainRepository.save(terrain);
    }

    public Terrain delete(Long id) throws Exception {
        Terrain terrain = terrainRepository.findOne(id);
        if (terrain.getTerrainParent() != null) {
            //creation lotissement
            Terrain parent = terrainRepository.findOne(terrain.getTerrainParent().getId());
            parent.setSurfaceMorcellee(parent.getSurfaceMorcellee() -terrain.getSurface());
            terrainRepository.save(parent);
        }
        return terrainRepository.save(terrain);
    }

}
