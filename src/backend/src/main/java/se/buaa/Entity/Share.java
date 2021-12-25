package se.buaa.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "share")
public class Share {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shareId;
    private int sharerUserId;
    private String shareContent;
    private Date shareCreateTime;
    private int supportCnt;
    private int unSupportCnt;
}
