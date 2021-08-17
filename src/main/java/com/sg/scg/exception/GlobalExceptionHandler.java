package com.sg.scg.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Mono;

@Order(-1)
@Component
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

	private final ObjectMapper objectMapper;

	public GlobalExceptionHandler() {
		this.objectMapper = new ObjectMapper();
	}

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		ex.printStackTrace();

		ServerHttpResponse response = exchange.getResponse();
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

		HttpStatus status;
		if (ex instanceof AbstractException) {
			status = ((AbstractException) ex).getStatus();
		} else if (ex instanceof ResponseStatusException) {
			status = ((ResponseStatusException) ex).getStatus();
		} else {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		response.setStatusCode(status);

		return response.writeWith(Mono.fromSupplier(() -> {
			byte[] data;
			Map<String, String> res = new HashMap<String, String>();
			res.put("message", ex.getMessage());
			try {
				data = objectMapper.writeValueAsBytes(res);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
				data = new byte[0];
			}
			return response.bufferFactory().wrap(data);
		}));
	}
}
