
package com.itwillbs.LaClave.Category;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Category {
    @Id
    private String code;       
    
    private String groupCode;
    
    private String name;       
    
    private String parentCode; 
    
    
}
