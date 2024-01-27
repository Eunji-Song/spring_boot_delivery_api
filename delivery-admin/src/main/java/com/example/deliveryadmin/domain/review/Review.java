package com.example.deliveryadmin.domain.review;

import com.example.deliveryadmin.common.entity.BaseEntity;
import com.example.deliveryadmin.domain.order.Order;
import com.example.deliveryadmin.domain.store.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review")
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order; // 어떤 주문건에 대한 리뷰인지 확인하기 위한 용도

    private Long userId; // 아직 회원 엔티티가 존재하지 않으므로 Long으로 할당

    private int score; // 평가 점수

    private String content;

//    @OneToMany(cascade = CascadeType.ALL)
//    private List<AttachmentFile> attachmentFiles;
}
