package com.example.deliveryadmin.common.util;

import com.querydsl.core.types.OrderSpecifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class PagingUtil {
    public static Pageable getPageable(int page, int size, String sort, String dir) {
        Sort.Direction direction = Sort.Direction.fromString(dir);
        Sort sortable = Sort.by(direction, sort);
        return PageRequest.of(page, size, sortable);
    }

    public static List<OrderSpecifier> getorderSpecifierList(Pageable pageable) {
        log.info("util - sort : {}", pageable.getSort());
        Sort sort = pageable.getSort();

        return new ArrayList<>();
    }
}
