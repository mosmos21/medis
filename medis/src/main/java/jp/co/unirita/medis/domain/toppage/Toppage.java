package jp.co.unirita.medis.domain.toppage;

import javax.persistence.*;
import javax.validation.constraints.Size;

import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@IdClass(value = Toppage.PK.class)
@Table(name = "toppage")
public class Toppage{

	@Id
	@Size(max = 64)
	private String employeeNumber;

	@Id
	@Size(min = 11, max = 11)
	private String boxId;

	private int toppageOrder;

    @Data
    public static class PK implements Serializable{
        private String employeeNumber;
        private String boxId;
    }
}
