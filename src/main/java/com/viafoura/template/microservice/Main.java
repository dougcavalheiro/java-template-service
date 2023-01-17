package com.viafoura.template.microservice;

import com.viafoura.template.microservice.infrastructure.vertx.ApplicationLauncher;
import com.viafoura.template.microservice.infrastructure.vertx.verticle.MainVerticle;

public class Main {

    public static void main(String[] args) {
        ApplicationLauncher.main(new String[]{"run", MainVerticle.class.getName()});
    }

}
