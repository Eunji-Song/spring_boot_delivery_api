package com.example.deliveryadmin.domain.product.repository;

import com.example.deliveryadmin.common.exception.NotFoundException;
import com.example.deliveryadmin.common.response.ResultCode;
import com.example.deliveryadmin.domain.product.ProductDto;
import com.example.deliveryadmin.domain.product.QProductDto_DetailInfo;
import com.example.deliveryadmin.domain.product.QProductDto_ListData;
import com.example.deliverycore.entity.QProduct;
import com.example.deliverycore.entity.attachmentfile.QProductAttachmentFile;
import com.querydsl.core.BooleanBuilder;
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
    public boolean isExistName(Long storeId, String productName, Long productId) {
        product = new QProduct("p");

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(product.store.id.eq(storeId)).and(product.name.eq(productName));
        if (productId != null) {
            booleanBuilder.and(product.id.ne(productId));
        }

        boolean isExist = jpaQueryFactory.select(product.id).from(product)
                .where(booleanBuilder)
                .fetchOne() != null;
        return isExist;
    }


    // == 매장 내 메뉴 로직 처리 == //


    @Override
    public List<ProductDto.ListData> getAllProducts(Long storeId) {
        product = new QProduct("p");
        productAttachmentFile = new QProductAttachmentFile("qaf");

        List<ProductDto.ListData> list = jpaQueryFactory.select(new QProductDto_ListData(product.id, product.name, product.description, product.thumbnail, product.isBest))
                .from(product)
                .leftJoin(product.thumbnail, productAttachmentFile)
                .where(product.store.id.eq(storeId))
                .fetch();
        return list;
    }


    @Override
    public ProductDto.DetailInfo getProductByIdAndStore(Long storeId, Long productId) {
        product = new QProduct("p");
        productAttachmentFile = new QProductAttachmentFile("qaf");

        ProductDto.DetailInfo detailInfo = jpaQueryFactory.select(new QProductDto_DetailInfo(product.id, product.category, product.name, product.description, product.price, product.thumbnail, product.isBest))
                .from(product)
                .leftJoin(product.thumbnail, productAttachmentFile)
                .where(product.store.id.eq(storeId), product.id.eq(productId))
                .fetchOne();


        return detailInfo;

    }
}
