package com.example.staservice.client;

import com.example.commonutils.R;

/**
 * com.example.staservice.client
 *
 * @author xzwnp
 * 2022/12/31
 * 19:59
 */
public class UcenterClientFallBack implements UcenterClient {

	@Override
	public R registerCount(String day) {
		return R.error().message("用户服务不可用");
	}
}
