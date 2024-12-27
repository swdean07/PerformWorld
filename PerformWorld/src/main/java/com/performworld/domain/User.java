package com.performworld.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "users")
@Getter
@ToString(exclude = {"password"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "total_spent", precision = 10, scale = 2)
    private Long totalSpent;

    @OneToOne
    @JoinColumn(name = "tier_id", referencedColumnName = "tier_id")
    private Tier tier;

    @Column(name = "address1", length = 255)
    private String address1;

    @Column(name = "address2", length = 255)
    private String address2;

    @Column(name = "postcode", length = 20)
    private String postcode;

    // 이메일을 반환하는 메서드를 추가 (getUsername 대신)
    public String getUsername() {
        return this.email; // 이메일을 사용자 이름으로 사용
    }
}

