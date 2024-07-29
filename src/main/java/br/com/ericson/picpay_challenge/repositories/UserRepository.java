package br.com.ericson.picpay_challenge.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ericson.picpay_challenge.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
