package br.com.ericson.picpay_challenge.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ericson.picpay_challenge.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findById(Long id);
}
