package com.itwillbs.LaClave.commoncode;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "COMMON_CODE")
@Getter
public class CommonCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMON_IDX")
    private Long commonIdx;

    @Column(name = "GROUP_CODE", length = 30)
    private String groupCode;      // COLOR, SIZE 등

    @Column(name = "CODE", length = 30)
    private String code;           // color_01

    @Column(name = "CODE_LABEL", length = 50)
    private String codeLabel;      // 블랙, M

    @Column(name = "CODE_DESC", length = 300)
    private String codeDesc;

    @Column(name = "PARENT_CODE_ID")
    private Long parentCodeId;

    @Column(name = "CODE_INDEX")
    private Integer codeIndex;

    @Column(name = "USING_STATUS", length = 1)
    private String usingStatus;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;
}