package com.inhabas.api.domain.member.domain.entity;

import com.inhabas.api.domain.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ANSWER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NO")
    private Integer no;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_STUDENT_ID", foreignKey = @ForeignKey(name = "FK_ANSWER_OF_MEMBER"))
    private Member member;

    @Column(name = "QUEST_NO", nullable = false)
    private Integer questNo;

    @Column(name = "CONTENT", nullable = false, length = 1000)
    private String CONTENT;

    public Answer(Member member, Integer questionNo, String content) {
        this.member = member;
        this.questNo = questionNo;
        this.CONTENT = content;
    }
}
