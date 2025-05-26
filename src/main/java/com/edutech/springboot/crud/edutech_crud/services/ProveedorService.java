package com.edutech.springboot.crud.edutech_crud.services;

import java.util.List;
import java.util.Optional;

import com.edutech.springboot.crud.edutech_crud.entities.Proveedor;

public interface ProveedorService {

    List<Proveedor> findByAll();

    Optional<Proveedor> findById(Long id);

    Proveedor save(Proveedor unProveedor);

    Optional<Proveedor> delete(Proveedor unProveedor);
}
