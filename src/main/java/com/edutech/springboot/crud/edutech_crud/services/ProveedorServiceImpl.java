package com.edutech.springboot.crud.edutech_crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.edutech.springboot.crud.edutech_crud.entities.Proveedor;

@Service
public class ProveedorServiceImpl implements ProveedorService{


    
    @Override
    public Optional<Proveedor> delete(Proveedor unProveedor) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public List<Proveedor> findByAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Proveedor> findById(Long id) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Proveedor save(Proveedor unProveedor) {
        // TODO Auto-generated method stub
        return null;
    }

}
