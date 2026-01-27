package com.itwillbs.LaClave.category;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "COMMON_CODE")
@Data
public class Category {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMON_IDX") 
    private Long commonIdx;       
    
    @Column(name = "GROUP_CODE") 
    private String groupCode;
    
    @Column(name = "CODE") 
    private String code;       
    
    @Column(name = "CODE_DESC") 
    private String codeDesc; 
    
    
}
