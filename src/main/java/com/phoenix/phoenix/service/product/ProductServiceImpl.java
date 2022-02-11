package com.phoenix.phoenix.service.product;

import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.phoenix.phoenix.data.dto.ProductDto;
import com.phoenix.phoenix.data.models.Product;
import com.phoenix.phoenix.data.repository.ProductRepository;
import com.phoenix.phoenix.service.cloud.CloudinaryService;
import com.phoenix.phoenix.web.exceptions.BusinessLogicException;
import com.phoenix.phoenix.web.exceptions.ProductDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product findProductById(Long id) throws ProductDoesNotExistException {
        if ( id == null ){
            throw new IllegalArgumentException("Id cannot be null");
        }
        Optional<Product> optionalProduct = productRepository.findById(id);
        if ( optionalProduct.isPresent()) return optionalProduct.get();

        throw new ProductDoesNotExistException("Product with id: "+id+" does not exist");
    }

    @Override
    public Product createProduct(ProductDto productDto) throws BusinessLogicException {

        //product dto is not null
        if ( productDto == null ) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        if ( productRepository.findProductByName(productDto.getName()) != null ) throw new BusinessLogicException("Product already exists");

        Product product = new Product();

        try{
            if ( productDto.getImage()!= null ) {
                Map<?, ?> uploadResult = cloudinaryService
                        .upload(productDto.getImage().getBytes(), ObjectUtils.asMap("public_id",
                                "inventory/" + productDto.getImage().getOriginalFilename(), "overwrite", true));
                product.setImageUrl(uploadResult.get("url").toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setDescription(productDto.getDescription());

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long productId,JsonPatch patch) throws BusinessLogicException, JsonPatchException, JsonProcessingException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if ( optionalProduct.isEmpty() ) throw new BusinessLogicException
                ("Product with ID "+productId+" does not exist");
        Product targetProduct = optionalProduct.get();

        try{
            targetProduct = applyPatchToProduct(patch, targetProduct);
            return productRepository.save(targetProduct);

        }catch (JsonPatchException|JsonProcessingException exception){
            throw new BusinessLogicException("Update failed");
        }
    }

    private Product applyPatchToProduct(JsonPatch patch, Product targetProduct) throws JsonProcessingException, JsonPatchException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode patched = patch.apply(mapper.convertValue(targetProduct, JsonNode.class));

        return mapper.treeToValue(patched, Product.class);
    }
}
