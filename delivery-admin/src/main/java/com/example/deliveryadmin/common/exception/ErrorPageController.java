package com.example.deliveryadmin.common.exception;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
@Hidden
public class ErrorPageController{
    @GetMapping("/error-page.400")
    public void error400(HttpServletResponse response) throws IOException {
        log.info("ErrorPageController - 400");
        response.sendError(400, "400 오류!");
    }

    @GetMapping("/error-page/404")
    public void error404(HttpServletResponse response) throws IOException {
        response.sendError(404, "404 오류!");
        log.info("ErrorPageController - 404");
    }

    @GetMapping("/error-page/500")
    public void error500(HttpServletResponse response) throws IOException {
        System.out.println("500 Eror");
        log.info("ErrorPageController - 500");
        response.sendError(500);
    }
}
