package org.xqzp.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;


@Data
@TableName("user_link")
public class UserLink implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private Integer id;

	private Integer uid;

	private Integer lid;

}
