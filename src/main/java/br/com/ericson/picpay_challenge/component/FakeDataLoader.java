package br.com.ericson.picpay_challenge.component;

import com.github.javafaker.Faker;

import br.com.ericson.picpay_challenge.entity.UserEntity;
import br.com.ericson.picpay_challenge.repositories.UserRepository;

import java.math.BigDecimal;
import java.util.Random;

import org.hibernate.usertype.UserType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FakeDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final Faker faker;
    private final Random random;

    public FakeDataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.faker = new Faker();
        this.random = new Random();
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            for (int i = 0; i < 100; i++) {
                UserEntity user = new UserEntity();
                user.setName(faker.name().fullName());
                if (random.nextBoolean()) {
                    user.setUserType("PF");
                    user.setCpfCnpj(faker.number().digits(11));
                } else {
                    user.setUserType("PJ");
                    user.setCpfCnpj(faker.number().digits(14));
                }
                user.setBalance(new BigDecimal(faker.number().randomDouble(2, 0, 10000)));
                user.setEmail(faker.internet().emailAddress());
                user.setPassword(faker.internet().password());
                userRepository.save(user);
            }

            System.out.println("Dados falsos gerados com sucesso!");
        } else {
            System.out.println("Dados já existem no banco de dados. Nenhum dado novo foi gerado.");
        }
    }
}