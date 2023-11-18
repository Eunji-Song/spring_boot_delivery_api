package song.deliveryapi.product;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    // DTO List -> Entity List
    public List<Product> convertDTOListToEntityList(List<ProductDto> productDtoList) {
        List<Product> entityList = productDtoList.stream().map(this::convertDtoToEntity).toList();
        log.info("convert list {}", entityList);
        return entityList;
    }

    // DTO -> Entity
    public Product convertDtoToEntity(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);;
        return product;
    }

    // Entity List -> DTO List
    public List<ProductDto> convertEntityToDtoList(List<Product> productEntityList) {
        // return productList.stream().map(source -> modelMapper.map(source, ProductDto.class)).collect(Collectors.toList());;;
        return productEntityList.stream().map(this::convertEntityToDto).toList();
    }

    public ProductDto convertEntityToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        return productDto;
    }



    public List<Product> getList() {
        return productRepository.findAll();
    }

    public ProductDto getDetailInfo(Long id) {
        Product product = productRepository.findById(id).get();
        return convertEntityToDto(product);
    }

    @Transactional
    public List<ProductDto> save(List<ProductDto> productDtoList) {
        // DTO -> Entity 변환
        List<Product> productList = productRepository.saveAll(this.convertDTOListToEntityList(productDtoList));
        log.info("entity list {}", productList);

        return convertEntityToDtoList(productList);
    }

    @Transactional
    // TODO : 입력한지 않은 컬럼의 데이터가 null로 업데이트 되는 현상 수정 코드 추가
    public ProductDto update(Long id, ProductDto productDto) {
        Boolean isExistData = productRepository.existsById(id);
        if (isExistData) {
            productDto.setId(id);
            Product updateEntity = modelMapper.map(productDto, Product.class);
            Product product = productRepository.save(updateEntity);
            return modelMapper.map(product, ProductDto.class);
        }

        return new ProductDto();
    }


    @Transactional
    public List<ProductDto> delete(Long id) {
        productRepository.deleteById(id);

        List<Product> productEntityList = productRepository.findAll();
        return productEntityList.stream().map(this::convertEntityToDto).toList();
    }
}
