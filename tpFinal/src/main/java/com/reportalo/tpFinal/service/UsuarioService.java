package com.reportalo.tpFinal.service;

import com.reportalo.tpFinal.model.Usuario;
import com.reportalo.tpFinal.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //metodo para buscar por id
    public Usuario getUserById(Long id){
        if(id == null || id <= 0){
            throw new IllegalArgumentException("El id del usuario no puede ser nulo o negativo");
        }

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if(usuarioOptional.isEmpty()){
            throw new IllegalArgumentException("No existe un usuario con el id: " + id);
        }

        return usuarioOptional.get();
    }

    //traer todos
    public List<Usuario> getAllUsers(){
        try{
           List<Usuario>  usuarios = usuarioRepository.findAll();
            if(usuarios.isEmpty()){
                throw new IllegalArgumentException("No hay usuarios registrados");
            }
            return usuarios;
        }catch(RuntimeException e){
            throw new IllegalArgumentException("No hay usuarios registrados" + e.getMessage());
        }
    }

    //Agregar usuario
    public Usuario addUser(Usuario usuario){
        if(usuario == null){
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        try {
            return  usuarioRepository.save(usuario);
        }catch (RuntimeException e){
            throw new RuntimeException("No se pudo agregar el usuario" + e.getMessage());
        }
    }
    //Eliminar usuario
    public void deleteUser(Long id){
        if(id == null || id <= 0){
            throw new IllegalArgumentException("El id del usuario no puede ser nulo o negativo");
        }
        try {
           if(!usuarioRepository.existsById(id)){
                throw new IllegalArgumentException("No existe un usuario con el id: " + id);
            }
            usuarioRepository.deleteById(id);
        }catch (RuntimeException e){
            throw new RuntimeException("No se pudo eliminar el usuario" + e.getMessage());
        }
    }

    //modificar usuario
    public Usuario updateUser(long id, Usuario usuarioActualizado){
        if(usuarioActualizado == null){
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        try {
            Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
            if(usuarioOptional.isEmpty()){
                throw new IllegalArgumentException("No existe un usuario con el id: " + id);
            }
            Usuario u = usuarioOptional.get();
            u.setUsername(usuarioActualizado.getUsername());
            u.setNombre(usuarioActualizado.getNombre());
            u.setApellido(usuarioActualizado.getApellido());
            u.setEmail(usuarioActualizado.getEmail());
            u.setDni(usuarioActualizado.getDni());
            return usuarioRepository.save(u);
        }catch (RuntimeException e){
            throw new RuntimeException("No se pudo actualizar el usuario" + e.getMessage());
        }
    }
}
