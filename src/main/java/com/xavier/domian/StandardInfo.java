package com.xavier.domian;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @description: 用户实体
 * @author: ex_wuzr11
 * @date: 2024/10/22 16:19
 */
@Entity
@Table(name = "user_case")
@Data
public class StandardInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", length = 32)
    private Long userId;

    @Column(name = "user_name", length = 30)
    private String userName;

    @Column(name = "email", length = 50)
    private String email;

}
