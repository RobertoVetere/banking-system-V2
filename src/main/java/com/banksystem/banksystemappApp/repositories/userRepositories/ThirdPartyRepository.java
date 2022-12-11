package com.banksystem.banksystemappApp.repositories.userRepositories;

import com.banksystem.banksystemappApp.models.users.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThirdPartyRepository extends JpaRepository<ThirdParty,Long> {

    Optional<ThirdParty>  findByName (String userName);

    Optional<ThirdParty>  findByUserName (String userName);
}
