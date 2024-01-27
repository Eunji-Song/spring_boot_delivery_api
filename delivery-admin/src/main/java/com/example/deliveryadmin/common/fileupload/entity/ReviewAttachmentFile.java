package com.example.deliveryadmin.common.fileupload.entity;

import com.example.deliveryadmin.domain.review.Review;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Review 첨부파일 클래스 엔티티
 * 첨부파일 용도
 * - 회원이 직접 찍은 리뷰용 사진
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewAttachmentFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_attachment_file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review reivew;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attachment_file_id", nullable = false)
    private AttachmentFile attachmentFile;

    // 모든 매개변수를 가지고 있는 생성자
    public ReviewAttachmentFile(Long id, Review reivew, AttachmentFile attachmentFile) {
        this.id = id;
        this.reivew = reivew;
        this.attachmentFile = attachmentFile;
    }
}
