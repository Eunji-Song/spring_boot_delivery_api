package com.example.deliveryuser.domain.product.repository;

import com.example.deliverycore.entity.Product;
import com.example.deliverycore.entity.QProduct;
import com.example.deliverycore.entity.attachmentfile.QProductAttachmentFile;
import com.example.deliveryuser.domain.product.ProductDto;
import com.example.deliveryuser.domain.product.QProductDto_DetailInfo;
import com.example.deliveryuser.domain.product.QProductDto_ListData;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    private QProduct product;
    private QProductAttachmentFile productAttachmentFile;

    @Override
    public List<ProductDto.ListData> getAllProducts(Long storeId) {
        product = new QProduct("p");
        productAttachmentFile = new QProductAttachmentFile("paf");

        List<ProductDto.ListData> list = jpaQueryFactory.select(new QProductDto_ListData(product.id, product.name, product.description, product.thumbnail, product.isBest))
                .from(product)
                .leftJoin(product.thumbnail, productAttachmentFile)
                .where(product.store.id.eq(storeId))
                .fetch();

        return list;
    }

    @Override
    public ProductDto.DetailInfo getProduct(Long storeId, Long productId) {
        product = new QProduct("p");
        productAttachmentFile = new QProductAttachmentFile("paf");

        ProductDto.DetailInfo detailInfo = jpaQueryFactory.select(new QProductDto_DetailInfo(product.id, product.name, product.description, product.price, product.thumbnail, product.isBest))
                .from(product)
                .leftJoin(product.thumbnail, productAttachmentFile)
                .where(product.store.id.eq(storeId))
                .fetchOne();
        return null;
    }

    @Override
    public Product getProductPrice(Long storeId, Long productId) {
        product = new QProduct("p");
        Product productInfo = jpaQueryFactory
                                    .select(Projections.constructor(Product.class, product.id, product.price))
                                    .from(product)
                                    .where(product.store.id.eq(storeId), product.id.eq(productId))
                                    .fetchFirst();
        return productInfo;
    }
}
