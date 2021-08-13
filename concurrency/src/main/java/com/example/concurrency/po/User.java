package com.example.concurrency.po;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 郑启
 * @date 2020/07/21 17:32
 */
@Data
@Accessors(fluent = true)
public class User {
	private volatile String usnm;
	private String pswd;
	public volatile int age;
}
