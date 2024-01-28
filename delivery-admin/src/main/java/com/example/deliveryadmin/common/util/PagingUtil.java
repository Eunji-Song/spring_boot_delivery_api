package com.example.deliveryadmin.common.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PagingUtil {
    public static Pageable getPageable(int page, int size, String sort, String dir) {
        Sort.Direction direction = Sort.Direction.fromString(dir);
        Sort sortable = Sort.by(direction, sort);
        return PageRequest.of(page, size, sortable);
    }
}
