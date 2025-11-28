package com.hieunn.productservice.data;

import com.hieunn.productservice.entities.Product;
import com.hieunn.productservice.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements ApplicationRunner {
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (productRepository.count() == 0) {
            log.info("Starting data initialization...");

            Product aquafina = new Product();
            aquafina.setName("Aquafina");
            aquafina.setDescription("Bottled water");
            aquafina.setPrice(7000);

            log.info("Created product 1: name = {}, description = {}, price = {}",
                    aquafina.getName(),
                    aquafina.getDescription(),
                    aquafina.getPrice());

            Product vinamilk = new Product();
            vinamilk.setName("Vinamilk");
            vinamilk.setDescription("Canned milk");
            vinamilk.setPrice(12000);

            log.info("Created product 2: name = {}, description = {}, price = {}",
                    vinamilk.getName(),
                    vinamilk.getDescription(),
                    vinamilk.getPrice());

            Product logitechMouse = new Product();
            logitechMouse.setName("Logitech Mouse");
            logitechMouse.setDescription("Mouse gaming");
            logitechMouse.setPrice(150000);

            log.info("Created product 3: name = {}, description = {}, price = {}",
                    logitechMouse.getName(),
                    logitechMouse.getDescription(),
                    logitechMouse.getPrice());

            Product laptopAsus = new Product();
            laptopAsus.setName("Laptop Asus");
            laptopAsus.setDescription("Laptop gaming");
            laptopAsus.setPrice(23000000);

            log.info("Created product 4: name = {}, description = {}, price = {}",
                    laptopAsus.getName(),
                    laptopAsus.getDescription(),
                    laptopAsus.getPrice());

            productRepository.saveAll(Arrays.asList(aquafina, vinamilk, logitechMouse, laptopAsus));

            log.info("Finished data initialization...");
        }
    }
}
