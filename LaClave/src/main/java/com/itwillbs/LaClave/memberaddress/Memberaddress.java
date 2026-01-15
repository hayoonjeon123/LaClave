package com.itwillbs.LaClave.memberaddress;

import java.time.LocalDateTime;

import com.itwillbs.LaClave.wishlist.Wishlist;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@Table(name = "MEMBER_ADDRESS")
public class Memberaddress {
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ADDRESS_IDX")
	private Long addressIdx;
	
	@Column(name = "MEMBER_IDX" ,nullable = false)
	private Long memberIdx;
	
	@Column(name ="RECIPIENT_NAME", nullable = false, length = 100)
	private String recipientName;
	
	@Column (name ="ADDRESS_NAME", nullable=false, length = 20)
	private String addressName;
	
	@Column (name ="PHONE",nullable=false,length =20)
	private String phone;
	
	@Column(name = "POST_CODE", nullable = false, length = 10)
	private String postCode;
	
	@Column(name = "ADDRESS", nullable = false, length = 200)
	private String address;
	
	 @Column(name = "ADDRESS_DETAIL", nullable = false, length = 200)
	private String addressDetail;
	

}
