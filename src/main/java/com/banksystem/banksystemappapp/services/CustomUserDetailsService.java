package com.banksystem.banksystemappapp.services;

import com.banksystem.banksystemappapp.models.users.User;
import com.banksystem.banksystemappapp.repositories.securityRepository.UserRepository;
import com.banksystem.banksystemappapp.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    /**
     * Carga los detalles de un usuario por su nombre de usuario.
     *
     * @param userName El nombre de usuario.
     * @return Los detalles del usuario.
     * @throws UsernameNotFoundException Si el usuario no se encuentra.
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        try {
            Optional<User> user = userRepository.findByUserName(userName);

            if (!user.isPresent()) {
                throw new UsernameNotFoundException("Usuario no encontrado");
            }

            CustomUserDetails customUserDetails = new CustomUserDetails(user.get());

            return customUserDetails;
        } catch (Exception e) {
            throw new UsernameNotFoundException("Error al cargar los detalles del usuario");
        }
    }
}