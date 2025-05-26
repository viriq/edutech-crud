package com.edutech.springboot.crud.edutech_crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edutech.springboot.crud.edutech_crud.entities.Proveedor;
import com.edutech.springboot.crud.edutech_crud.repository.ProveedorRepository;

@Service
public class ProveedorServiceImpl implements ProveedorService{

    @Autowired
    private ProveedorRepository repository;

    @Override
    @Transactional
    public Optional<Proveedor> delete(Proveedor unProveedor) {
        Optional<Proveedor> proveedorOptional = repository.findById(unProveedor.getId());
        proveedorOptional.ifPresent(proveedorDb->{
            repository.delete(unProveedor);
        });
        return proveedorOptional;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Proveedor> findByAll() {
        return (List<Proveedor>)repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Proveedor> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Proveedor save(Proveedor unProveedor) {
        return repository.save(unProveedor);
    }

}
