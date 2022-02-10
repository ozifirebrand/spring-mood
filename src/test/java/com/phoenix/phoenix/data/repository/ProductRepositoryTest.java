package com.phoenix.phoenix.data.repository;

import com.phoenix.phoenix.data.models.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
@Sql(scripts = {"/db/insert.sql"})
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testSaveProductToDatabaseTest(){
        //create a new product
//        Product product = new Product();
//        product.setName("Omo");
//        product.setPrice(55);
//        product.setDescription("A detergent from Omo");
//        product.setQuantity(10);
//        product.setImageUrl("product.detergent.omo1");
//        assertThat(product.getId()).isNull();
//        // save the product
//        productRepository.save(product);
//        log.info("Product Saved :: {}", product);
//        assertThat(product.getId()).isNotNull();
//        assertThat(product.getName()).isEqualTo("Omo");
//        assertThat(product.getPrice()).isEqualTo(55);
//        assertThat(product.getDateCreated()).isNotNull();

    }

    @Test
    public void testFindExistingProductFromDatabase(){
        Product product = productRepository.findById(12L).orElse(null);
        assertThat(product).isNotNull();
        assertThat(product.getId()).isEqualTo(12);
        assertThat(product.getName()).isEqualTo("Ariel");
        assertThat(product.getPrice()).isEqualTo(554);
        assertThat(product.getQuantity()).isEqualTo(3);

        log.info("Product retrieved :: {}", product);
    }

    @Test
    public void testFindAllProducts(){
        List<Product> products = productRepository.findAll();
        assertThat(products.size()).isEqualTo(4);
        assertThat(products).isNotNull();
    }

    @Test
    public void testFindProductByName(){
        Product product = productRepository.findProductByName("Omo");
        assertThat(product.getQuantity()).isEqualTo(3);
        assertThat(product.getName()).isEqualTo("Omo");
        assertThat(product.getPrice()).isEqualTo(554);

        log.info("Product retrieved :: {}", product);
    }

    @Test
    public void testUpdateProduct(){
        Product savedProduct = productRepository.findProductByName("Omo");
        assertThat(savedProduct.getName()).isEqualTo("Omo");
        assertThat(savedProduct.getPrice()).isEqualTo(554);
        savedProduct.setName("Tura");
        savedProduct.setPrice(500);
        productRepository.save(savedProduct);
        assertThat(savedProduct.getName()).isEqualTo("Tura");
        assertThat(savedProduct.getPrice()).isEqualTo(500);
    }
}