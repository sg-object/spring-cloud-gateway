package com.sg.scg.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
public class UrlFilter extends AbstractGatewayFilterFactory<UrlFilter.Config> {

	private final Logger logger = LogManager.getLogger(getClass());

	public UrlFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return new OrderedGatewayFilter(((exchange, chain) -> {
			logger.info("filter!!!");
			return chain.filter(exchange).then();
		}), 10);
	}

	@Data
	public static class Config {
		private String test;
	}
}
